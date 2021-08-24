package io.webthings.webthing.common;

import io.webthings.webthing.JSONEntity;
import io.webthings.webthing.affordances.ActionAffordance;
import io.webthings.webthing.affordances.EventAffordance;
import io.webthings.webthing.affordances.PropertyAffordance;
import io.webthings.webthing.exceptions.InvalidFieldException;
import io.webthings.webthing.exceptions.WoTException;
import io.webthings.webthing.forms.Form;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import org.json.JSONObject;

/**
 * @author Lorenzo
 */
public class ThingData extends JSONEntity {
    private List<java.net.URI> contexts;
    private List<String> types;
    private java.net.URI id;
    private String title;
    private Map<String, String> titles;
    private String description;
    private Map<String, String> descriptions;
    private VersionInfo version;
    private java.net.URI support;
    private java.net.URI base;
    private Map<String, ActionAffordance> actions;
    private Map<String, EventAffordance> events;
    private Map<String, PropertyAffordance> properties;
    private List<Link> links;
    private List<Form> forms;
    private Set<String> security;
    private Map<String, SecurityScheme> securityDefinitions;

    public void setContext(java.net.URI t) {
        contexts = new ArrayList<>();
        contexts.add(t);
    }

    public java.net.URI getContext() {
        java.net.URI ret = null;
        if (contexts != null && contexts.size() >= 1) {
            ret = contexts.get(0);
        }
        return ret;
    }

    public List<java.net.URI> getContexts() {
        return contexts;
    }

    public void addContext(java.net.URI t) {
        if (contexts == null) {
            setContext(t);
        } else {
            contexts.add(t);
        }
    }

    public void setType(String t) {
        types = new ArrayList<>();
        types.add(t);
    }

    public String getType() {
        String ret = null;
        if (types != null && types.size() >= 1) {
            ret = types.get(0);
        }
        return ret;
    }

    public List<String> getTypes() {
        return types;
    }

    public void addType(String t) {
        if (types == null) {
            setType(t);
        } else {
            types.add(t);
        }
    }

    public java.net.URI getId() {
        return id;
    }

    public void setId(java.net.URI id) {
        this.id = id;
    }

    public void setDefaultTitle(String t) {
        title = t;
    }

    public String getDefaultTitle() {
        return title;
    }

    public void setI18NTitle(String lang, String t) {
        if (titles == null) {
            titles = new TreeMap<>();
        }

        titles.put(lang, t);
    }

    public String getI18NTitle(String lang) {
        String ret = null;
        if (titles != null) {
            ret = titles.get(lang);
        }

        return ret;
    }

    public void removeI18NTitle(String lang) {
        if (titles != null) {
            titles.remove(lang);
        }
    }

    public void setDefaultDescription(String d) {
        description = d;
    }

    public String getDefaultDescription() {
        return description;
    }

    public void setI18NDescription(String lang, String d) {
        if (descriptions == null) {
            descriptions = new TreeMap<>();
        }
        descriptions.put(lang, d);
    }

    public String getI18NDescription(String lang) {
        String ret = null;
        if (descriptions != null) {
            ret = descriptions.get(lang);
        }

        return ret;
    }

    public void removeI18NDescription(String lang) {
        if (descriptions != null) {
            descriptions.remove(lang);
        }
    }

    public VersionInfo getVersion() {
        return version;
    }

    public void setVersion(VersionInfo v) {
        version = v;
    }

    public java.net.URI getSupport() {
        return support;
    }

    public void setSupport(java.net.URI u) {
        support = u;
    }

    public java.net.URI getBase() {
        return base;
    }

    public void setBase(java.net.URI u) {
        base = u;
    }

    public void addAction(String id, ActionAffordance a) {
        if (actions == null) {
            actions = new TreeMap<>();
        }

        actions.put(id, a);
    }

    public ActionAffordance getAction(String id) {
        if (actions == null) {
            return null;
        }

        return actions.get(id);
    }

    public Map<String, ActionAffordance> getActions() {
        return actions;
    }

    public void removeAction(String id) {
        if (actions == null) {
            return;
        }

        actions.remove(id);
    }

    public void addEvent(String id, EventAffordance a) {
        if (events == null) {
            events = new TreeMap<>();
        }

        events.put(id, a);
    }

    public EventAffordance getEvent(String id) {
        if (events == null) {
            return null;
        }

        return events.get(id);
    }

    public Map<String, EventAffordance> getEvents() {
        return events;
    }

    public void removeEvent(String id) {
        if (events == null) {
            return;
        }

        events.remove(id);
    }

    public void addProperty(String id, PropertyAffordance a) {
        if (properties == null) {
            properties = new TreeMap<>();
        }

        properties.put(id, a);
    }

    public PropertyAffordance getProperty(String id) {
        if (properties == null) {
            return null;
        }

        return properties.get(id);
    }

    public Map<String, PropertyAffordance> getProperties() {
        return properties;
    }

    public void removeProperty(String id) {
        if (properties == null) {
            return;
        }

        properties.remove(id);
    }

    public List<Link> getLink() {
        return links;
    }

    public void addLink(Link o) {
        if (links == null) {
            links = new ArrayList<>();
        }

        links.add(o);
    }

    public List<Form> getForm() {
        return forms;
    }

    public void addForm(Form o) {
        if (forms == null) {
            forms = new ArrayList<>();
        }

        forms.add(o);
    }

    public void setSecurity(String t) {
        security = new TreeSet<>();
        security.add(t);
    }

    /*
        public String getSecurity() {
            String ret = null;
            if (security != null && security.size() >= 1) {
                ret = security.get(0);
            }
            return ret;
        }
    */
    public Set<String> getSecurities() {
        return security;
    }

    public void addSecurity(String t) {
        if (security == null) {
            setSecurity(t);
        } else {
            security.add(t);
        }
    }

    public void addSecurityDefinition(String id, SecurityScheme a) {
        if (securityDefinitions == null) {
            securityDefinitions = new TreeMap<>();
        }

        securityDefinitions.put(id, a);
    }

    public SecurityScheme getSecurityDefinition(String id) {
        if (securityDefinitions == null) {
            return null;
        }

        return securityDefinitions.get(id);
    }

    public Map<String, SecurityScheme> getSecurityDefinitions() {
        return securityDefinitions;
    }

    public void removeSecurityDefinition(String id) {
        if (securityDefinitions == null) {
            return;
        }

        securityDefinitions.remove(id);
    }

    @Override
    public JSONObject asJSON() throws WoTException {
        final JSONObject ret = new JSONObject();
        //convert URI to String
        if (contexts != null) {
            final List<String> uriAsStrings = new ArrayList<>();
            for (java.net.URI u : contexts) {
                uriAsStrings.add(u.toString());
            }

            JSONEntityHelpers.addSingleItemOrList("@context",
                                                  uriAsStrings,
                                                  ret);
        }

        JSONEntityHelpers.addSingleItemOrList("@type", types, ret);
        JSONEntityHelpers.addURI("id", id, ret);
        JSONEntityHelpers.addURI("base", base, ret);
        JSONEntityHelpers.addURI("support", support, ret);

        JSONEntityHelpers.addString("title", title, ret);
        JSONEntityHelpers.addCollection("titles", titles, ret);
        JSONEntityHelpers.addString("description", description, ret);
        JSONEntityHelpers.addCollection("descriptions", descriptions, ret);

        JSONEntityHelpers.addJSONEntity("version", version, ret);

        JSONEntityHelpers.addJSONEntityCollection("actions", actions, ret);
        JSONEntityHelpers.addJSONEntityCollection("properties", properties,
                                                  ret);
        JSONEntityHelpers.addJSONEntityCollection("events", events, ret);

        JSONEntityHelpers.addJSONEntityCollection("forms", forms, ret);
        JSONEntityHelpers.addJSONEntityCollection("links", links, ret);

        JSONEntityHelpers.addSingleItemOrList("security", security, ret);
        JSONEntityHelpers.addJSONEntityCollection("securityDefinitions",
                                                  securityDefinitions,
                                                  ret);

        return ret;
    }

    @Override
    public JSONEntity fromJSON(JSONObject o) throws WoTException {
        //read uri array string 
        final List<String> uriAsStrings =
                JSONEntityHelpers.readObjectSingleOrList(o,
                                                         "@context",
                                                         String.class,
                                                         ArrayList.class);
        if (uriAsStrings != null) {
            contexts = new ArrayList<>();
            for (final String s : uriAsStrings) {
                try {
                    contexts.add(new java.net.URI(s));
                } catch (URISyntaxException e) {
                    throw new InvalidFieldException("@context", s);
                }
            }
        }

        types = JSONEntityHelpers.readObjectSingleOrList(o,
                                                         "@type",
                                                         String.class,
                                                         ArrayList.class);
        id = JSONEntityHelpers.readURI(o, "id");
        base = JSONEntityHelpers.readURI(o, "base");
        support = JSONEntityHelpers.readURI(o, "support");

        title = JSONEntityHelpers.readObject(o, "title", String.class);
        titles = JSONEntityHelpers.readCollection(o,
                                                  "titles",
                                                  String.class,
                                                  TreeMap.class);
        description =
                JSONEntityHelpers.readObject(o, "description", String.class);
        descriptions = JSONEntityHelpers.readCollection(o,
                                                        "descriptions",
                                                        String.class,
                                                        TreeMap.class);

        version =
                JSONEntityHelpers.readEntity(o, "version", VersionInfo.class);

        actions = JSONEntityHelpers.readEntityMap(o,
                                                  "actions",
                                                  ActionAffordance.class,
                                                  TreeMap.class);
        events = JSONEntityHelpers.readEntityMap(o,
                                                 "events",
                                                 EventAffordance.class,
                                                 TreeMap.class);
        properties = JSONEntityHelpers.readEntityMap(o,
                                                     "properties",
                                                     PropertyAffordance.class,
                                                     TreeMap.class);

        security = JSONEntityHelpers.readObjectSingleOrList(o,
                                                            "security",
                                                            String.class,
                                                            TreeSet.class);
        securityDefinitions = JSONEntityHelpers.readEntityMap(o,
                                                              "securityDefinitions",
                                                              SecurityScheme.class,
                                                              TreeMap.class);


        return this;
    }
}
