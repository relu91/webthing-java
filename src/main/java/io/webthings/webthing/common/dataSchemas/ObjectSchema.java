/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.webthings.webthing.common.dataSchemas;

import io.webthings.webthing.JSONEntity;
import io.webthings.webthing.common.DataSchema;
import io.webthings.webthing.common.JSONEntityHelpers;
import io.webthings.webthing.exceptions.WoTException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.json.JSONObject;

/**
 *
 * @author Lorenzo
 */
public class ObjectSchema extends DataSchema{
    
    private     Map<String,DataSchema>      __properties;
    private     List<String>                __required;
    
    @Override
    public typeId getJSONType() {
        return typeId.tiObject;
    }
    
    @Override
    public JSONEntity fromJSON(JSONObject o) throws WoTException {
        super.fromJSON(o);
        __required = JSONEntityHelpers.readCollection(o, "required", String.class, ArrayList.class);
        return this;
    }

    @Override
    public JSONObject   asJSON() {
        final JSONObject ret = super.asJSON();
        JSONEntityHelpers.addCollection("required", __required, ret);
        return ret;
    }    
    
    public Map<String,DataSchema>  getProperties() {
        return __properties;
    }
    
    public void setProperties(Map<String,DataSchema>  o) {
        __properties = o;
    }
    
    public List<String>  getRequired() {
        return __required;
    }
    
    public void setRequired(List<String>  o ) {
        __required = o;
    }
}
