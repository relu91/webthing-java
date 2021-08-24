package io.webthings.webthing.server;

import io.webthings.webthing.affordances.InteractionAffordance;
import io.webthings.webthing.common.ExposeThingInit;
import io.webthings.webthing.forms.Form;

import java.util.Map;
import java.util.TreeMap;

/**
 * @author Lorenzo
 */
public class ManagedThingsCollection {
    private static ManagedThingsCollection inst;

    public static ManagedThingsCollection getInstance() {
        if (inst == null) {
            inst = new ManagedThingsCollection();
        }

        return inst;
    }

    private final Map<String, InteractionData> registeredUrls;
    private final Map<String, Class> actionsUrls;
    private final Map<String, Class> eventsUrls;
    private final Map<String, Class> propertiesUrls;
    private final Map<String, Class> formMetadataUrls;
    private final Map<String, FormData> registeredRootForms;

    private ManagedThingsCollection() {
        registeredUrls = new TreeMap<>();

        actionsUrls = new TreeMap<>();
        eventsUrls = new TreeMap<>();
        propertiesUrls = new TreeMap<>();
        formMetadataUrls = new TreeMap<>();
        registeredRootForms = new TreeMap<>();
    }

    private class InteractionData {
        public final InteractionAffordance Affordance;
        public final String Name;
        public final ExposedWebThing Owner;

        public InteractionData(ExposedWebThing to,
                               InteractionAffordance ia,
                               String n) {
            Affordance = ia;
            Name = n;
            Owner = to;
        }
    }

    private class FormData {
        public final Form FormData;
        public final ExposedWebThing Owner;

        public FormData(Form f, ExposedWebThing o) {
            FormData = f;
            Owner = o;
        }
    }

    public void add(ExposedWebThing to) {
        final ExposeThingInit td = to.getData();

        final java.net.URI baseURI = td.getBase();
        String sBaseURI = "";
        if (baseURI != null) {
            sBaseURI = baseURI.toString();
        }


        //load actions
        //Set<String> actionURLs =  new TreeSet<>();

        loadMap(new ActionHandlerGetter(),
                to,
                td.getActions(),
                sBaseURI,
                actionsUrls);
        //loadMap(to,td.getEvents(), sBaseURI,eventsUrls);
        loadMap(new PropertyHandlerGetter(),
                to,
                td.getProperties(),
                sBaseURI,
                propertiesUrls);

        loadMap(new EventHandlerGetter(),
                to,
                td.getEvents(),
                sBaseURI,
                eventsUrls);


        //load root level forms
        if (td.getForm() != null) {
            for (final Form f : td.getForm()) {
                final String path =
                        addForm(f, FormMetadataHandler.class, formMetadataUrls);
                registeredRootForms.put(path, new FormData(f, to));
            }
        }
    }

    public Form getRootForm(String url) {
        Form ret = null;
        final FormData id = registeredRootForms.get(url);
        if (id != null) {
            ret = id.FormData;
        }
        return ret;
    }

    public ExposedWebThing getRootFormOwner(String url) {
        ExposedWebThing ret = null;
        final FormData id = registeredRootForms.get(url);
        if (id != null) {
            ret = id.Owner;
        }
        return ret;
    }

    public InteractionAffordance getInteraction(String url) {
        InteractionAffordance ret = null;
        final InteractionData id = registeredUrls.get(url);
        if (id != null) {
            ret = id.Affordance;
        }

        return ret;
    }

    public String getInteractionName(String url) {
        String ret = null;
        final InteractionData id = registeredUrls.get(url);
        if (id != null) {
            ret = id.Name;
        }

        return ret;
    }

    public ExposedWebThing getInteractionOwner(String url) {
        ExposedWebThing ret = null;
        final InteractionData id = registeredUrls.get(url);
        if (id != null) {
            ret = id.Owner;
        }

        return ret;
    }

    private interface HandlerGetter {
        public Class getHandler(ExposedWebThing to, String name);
    }

    private class PropertyHandlerGetter implements HandlerGetter {
        @Override
        public Class getHandler(ExposedWebThing to, String name) {
            final Property p = to.getProperty(name);
            return p.getHandler();
        }
    }

    private class ActionHandlerGetter implements HandlerGetter {
        @Override
        public Class getHandler(ExposedWebThing to, String name) {
            final Action a = to.getAction(name);
            return a.getHandler();
        }
    }

    private class EventHandlerGetter implements HandlerGetter {
        @Override
        public Class getHandler(ExposedWebThing to, String name) {
            final Event a = to.getEvent(name);
            return a.getHandler();
        }
    }

    private String addForm(Form f,
                           Class handler,
                           Map<String, Class> storedURLs) {
        final java.net.URI u = f.getHref();
        String path = "";
        if (u.isAbsolute()) {
            path = u.getPath();
        } else {
            path = u.toString();
        }
        storedURLs.put(path, handler);

        return path;
    }

    private <__T extends InteractionAffordance, __GETTER extends HandlerGetter> void loadMap(
            __GETTER g,
            ExposedWebThing to,
            Map<String, __T> m,
            String base,
            Map<String, Class> storedURLs) {
        if (m == null) {
            return;
        }
        for (final Map.Entry<String, __T> e : m.entrySet()) {
            final String key = e.getKey();
            final __T data = e.getValue();
            for (final Form f : data.getForms()) {
                final String path =
                        addForm(f, g.getHandler(to, key), storedURLs);
                registeredUrls.put(path, new InteractionData(to, data, key));
            }
        }
    }

    public Map<String, Class> getActionsURL() {
        return actionsUrls;
    }

    public Map<String, Class> getEventsURL() {
        return eventsUrls;
    }

    public Map<String, Class> getPropertiesURL() {
        return propertiesUrls;
    }

    public Map<String, Class> getRootFormsURL() {
        return formMetadataUrls;
    }
}
