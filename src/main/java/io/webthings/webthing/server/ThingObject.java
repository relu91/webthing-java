/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.webthings.webthing.server;

import io.webthings.webthing.affordances.PropertyAffordance;
import io.webthings.webthing.common.ThingData;
import java.util.Map;
import java.util.TreeMap;

/**
 *
 * @author Lorenzo
 */
public class  ThingObject {
    private final   ThingData                   __data;
    private final   Map<String,Property>        __properties = new TreeMap<>();
    private final   Map<String,Action>          __actions = new TreeMap<>();
    
    
    public ThingObject(ThingData d) {
        __data = d;
        
        for(final Map.Entry<String,PropertyAffordance> e : d.getProperties().entrySet()) {
            final String name = e.getKey();
            final PropertyAffordance pa = e.getValue();
            __properties.put(name, new Property(name, pa));
        }
    }
    
    
    public ThingData    getData() {
        return __data;
    }
    public void addProperty (Property p) {
        __data.addProperty(p.getName(), p.getData());
        __properties.put(p.getName(), p);
     }
    
    public void removeProperty(String s) {
        __data.removeProperty(s);
        __properties.remove(s);
    }
    
    public void addAction (Action  p) {
        __data.addAction(p.getName(), p.getData());
        __actions.put(p.getName(), p);
     }
    
    public void removeAction(String s) {
        __data.removeAction(s);
        __actions.remove(s);
    }
    
    public Property getProperty(String s) {
        return __properties.get(s);
    }
    public Action getAction(String s) {
        return __actions.get(s);
    }
    
    public void registerEndpoints() {
        final ManagedThingsCollection mti = ManagedThingsCollection.getInstance();
        mti.add(this);
        
    }
    
    public void  setPropertyValue(String name, Object value) {
        final Property p = __properties.get(name);
        if (p != null) {
            p.setValue(value);
        }
        
    }
    
    public Object getPropertyValue(String name) {
        Object ret = null;
        final Property p = __properties.get(name);
        if ( p != null)
            ret = p.getValue();
        
        return ret;
    }
    
    public Map<String,Property> getProperties() {
        return __properties;
    }
    
    public Map<String,Action> getActions() {
        return __actions;
    }
}
