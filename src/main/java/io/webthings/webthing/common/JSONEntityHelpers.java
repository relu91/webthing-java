/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.webthings.webthing.common;

import io.webthings.webthing.JSONEntity;
import io.webthings.webthing.exceptions.InvalidFieldException;
import io.webthings.webthing.exceptions.MissingFieldException;
import io.webthings.webthing.exceptions.WoTException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author Lorenzo
 */
public class JSONEntityHelpers {
    public static void addString(String name, String val, JSONObject o) {
        
        if ( val != null) {
            final String s_val  = val.trim();
            if (s_val.length() > 0 ) {
                o.put(name, s_val);
            }
        }
    }
    public static void addObject(String name, Object val, JSONObject o) {
        
        if ( val != null) {
            o.put(name, val);
        }
    }
    
    public static void addCollection(String name, Collection c, JSONObject o ) {
        if (c != null && c.size() > 0 ) {
            o.put(name, c);
        }
        
    }
    public static void addCollection(String name, Map  m, JSONObject o ) {
        if (m != null && m.size() > 0 ) {
            o.put(name, m);
        }
        
    }
    public static void addSingleItemOrList(String name, Collection<String> l, JSONObject o) {
        if (l != null) {
            if (l.size() == 1 )
                addString(name,(String) l.toArray()[0],o);
            else
                addCollection(name, l, o);
        }
        
    }
    
    public static void addRequiredString(String name, String val, JSONObject o) throws MissingFieldException {
        if (val == null || val.trim().length() == 0 )
            throw new MissingFieldException(name);
        
        addString(name, val, o);
    }
    
    public static void addJSONEntity(String name, JSONEntity e, JSONObject o )  {
        if ( e != null) {
            try {
                o.put(name, e.asJSON());
            } catch(Exception ee ) {
                System.err.println(ee);
            }
        }
    }
    
    public static void addRequiredJSONEntity(String name, JSONEntity e, JSONObject o )  throws WoTException{ 
        if ( e != null) {
            o.put(name, e.asJSON());
        }
    }
    
    public static List<String>  checkedInitList(String v ) {
       List<String>   ret = null;
       if (v != null && v.length() > 0 ) {
           ret = new ArrayList<>();
           ret.add(v);
       }
       return ret;
    }
    public static Set<String>  checkedInitSet(String v ) {
       Set<String>   ret = null;
       if (v != null && v.length() > 0 ) {
           ret = new TreeSet<>();
           ret.add(v);
       }
       return ret;
    }
    public static void addJSONEntityCollection(String name, List<? extends JSONEntity> c, JSONObject o ) {
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

    public static void addJSONEntityCollection(String name, Map<String, ? extends JSONEntity> c, JSONObject o ) {
        if (c != null && c.size() >  0) {
            final JSONObject m = new JSONObject();
            
            for(final Map.Entry<String,? extends JSONEntity> e : c.entrySet() ) {
                try {
                    final String key = e.getKey();
                    final JSONEntity val = e.getValue();
                    m.put(key,val.asJSON());
                } catch(Exception ee ) {
                    System.err.println(ee);
                }
            }
            
            o.put(name, m);
        
        }        
    }
  
    public static  <__T extends JSONEntity,__COLL extends Collection<__T> > __COLL readEntityCollection (JSONObject root, String name, Class<__T> cls,Class<__COLL> ccls) {
        __COLL ret  = null;
        try {
            final JSONArray o = root.getJSONArray(name);
            if (o != null) {
                ret = ccls.newInstance();
                for(int i = 0 ; i < o.length();++i) {
                    final JSONObject itm = o.getJSONObject(i);
                    final __T        retType = (__T) cls.newInstance().fromJSON(itm);
                    ret.add(retType);
                }
                
            }
        } catch(Exception e ) {
            System.err.println(e);
        }
        
        return ret;
    }
    public static  <__T extends JSONEntity,__COLL extends Map<String,__T> > __COLL readEntityMap (JSONObject root, String name, Class<__T> cls,Class<__COLL> ccls) {
        __COLL ret  = null;
        try {
            final JSONObject o = root.getJSONObject(name);
            if (o != null) {
                ret = ccls.newInstance();
                for(final String key : o.keySet()) {
                    if (cls.isInstance(SecurityScheme.class ) == false ) {
                        final JSONObject child = o.getJSONObject(key);
                        final __T        retType = (__T) cls.newInstance().fromJSON(child);
                        ret.put(key, retType);
                    } else {
                        final JSONObject child = o.getJSONObject(key);
                        final __T        retType = (__T) SecurityScheme.newInstance(o);
                        ret.put(key, retType);
                        
                    }
                    
                }
                
            }
        } catch(Exception e ) {
            System.err.println(e);
        }
        
        return ret;
    }    
    public static  <__T extends JSONEntity> __T readEntity (JSONObject root, String name, Class<__T> cls) {
        __T ret = null;
        try {
            
            final JSONObject    o = root.getJSONObject(name);
            if ( o != null) {
                if (cls.getName().equals(DataSchema.class.getName())) {
                    ret = (__T) DataSchema.newInstance(o);
                } else {
                    final   __T  c = cls.newInstance();
                    ret = (__T) c.fromJSON(o);
                }
                
     
            }
        } catch(Exception e ) {
            System.err.println(e);
        }
        
        return ret;
    }
   
    public static  <__T > __T readObject (JSONObject root, String name, Class<__T> cls) {
        __T ret = null;
        try {
            
            final Object    o = root.get(name);
            if ( o != null) {
                ret = (__T) o;
                
            }
        } catch(Exception e ) {
            System.err.println(e);
        }
        
        return ret;
        
        
    }
    
    public static <__T,__COLL extends Collection<__T> >  __COLL readObjectSingleOrList(JSONObject root, String name, Class<__T> cls,Class<__COLL> ccls) {
        __COLL ret = null;
        try {
            final Object    value = root.get(name);
            if (value != null) {
                ret = ccls.newInstance();
                if (value  instanceof JSONArray) {
                    //copies all elements
                   final JSONArray ja = (JSONArray) value;
                   for(final Object itm : ja ) {
                       final __T t = (__T) itm;
                       ret.add(t);
                   }
                    
                } else {
                    //adds single item to collection 
                    final __T t = (__T) value;
                    ret.add(t);
                }
                
            }


        } catch(Exception e ) {
            System.err.println(e);
        }
        
        return ret;
    }
    
    public static <__T,__COLL >  __COLL readCollection(JSONObject root, String name, Class<__T> cls,Class<__COLL> ccls) {
        __COLL ret = null;
        try {
            final Object    value = root.get(name);
            if (value != null) {
                boolean done = false;
                ret = ccls.newInstance();
                if (value  instanceof JSONArray) {
                    //plain collection set / list
                   final JSONArray ja = (JSONArray) value;
                   final Collection coll = (Collection<__T>) ret;
                   for(final Object itm : ja ) {
                       final __T t = (__T) itm;
                       coll.add(t);
                   }
                } 
                if (value instanceof JSONObject) {
                    //map
                    final JSONObject child = (JSONObject) value;
                    final Map<String,__T> coll = (Map<String,__T>) ret;
                    for(final String key : child.keySet()) {
                        final __T collValue = (__T) child.get(key);
                        coll.put(key, collValue);
                    }
                }
                
            }


        } catch(Exception e ) {
            System.err.println(e);
        }
        
        return ret;        
    }

    public static  <__T extends JSONEntity,__COLL extends Collection<__T> > __COLL readSingleEntityOrCollection (JSONObject root, String name, Class<__T> cls,Class<__COLL> ccls) {
        final __T  ent = readEntity(root, name, cls);
        __COLL ret = null;
        if (ent == null) {
            //try collection 
            ret = readEntityCollection(root, name, cls, ccls);
        } else {
            try {
                ret = ccls.newInstance();
                ret.add(ent);
            } catch(Exception e ) {
                
            }
            
        }
        
        return ret;
    }
    
    public static void addJSONSingleEntityOrCollection(String name, List<? extends JSONEntity> c, JSONObject o ) {
        if (c == null)
            return;
        
        if (c.size() == 0 )
            return;
        
        if (c.size() == 1 ) {
            addJSONEntity(name, c.get(0), o);
        } else {
            addJSONSingleEntityOrCollection(name, c, o);
        }
    }
    
    public static void addURI(String name, java.net.URI uri, JSONObject o ) {
        if (uri == null)
            return;
        
        final String s = uri.toString();
        
        addString(name, s, o);
    }
    
    public static java.net.URI readURI(JSONObject root, String name ) throws WoTException {
        try {
            return readObject(root, name, java.net.URI.class);
        } catch(Exception e) {
            java.net.URI ret = null;
            final String s = readObject(root, name, String.class);

            if (s == null || s.length() == 0 )
                return null;

            try {
                ret = new java.net.URI(s);
            }  catch (URISyntaxException ee ) {
                throw new InvalidFieldException(name,s);
            }

            return ret;
            
        }
    }
}
