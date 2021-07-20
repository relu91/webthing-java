/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.webthings.webthing.server;

import fi.iki.elonen.NanoWSD;
import io.webthings.webthing.affordances.PropertyAffordance;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONObject;

/**
 *
 * @author Lorenzo
 */
public class Property implements IObservable {
    private final PropertyAffordance    __prop_def;
    private Object                      __value;
    private String                      __name;
    private Class                       __handler;
    private List<NanoWSD.WebSocket>     __subscribers = new ArrayList<>();
    
    
    public Property(String name,String desc, String type,Object value) {
        __value = value;
        __prop_def = new PropertyAffordance();
        __prop_def.setDefaultDescription(desc);
        __prop_def.setDefaultTitle(name);
        __name = name;
        __prop_def.setType(type);
    }
    public Property(String name, PropertyAffordance p) {
        this(name, p, PropertyHandler.class);
    }
    public Property(String name, PropertyAffordance p,Class h) {
        __prop_def = p;
        __name = name;
        __handler = h;
    }
    
    public PropertyAffordance getData() {
        return __prop_def;
    }
    
    public  Object getValue() {
        return __value;
    }
    
    public void setValue(Object v ) {
        __value = v;
        //notify all subscribers
        final JSONObject o = new JSONObject();
        o.put(__name, __value);
        final String s = o.toString();
        
        List<NanoWSD.WebSocket> deadOnes = new ArrayList<>();
        
        for(final NanoWSD.WebSocket x : __subscribers) {
            try {
                x.send(s);
            } catch(Exception e ) {
                deadOnes.add(x);
            }
        }
        
        for(final NanoWSD.WebSocket x: deadOnes) {
            removeSubscriber(x);
        }
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
    
}
