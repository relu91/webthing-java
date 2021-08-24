package io.webthings.webthing.server;

import fi.iki.elonen.NanoWSD;
import io.webthings.webthing.affordances.PropertyAffordance;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

/**
 * @author Lorenzo
 */
public class Property implements IObservable {
    private final PropertyAffordance propertyAffordance;
    private Object value;
    private String name;
    private Class handler;
    private List<NanoWSD.WebSocket> subscribers = new ArrayList<>();
    private ThingObject owner;

    public ThingObject getOwner() {
        return owner;
    }

    public void setOwner(ThingObject o) {
        owner = o;
    }

    public Property(String name, String desc, String type, Object value) {
        this.value = value;
        propertyAffordance = new PropertyAffordance();
        propertyAffordance.setDefaultDescription(desc);
        propertyAffordance.setDefaultTitle(name);
        this.name = name;
        propertyAffordance.setType(type);
    }

    public Property(String name, PropertyAffordance p) {
        this(name, p, PropertyHandler.class);
    }

    public Property(String name, PropertyAffordance p, Class h) {
        propertyAffordance = p;
        this.name = name;
        handler = h;
    }

    public PropertyAffordance getData() {
        return propertyAffordance;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object v) {
        value = v;
        //notify all subscribers
        final JSONObject o = new JSONObject();
        o.put(name, value);
        final String s = o.toString();

        List<NanoWSD.WebSocket> deadOnes = new ArrayList<>();

        for (final NanoWSD.WebSocket x : subscribers) {
            try {
                x.send(s);
            } catch (Exception e) {
                deadOnes.add(x);
            }
        }

        for (final NanoWSD.WebSocket x : deadOnes) {
            removeSubscriber(x);
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String s) {
        name = s;
    }

    public Class getHandler() {
        return handler;
    }

    public void setHandler(Class c) {
        handler = c;
    }

    @Override
    public void addSuscriber(NanoWSD.WebSocket ws) {
        subscribers.add(ws);
    }

    @Override
    public void removeSubscriber(NanoWSD.WebSocket ws) {
        subscribers.remove(ws);
    }

    @Override
    public List<NanoWSD.WebSocket> getSubscribers() {
        return subscribers;
    }
}
