/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.webthings.webthing.affordances;

import io.webthings.webthing.JSONEntity;
import io.webthings.webthing.forms.Form;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import org.json.JSONObject;

/**
 *
 * @author Lorenzo
 */
public class Interaction extends JSONEntity{
    private     String                   __title;
    private     Map<String,String>       __titles;
    private     String                   __description;
    private     Map<String,String>       __descriptions;
    private     List<String>             __types;
    private     List<Form>              __forms;
    
    public Interaction() {
        
    }
    
    public Interaction(Form f ) {
        __forms = new ArrayList<>();
        __forms.add(f);
    }
    
    public Interaction(String type, String title, String desc, Form f ) {
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
    
    public JSONObject   asJSON() {
        final JSONObject ret = new JSONObject();
        
        addSingleItemOrList("@type", __types, ret);
        
        addString("title",__title, ret);
        addCollection("titles", __titles, ret);
        addString("description",__description,ret);
        addCollection("descriptions",__descriptions,ret);
        addJSONEntityCollection("forms",__forms, ret);
        return ret;
    }
    public void addForm(Form f )  {
        __forms.add(f);
    }
    
    public List<Form>  getForms() {
        return __forms;
    }
    
    
}
