/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.webthings.webthing.affordances;

import io.webthings.webthing.JSONEntity;
import io.webthings.webthing.common.DataSchema;
import io.webthings.webthing.common.JSONEntityHelpers;
import static io.webthings.webthing.common.JSONEntityHelpers.addCollection;
import static io.webthings.webthing.common.JSONEntityHelpers.addJSONEntityCollection;
import static io.webthings.webthing.common.JSONEntityHelpers.addSingleItemOrList;
import static io.webthings.webthing.common.JSONEntityHelpers.addString;
import static io.webthings.webthing.common.JSONEntityHelpers.checkedInitList;
import io.webthings.webthing.exceptions.InvalidFieldException;
import io.webthings.webthing.exceptions.WoTException;
import io.webthings.webthing.forms.Form;
import io.webthings.webthing.forms.Operation;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import org.json.JSONObject;

/**
 *
 * @author Lorenzo
 */
public class InteractionAffordance extends JSONEntity{
    private     String                   __title;
    private     Map<String,String>       __titles;
    private     String                   __description;
    private     Map<String,String>       __descriptions;
    private     List<String>             __types;
    private     List<Form>              __forms;
    private     Map<String, DataSchema> __uriVariables;
    private     Set<Operation.id>       __allowedOps;
    
    protected InteractionAffordance(Set<Operation.id> allowedOps) {
        __allowedOps = allowedOps;
    }
    public InteractionAffordance() {
        
    }
    
    public InteractionAffordance(Form f ) {
        __forms = new ArrayList<>();
        __forms.add(f);
    }
    
    public InteractionAffordance(String type, String title, String desc, Form f ) {
        this(f);
        __title = title;
        __description = desc;
        __types = checkedInitList(type);
        
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
    
    public void addForm(Form f ) throws WoTException {
        checkForm(f);
        __forms.add(f);
    }
    
    public List<Form>  getForms() {
        return __forms;
    }
    
    public Map<String, DataSchema>  getUriVariables() {
        return  __uriVariables;
    }
    
    public DataSchema   getUriVariable(String s) {
        final DataSchema ret = (__uriVariables == null ? null : __uriVariables.get(s));
        return ret;
    }
    
    public void         putUriVariable(String s, DataSchema d) {
        if (__uriVariables == null)
            __uriVariables = new TreeMap<>();
        
        __uriVariables.put(s, d);
    }

    @Override
    public JSONObject   asJSON() {
        final JSONObject ret = new JSONObject();
        
        addSingleItemOrList("@type", __types, ret);
        
        addString("title",__title, ret);
        addCollection("titles", __titles, ret);
        addString("description",__description,ret);
        addCollection("descriptions",__descriptions,ret);
        addJSONEntityCollection("forms",__forms, ret);
        //addCollection(__title, __titles, ret);"uriVariables", __uriVariables, ret);
        
        
        return ret;
    }
    
    @Override
    public JSONEntity fromJSON(JSONObject o) throws WoTException {
        __title = JSONEntityHelpers.readObject(o, "title", String.class);
        __titles = JSONEntityHelpers.readCollection(o, "titles", String.class,TreeMap.class);
        __description = JSONEntityHelpers.readObject(o, "description", String.class);
        __descriptions = JSONEntityHelpers.readCollection(o, "descriptions", String.class,TreeMap.class);
        __types = JSONEntityHelpers.readObjectSingleOrList(o, "@type", String.class, ArrayList.class);
        __forms = JSONEntityHelpers.readEntityCollection(o, "forms", Form.class, ArrayList.class);
        //checks all form operations
        for(final Form f : __forms ) {
            checkForm(f);
        }
        //__uriVariables 
        return this;
    }
    
    private void checkForm(Form f ) throws WoTException{
        final List<Operation.id> opList = f.getOperationList();
        
        if (opList != null) {
            for(final Operation.id i : opList) {
                if (__allowedOps.contains(i) == false) {
                    throw new InvalidFieldException("operation", i.toString());
                }
            }
        }
        
    }
}
