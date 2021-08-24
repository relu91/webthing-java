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
 * @author Lorenzo
 */
public class DataSchema extends JSONEntity {
    public enum typeId {
        tiObject, tiArray, tiNumber, tiBoolean, tiString, tiInteger, tiNull
    }

    private String title;
    private Map<String, String> titles;
    private String description;
    private Map<String, String> descriptions;
    private List<String> types;
    private typeId jsonType;
    private Object __const;
    private String unit;
    private List<DataSchema> oneOf;
    private List<Object> __enum;
    private Boolean readOnly;
    private Boolean writeOnly;
    private String format;

    public DataSchema() {
        //when type is not specified !!! 
        jsonType = null;
    }

    protected DataSchema(typeId id) {
        jsonType = id;
    }

    public void setType(String t) {
        types = new ArrayList<>();
        types.add(t);
    }

    public String getType() {
        String ret = null;
        if (types != null && types.size() >= 1) {
            ret = types.get(0);
        }
        return ret;
    }

    public List<String> getTypes() {
        return types;
    }

    public void addType(String t) {
        if (types == null) {
            setType(t);
        } else {
            types.add(t);
        }
    }

    public void setDefaultTitle(String t) {
        title = t;
    }

    public String getDefaultTitle() {
        return title;
    }

    public void setI18NTitle(String lang, String t) {
        if (titles == null) {
            titles = new TreeMap<>();
        }

        titles.put(lang, t);
    }

    public String getI18NTitle(String lang) {
        String ret = null;
        if (titles != null) {
            ret = titles.get(lang);
        }

        return ret;
    }

    public void removeI18NTitle(String lang) {
        if (titles != null) {
            titles.remove(lang);
        }
    }

    public void setDefaultDescription(String d) {
        description = d;
    }

    public String getDefaultDescription() {
        return description;
    }

    public void setI18NDescription(String lang, String d) {
        if (descriptions == null) {
            descriptions = new TreeMap<>();
        }
        descriptions.put(lang, d);
    }

    public String getI18NDescription(String lang) {
        String ret = null;
        if (descriptions != null) {
            ret = descriptions.get(lang);
        }

        return ret;
    }

    public void removeI18NDescription(String lang) {
        if (descriptions != null) {
            descriptions.remove(lang);
        }
    }

    public void setReadOnly(boolean f) {
        readOnly = f;
    }

    public boolean getReadOnly() {
        return readOnly;
    }

    public void setWriteOnly(boolean f) {
        writeOnly = f;
    }

    public boolean getWriteOnly() {
        return writeOnly;
    }

    public typeId getJSONType() {
        return jsonType;
    }

    /*
       public void setJSONType(typeId s ) {
           jsonType = s;
       }
   */
    public List<DataSchema> getOneOf() {
        return oneOf;
    }

    public void addOneOf(DataSchema d) {
        if (oneOf == null) {
            oneOf = new ArrayList<>();
        }

        oneOf.add(d);
    }

    public List<Object> getEnum() {
        return __enum;
    }

    public void addEnum(Object o) {
        if (__enum == null) {
            __enum = new ArrayList<>();
        }

        __enum.add(o);
    }

    public void setFormat(String s) {
        format = s;
    }

    public String getFormat() {
        return format;
    }

    public JSONObject asJSON() {
        final JSONObject ret = new JSONObject();

        addSingleItemOrList("@type", types, ret);

        addString("title", title, ret);
        addCollection("titles", titles, ret);
        addString("description", description, ret);
        addCollection("descriptions", descriptions, ret);
        addString("type", decodeTypeId(jsonType), ret);
        addCollection("enum", __enum, ret);
        addJSONEntityCollection("oneOf", oneOf, ret);
        addObject("readOnly", readOnly, ret);
        addObject("writeOnly", writeOnly, ret);
        addString("format", format, ret);
        return ret;
    }

    @Override
    public JSONEntity fromJSON(JSONObject o) throws WoTException {
        title = JSONEntityHelpers.readObject(o, "title", String.class);
        titles = JSONEntityHelpers.readCollection(o,
                                                  "titles",
                                                  String.class,
                                                  TreeMap.class);
        description =
                JSONEntityHelpers.readObject(o, "description", String.class);
        descriptions = JSONEntityHelpers.readCollection(o,
                                                        "descriptions",
                                                        String.class,
                                                        TreeMap.class);
        types = JSONEntityHelpers.readObjectSingleOrList(o,
                                                         "@type",
                                                         String.class,
                                                         ArrayList.class);
        final String jsonType =
                JSONEntityHelpers.readObject(o, "type", String.class);
        this.jsonType = decodeTypeId(jsonType);
        __const = JSONEntityHelpers.readObject(o, "const", Object.class);
        unit = JSONEntityHelpers.readObject(o, "unit", String.class);
        __enum = JSONEntityHelpers.readCollection(o,
                                                  "enum",
                                                  Object.class,
                                                  ArrayList.class);
        oneOf = JSONEntityHelpers.readEntityCollection(o,
                                                       "oneOf",
                                                       DataSchema.class,
                                                       ArrayList.class);


        return this;
    }

    public static DataSchema newInstance(typeId id) {
        if (id == null) {
            return null;
        }

        DataSchema ret = null;
        switch (id) {
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

    public static DataSchema newInstance(JSONObject o) throws WoTException {
        final String jsonType =
                JSONEntityHelpers.readObject(o, "type", String.class);
        final typeId id = decodeTypeId(jsonType);
        final DataSchema ret = newInstance(id);
        if (ret != null) {
            ret.fromJSON(o);
        }

        return ret;
    }

    private static typeId decodeTypeId(String id) {
        typeId ret = null;
        switch (id.trim()) {
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
            case "object":
                ret = typeId.tiObject;
                break;
            case "string":
                ret = typeId.tiString;
                break;
        }

        return ret;
    }

    private static String decodeTypeId(typeId id) {
        if (id == null) {
            return null;
        }

        String ret = null;

        switch (id) {
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
            case tiObject:
                ret = "object";
                break;
            case tiString:
                ret = "string";
                break;
        }

        return ret;
    }
}
