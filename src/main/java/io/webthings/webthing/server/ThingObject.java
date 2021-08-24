package io.webthings.webthing.server;

import io.webthings.webthing.affordances.PropertyAffordance;
import io.webthings.webthing.common.ThingData;

import java.util.Map;
import java.util.TreeMap;

/**
 * @author Lorenzo
 */
public class ThingObject {
    private final ThingData data;
    private final Map<String, Property> properties = new TreeMap<>();
    private final Map<String, Action> actions = new TreeMap<>();
    private final Map<String, Event> events = new TreeMap<>();

    public ThingObject(ThingData d) {
        data = d;

        for (final Map.Entry<String, PropertyAffordance> e : d.getProperties()
                                                              .entrySet()) {
            final String name = e.getKey();
            final PropertyAffordance pa = e.getValue();
            properties.put(name, new Property(name, pa));
        }
    }

    public ThingData getData() {
        return data;
    }

    public void addProperty(Property p) {
        data.addProperty(p.getName(), p.getData());
        properties.put(p.getName(), p);
        p.setOwner(this);
    }

    public void removeProperty(String s) {
        data.removeProperty(s);
        properties.remove(s);
    }

    public void addAction(Action p) {
        data.addAction(p.getName(), p.getData());
        actions.put(p.getName(), p);
        p.setOwner(this);
    }

    public void removeAction(String s) {
        data.removeAction(s);
        actions.remove(s);
    }

    public Property getProperty(String s) {
        return properties.get(s);
    }

    public Action getAction(String s) {
        return actions.get(s);
    }

    public void registerEndpoints() {
        final ManagedThingsCollection mti =
                ManagedThingsCollection.getInstance();
        mti.add(this);
    }

    public void setPropertyValue(String name, Object value) {
        final Property p = properties.get(name);
        if (p != null) {
            p.setValue(value);
        }
    }

    public Object getPropertyValue(String name) {
        Object ret = null;
        final Property p = properties.get(name);
        if (p != null) {
            ret = p.getValue();
        }

        return ret;
    }

    public Map<String, Property> getProperties() {
        return properties;
    }

    public Map<String, Action> getActions() {
        return actions;
    }

    public Map<String, Event> getEvents() {
        return events;
    }

    public void addEvent(Event p) {
        data.addEvent(p.getName(), p.getData());
        events.put(p.getName(), p);
        p.setOwner(this);
    }

    public void removeEvent(String s) {
        data.removeEvent(s);
        events.remove(s);
    }

    public Event getEvent(String s) {
        return events.get(s);
    }
}
