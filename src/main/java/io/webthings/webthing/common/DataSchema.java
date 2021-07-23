/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.webthings.webthing.common;

import io.webthings.webthing.JSONEntity;
import static io.webthings.webthing.common.JSONEntityHelpers.addCollection;
import static io.webthings.webthing.common.JSONEntityHelpers.addJSONEntityCollection;
import static io.webthings.webthing.common.JSONEntityHelpers.addObject;
import static io.webthings.webthing.common.JSONEntityHelpers.addSingleItemOrList;
import static io.webthings.webthing.common.JSONEntityHelpers.addString;
import static io.webthings.webthing.common.JSONEntityHelpers.checkedInitList;
import io.webthings.webthing.common.dataSchemas.ArraySchema;
import io.webthings.webthing.common.dataSchemas.BooleanSchema;
import io.webthings.webthing.common.dataSchemas.IntegerSchema;
import io.webthings.webthing.common.dataSchemas.NullSchema;
import io.webthings.webthing.common.dataSchemas.NumberSchema;
import io.webthings.webthing.common.dataSchemas.ObjectSchema;
import io.webthings.webthing.common.dataSchemas.StringSchema;
import io.webthings.webthing.exceptions.WoTException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import org.json.JSONObject;

/**
 *
 * @author Lorenzo
 */
public class DataSchema extends JSONEntity {
    public   enum   typeId {
        tiObject,
        tiArray,
        tiNumber,
        tiBoolean,
        tiString,
        tiInteger,
        tiNull
    }
    private     String                   __title;
    private     Map<String,String>       __titles;
    private     String                   __description;
    private     Map<String,String>       __descriptions;
    private     List<String>             __types;
    
    private     typeId                  __jsonType;
    private     Object                  __const;
    private     String                  __unit;
    private     List<DataSchema>        __oneOf;
    private     List<Object>            __enum;
    private     Boolean                 __readOnly;
    private     Boolean                 __writeOnly;
    private     String                  __format;
    
    public DataSchema() {
        //when type is not specified !!! 
        __jsonType = null;
        
    }
    protected DataSchema(typeId id) {
        __jsonType = id;
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
    public void setReadOnly(boolean f) {
        __readOnly = f;
    }
    public boolean getReadOnly() {
        return  __readOnly;
    }
    public void setWriteOnly(boolean f) {
        __writeOnly = f;
    }
    public boolean getWriteOnly() {
        return  __writeOnly;
    }
       
    public typeId getJSONType() {
        return __jsonType;
    }
 /*   
    public void setJSONType(typeId s ) {
        __jsonType = s;
    }
*/    
    public List<DataSchema> getOneOf() {
        return __oneOf;
    }
    
    public void addOneOf(DataSchema d) {
        if (__oneOf == null)
            __oneOf = new ArrayList<>();
        
        __oneOf.add(d);
    }
    
    public List<Object> getEnum() {
        return  __enum;
    }
    
    public void addEnum(Object o ) {
        if (__enum == null)
            __enum = new ArrayList<>();
        
        __enum.add(o);
    }
    
    public void setFormat(String s ) {
        __format = s;
    }
    
    public String getFormat() {
        return __format;
    }
    public JSONObject   asJSON() {
        final JSONObject ret = new JSONObject();
        
        addSingleItemOrList("@type", __types, ret);
        
        addString("title",__title, ret);
        addCollection("titles", __titles, ret);
        addString("description",__description,ret);
        addCollection("descriptions",__descriptions,ret);
        addString("type",decodeTypeId(__jsonType),ret);
        addCollection("enum", __enum, ret);
        addJSONEntityCollection("oneOf", __oneOf, ret);
        addObject("readOnly", __readOnly, ret);
        addObject("writeOnly", __writeOnly, ret);
        addString("format", __format, ret);
        return ret;
    }

    @Override
    public JSONEntity fromJSON(JSONObject o) throws WoTException {
        __title = JSONEntityHelpers.readObject(o, "title", String.class);
        __titles = JSONEntityHelpers.readCollection(o, "titles", String.class,TreeMap.class);
        __description = JSONEntityHelpers.readObject(o, "description", String.class);
        __descriptions = JSONEntityHelpers.readCollection(o, "descriptions", String.class,TreeMap.class);
        __types = JSONEntityHelpers.readObjectSingleOrList(o, "@type", String.class, ArrayList.class);
        final String jsonType = JSONEntityHelpers.readObject(o, "type", String.class);
        __jsonType = decodeTypeId(jsonType);
        __const = JSONEntityHelpers.readObject(o, "const", Object.class);
        __unit = JSONEntityHelpers.readObject(o, "unit", String.class);
        __enum = JSONEntityHelpers.readCollection(o, "enum", Object.class,ArrayList.class);
        __oneOf = JSONEntityHelpers.readEntityCollection(o, "oneOf", DataSchema.class, ArrayList.class);

          
        return this;
    }
    public static DataSchema newInstance(typeId id) {
        if (id == null)
            return null;
        
        DataSchema ret = null;
        switch(id) {
            case tiArray:
                ret = new ArraySchema();
                break;
            case tiBoolean:
                ret = new BooleanSchema();
                break;
            case tiInteger:
                ret = new IntegerSchema();
                break;
            case tiNull:
                ret = new NullSchema();
                break;
            case tiNumber:
                ret = new NumberSchema();
                break;
            case tiObject:
                ret = new ObjectSchema();
                break;
            case tiString:
                ret = new StringSchema();
                break;
        }
        
        return ret;
    }
    public static DataSchema newInstance(JSONObject o ) throws WoTException {
        final String jsonType = JSONEntityHelpers.readObject(o, "type", String.class);   
        final typeId id = decodeTypeId(jsonType);
        final DataSchema ret = newInstance(id);
        if (ret != null)
            ret.fromJSON(o);
        
        return ret;
    }
    private static typeId decodeTypeId(String id ) {
        typeId ret = null;
        switch(id.trim()) {
            case "array":
                ret = typeId.tiArray;
                break;
            case "boolean":
                ret = typeId.tiBoolean;
                break;
            case "integer":
                ret = typeId.tiInteger;
                break;
            case "null":
                ret = typeId.tiNull;
                break;
            case "number":
                ret = typeId.tiNumber;
                break;
            case  "object":
                ret = typeId.tiObject;
                break;
            case "string":
                ret = typeId.tiString;
                break;
        }
        
        return ret;
    }
    private static String decodeTypeId(typeId id )  {
        if (id == null)
            return null;
        
        String ret = null;
        
        switch(id) {
            case tiArray:
                ret = "array";
                break;
            case tiBoolean:
                ret = "boolean";
                break;
            case tiInteger:
                ret = "integer";
                break;
            case tiNull:
                ret = "null";
                break;
            case tiNumber:
                ret = "number";
                break;
            case  tiObject:
                ret = "object";
                break;
            case tiString:
                ret = "string";
                break;
        }
        
        return ret;
    }
}
