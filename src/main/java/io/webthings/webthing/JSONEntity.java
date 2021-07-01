/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.webthings.webthing;

import io.webthings.webthing.exceptions.MissingFieldException;
import io.webthings.webthing.exceptions.WoTException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import org.json.JSONObject;

/**
 *
 * @author Lorenzo
 */
public abstract class  JSONEntity {
    public abstract JSONObject asJSON() throws WoTException;
    
    protected static void addString(String name, String val, JSONObject o) {
        
        if ( val != null) {
            final String s_val  = val.trim();
            if (s_val.length() > 0 ) {
                o.put(name, s_val);
            }
        }
    }
    protected static void addObject(String name, Object val, JSONObject o) {
        
        if ( val != null) {
            o.put(name, val);
        }
    }
    
    protected static void addCollection(String name, Collection c, JSONObject o ) {
        if (c != null && c.size() > 0 ) {
            o.put(name, c);
        }
        
    }
    protected static void addCollection(String name, Map  m, JSONObject o ) {
        if (m != null && m.size() > 0 ) {
            o.put(name, m);
        }
        
    }
    protected static void addSingleItemOrList(String name, Collection<String> l, JSONObject o) {
        if (l != null) {
            if (l.size() == 1 )
                addString(name,(String) l.toArray()[0],o);
            else
                addCollection(name, l, o);
        }
        
    }
    
    protected static void addRequiredString(String name, String val, JSONObject o) throws MissingFieldException {
        if (val == null || val.trim().length() == 0 )
            throw new MissingFieldException(name);
        
        addString(name, val, o);
    }
    
    protected static void addJSONEntity(String name, JSONEntity e, JSONObject o )  {
        if ( e != null) {
            try {
                o.put(name, e.asJSON());
            } catch(Exception ee ) {
                System.err.println(ee);
            }
        }
    }
    
    protected static void addRequiredJSONEntity(String name, JSONEntity e, JSONObject o )  throws WoTException{ 
        if ( e != null) {
            o.put(name, e.asJSON());
        }
    }
    
    protected static List<String>  checkedInitList(String v ) {
       List<String>   ret = null;
       if (v != null && v.length() > 0 ) {
           ret = new ArrayList<>();
           ret.add(v);
       }
       return ret;
    }
    protected static Set<String>  checkedInitSet(String v ) {
       Set<String>   ret = null;
       if (v != null && v.length() > 0 ) {
           ret = new TreeSet<>();
           ret.add(v);
       }
       return ret;
    }
    protected static void addJSONEntityCollection(String name, List<? extends JSONEntity> c, JSONObject o ) {
        if (c != null && c.size() >  0) {
            for(final JSONEntity e : c ) {
                try {
                    o.append(name, e.asJSON());
                } catch(Exception ee ) {
                    System.err.println(ee);
                }
            }
        
        }        
    }
}
