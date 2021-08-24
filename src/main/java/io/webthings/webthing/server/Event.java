package io.webthings.webthing.server;

import fi.iki.elonen.NanoWSD;
import io.webthings.webthing.affordances.EventAffordance;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

/**
 * @author Lorenzo
 */
public abstract class Event implements IObservable, INotifiable {
    protected final EventAffordance eventAffordance;
    protected String name;
    protected Class handler;
    protected List<NanoWSD.WebSocket> subscribers = new ArrayList<>();
    private ThingObject owner;

    public ThingObject getOwner() {
        return owner;
    }

    public void setOwner(ThingObject o) {
        owner = o;
    }

    public Event(String name, String desc, String type) {
        eventAffordance = new EventAffordance();
        eventAffordance.setDefaultDescription(desc);
        eventAffordance.setDefaultTitle(name);
        this.name = name;
        eventAffordance.setType(type);
    }

    public Event(String name, EventAffordance p) {
        this(name, p, EventHandler.class);
    }

    public Event(String name, EventAffordance p, Class h) {
        eventAffordance = p;
        this.name = name;
        handler = h;
    }

    public EventAffordance getData() {
        return eventAffordance;
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

    @Override
    public void notifyEvent() {
        final List<NanoWSD.WebSocket> deadOnes = new ArrayList<>();
        final JSONObject o = makeEventData();
        final String s = o.toString();

        for (final NanoWSD.WebSocket x : subscribers) {
            try {
                //do something
                x.send(s);
            } catch (Exception e) {
                deadOnes.add(x);
            }
        }

        for (final NanoWSD.WebSocket x : deadOnes) {
            subscribers.remove(x);
        }
    }

    protected abstract JSONObject makeEventData();
}
