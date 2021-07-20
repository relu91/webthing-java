/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.webthings.webthing.server;

import fi.iki.elonen.NanoWSD;
import io.webthings.webthing.affordances.EventAffordance;
import io.webthings.webthing.affordances.EventAffordance;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Lorenzo
 */
public class Event implements IObservable , INotifiable{
    private final EventAffordance       __evt_def;
    private String                      __name;
    private Class                       __handler;
    private List<NanoWSD.WebSocket>     __subscribers = new ArrayList<>();
    
    
    public Event(String name,String desc, String type) {
        __evt_def = new EventAffordance();
        __evt_def.setDefaultDescription(desc);
        __evt_def.setDefaultTitle(name);
        __name = name;
        __evt_def.setType(type);
    }
    public Event(String name, EventAffordance p) {
        this(name, p, null);//EventHandler.class);
    }
    public Event(String name, EventAffordance p,Class h) {
        __evt_def = p;
        __name = name;
        __handler = h;
    }
    
    public EventAffordance getData() {
        return __evt_def;
    }
    
  
    public String getName() {
        return __name;
    }
    
    public void setName(String s ) {
        __name = s;
    }
    
    public Class getHandler() {
        return __handler;
    }
    
    public void setHandler(Class c ) {
        __handler = c;
    }

    @Override
    public void addSuscriber(NanoWSD.WebSocket ws) {
        __subscribers.add(ws);
    }

    @Override
    public void removeSubscriber(NanoWSD.WebSocket ws) {
        __subscribers.remove(ws);
    }

    @Override
    public List<NanoWSD.WebSocket> getSubscribers() {
        return __subscribers;
    }

    @Override
    public void notifyEvent() {
        final List<NanoWSD.WebSocket>   deadOnes = new ArrayList<>();
        for(final NanoWSD.WebSocket x : __subscribers)  {
            try {
                //do something
            } catch(Exception e ) {
                deadOnes.add(x);
            }
        }
        
        for(final NanoWSD.WebSocket x : deadOnes)  {
            __subscribers.remove(x);
        }
    }
    
}
