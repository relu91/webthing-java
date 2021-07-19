/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
 *
 * @author Lorenzo
 */
public class ThingData extends JSONEntity {
    private List<java.net.URI>                  __contexts;
    private List<String>                        __types;
    private java.net.URI                        __id;
    private String                              __title;
    private Map<String,String>                  __titles;
    private String                              __description;
    private Map<String,String>                  __descriptions;
    private VersionInfo                         __version;
    private java.net.URI                        __support;
    private java.net.URI                        __base;
    
    private Map<String,ActionAffordance>        __actions;
    private Map<String,EventAffordance>         __events;
    private Map<String,PropertyAffordance>      __properties;    
    private List<Link>                          __links;
    private List<Form>                          __forms;
    
    private Set<String>                         __security;
    private Map<String,SecurityScheme>          __securityDefinitions;
    
    
    public void setContext(java.net.URI t) {
        __contexts = new ArrayList<>();
        __contexts.add(t);
    }
    public java.net.URI getContext() {
        java.net.URI ret = null;
        if (__contexts != null && __contexts.size() >= 1) {
            ret = __contexts.get(0);
        }
        return ret;
    }
    
    public List<java.net.URI> getContexts() {
        return  __contexts;
    }
    public void addContext(java.net.URI t) {
        if (__contexts  == null)
            setContext(t);
        else
            __contexts.add(t);
    }
    
    public void setType(String t) {
        __types = new ArrayList<>();
        __types.add(t);
    }
    public String getType() {
        String ret = null;
        if (__types != null && __types.size() >= 1) {
            ret = __types.get(0);
        }
        return ret;
    }
    
    public List<String> getTypes() {
        return  __types;
    }
    public void addType(String t) {
        if (__types  == null)
            setType(t);
        else
            __types.add(t);
    }
    
    public java.net.URI getId() {
        return __id;
    }
    
    public void setId(java.net.URI id) {
        __id = id;
    }
    
    public void setDefaultTitle(String t) {
        __title = t;
    }
    public String getDefaultTitle() {
        return __title;
    }
    public void setI18NTitle(String lang, String t) {
        if (__titles == null)  
            __titles = new TreeMap<>();
        
        __titles.put(lang, t);
    }
    public String getI18NTitle(String lang) {
        String ret = null;
        if (__titles != null)
            ret =  __titles.get(lang);
        
        return ret;
    }
    public void removeI18NTitle(String lang) {
        if (__titles != null)
            __titles.remove(lang);
    }
    public void setDefaultDescription(String d) {
        __description = d;
    }

    public String getDefaultDescription() {
        return __description;
    }
    public void setI18NDescription(String lang, String  d) {
        if (__descriptions == null) {
            __descriptions = new TreeMap<>();
        }
        __descriptions.put(lang, d);
        
    }
    
    public String getI18NDescription(String lang) {
        String ret = null;
        if (__descriptions != null)
            ret = __descriptions.get(lang);
        
        return ret;
    }
    
    public void removeI18NDescription(String lang) {
        if (__descriptions != null)
            __descriptions.remove(lang);
    }
    
    public VersionInfo getVersion() {
        return __version;
    }
    
    public void setVersion(VersionInfo v) {
        __version = v;
    }
    
    public java.net.URI getSupport() {
        return __support;
    }
    
    public void setSupport(java.net.URI u) {
        __support = u;
    }
    
    public java.net.URI getBase() {
        return __base;
    }
    
    public void setBase(java.net.URI u) {
        __base = u;
    }
    
    public void addAction(String id, ActionAffordance a) {
        if (__actions == null) {
            __actions = new TreeMap<>();
        }
        
        __actions.put(id, a);

    }
    public ActionAffordance getAction(String id) {
        if (__actions == null)
            return null;
        
        return __actions.get(id);
    } 
    
    public Map<String, ActionAffordance> getActions() {
        return __actions;
    }
    
    public void removeAction(String id) {
        if (__actions == null)
            return;
        
        __actions.remove(id);
    }
    
    
    public void addEvent(String id, EventAffordance a) {
        if (__events == null)
            __events = new TreeMap<>();
        
        __events.put(id, a);
    }
    public EventAffordance getEvent(String id) {
        if (__events == null)
            return null;
        
        return __events.get(id);
    } 
    
    public Map<String, EventAffordance> getEvents() {
        return __events;
    }
    
    public void removeEvent(String id) {
        if (__events == null)
            return;
        
        __events.remove(id);
    }
    
    public void addProperty(String id, PropertyAffordance a) {
        if (__properties == null)
            __properties = new TreeMap<>();
        
        __properties.put(id, a);
    }
    public PropertyAffordance getProperty(String id) {
        if (__properties == null)
            return null;
        
        return __properties.get(id);
    } 
    
    public Map<String, PropertyAffordance> getProperties() {
        return __properties;
    }
    
    public void removeProperty(String id) {
        if (__properties == null)
            return;
        
        __properties.remove(id);
    }
    
    public List<Link> getLink() {
        return  __links;
    }
    
    public void addLink(Link o ) {
        if (__links == null)
            __links = new ArrayList<>();
        
        __links.add(o);
    }
    
    public List<Form> getForm() {
        return  __forms;
    }
    
    public void addForm(Form o ) {
        if (__forms == null)
            __forms = new ArrayList<>();
        
        __forms.add(o);
    }
    
    public void setSecurity(String t) {
        __security = new TreeSet<>();
        __security.add(t);
    }
/*    
    public String getSecurity() {
        String ret = null;
        if (__security != null && __security.size() >= 1) {
            ret = __security.get(0);
        }
        return ret;
    }
*/    
    public Set<String> getSecurities() {
        return  __security;
    }
    public void addSecurity(String t) {
        if (__security  == null)
            setSecurity(t);
        else
            __security.add(t);
    }
    
    public void addSecurityDefinition(String id, SecurityScheme a) {
        if (__securityDefinitions == null)
            __securityDefinitions = new TreeMap<>();
        
        __securityDefinitions.put(id, a);
    }
    public SecurityScheme getSecurityDefinition(String id) {
        if (__securityDefinitions == null)
            return null;
        
        return __securityDefinitions.get(id);
    } 
    
    public Map<String, SecurityScheme> getSecurityDefinitions() {
        return __securityDefinitions;
    }
    
    public void removeSecurityDefinition(String id) {
        if (__securityDefinitions == null)
            return;
        
        __securityDefinitions.remove(id);
    }

    @Override
    public JSONObject asJSON() throws WoTException {
        final JSONObject ret = new JSONObject();
        //convert URI to String
        if (__contexts != null) {
            final List<String>    uriAsStrings = new ArrayList<>();
            for(java.net.URI u : __contexts) {
                uriAsStrings.add(u.toString());
            }
            
            JSONEntityHelpers.addSingleItemOrList("@context", uriAsStrings, ret);
        }
        
        JSONEntityHelpers.addSingleItemOrList("@type", __types, ret);
        JSONEntityHelpers.addURI("id", __id, ret);
        JSONEntityHelpers.addURI("base", __base, ret);
        JSONEntityHelpers.addURI("support", __support, ret);
        
        JSONEntityHelpers.addString("title",__title, ret);
        JSONEntityHelpers.addCollection("titles", __titles, ret);
        JSONEntityHelpers.addString("description",__description,ret);
        JSONEntityHelpers.addCollection("descriptions",__descriptions,ret);
        
        JSONEntityHelpers.addJSONEntity("version", __version, ret);
        
        JSONEntityHelpers.addJSONEntityCollection("actions", __actions, ret);
        JSONEntityHelpers.addJSONEntityCollection("properties", __properties, ret);
        JSONEntityHelpers.addJSONEntityCollection("events", __events, ret);
        
        JSONEntityHelpers.addJSONEntityCollection("forms", __forms, ret);
        JSONEntityHelpers.addJSONEntityCollection("links", __links, ret);
        
        JSONEntityHelpers.addSingleItemOrList("security", __security, ret);
        JSONEntityHelpers.addJSONEntityCollection("securityDefinitions", __securityDefinitions, ret);
        
        return ret;
    }

    @Override
    public JSONEntity fromJSON(JSONObject o) throws WoTException {
        //read uri array string 
        final List<String>    uriAsStrings = JSONEntityHelpers.readObjectSingleOrList(o, "@context", String.class, ArrayList.class);
        if (uriAsStrings != null) {
            __contexts = new ArrayList<>();
            for(final String s : uriAsStrings) {
                try {
                    __contexts.add(new java.net.URI(s));
                } catch(URISyntaxException e) {
                    throw new InvalidFieldException("@context",s);
                }

            }
            
        }
        
        __types = JSONEntityHelpers.readObjectSingleOrList(o, "@type", String.class, ArrayList.class);
        __id = JSONEntityHelpers.readURI(o, "id");
        __base = JSONEntityHelpers.readURI(o, "base");        
        __support = JSONEntityHelpers.readURI(o, "support");        
        
        __title = JSONEntityHelpers.readObject(o, "title", String.class);
        __titles = JSONEntityHelpers.readCollection(o, "titles", String.class,TreeMap.class);
        __description = JSONEntityHelpers.readObject(o, "description", String.class);
        __descriptions = JSONEntityHelpers.readCollection(o, "descriptions", String.class,TreeMap.class);

        __version = JSONEntityHelpers.readEntity(o, "version", VersionInfo.class);
        
        __actions = JSONEntityHelpers.readEntityMap(o, "actions", ActionAffordance.class, TreeMap.class);
        __events = JSONEntityHelpers.readEntityMap(o, "events", EventAffordance.class, TreeMap.class);
        __properties = JSONEntityHelpers.readEntityMap(o, "properties", PropertyAffordance.class, TreeMap.class);
        
        __security = JSONEntityHelpers.readObjectSingleOrList(o, "security", String.class, TreeSet.class);
         __securityDefinitions = JSONEntityHelpers.readEntityMap(o, "securityDefinitions",SecurityScheme.class, TreeMap.class);
         
        
        return this;
        
    }
    
}
