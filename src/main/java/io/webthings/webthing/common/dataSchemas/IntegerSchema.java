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
import org.json.JSONObject;

/**
 *
 * @author Lorenzo
 */
public class IntegerSchema extends DataSchema{
    private Integer  __minimum;
    private Integer  __maximum;
    
    @Override
    public typeId getJSONType() {
        return typeId.tiInteger;
    }
    
    @Override
    public JSONEntity fromJSON(JSONObject o) throws WoTException {
        super.fromJSON(o);
        __minimum = JSONEntityHelpers.readObject(o, "minimum", Integer.class);
        __maximum = JSONEntityHelpers.readObject(o, "maximum", Integer.class);
        
        return this;
    }

    @Override
    public JSONObject   asJSON() {
        final JSONObject ret = super.asJSON();
        JSONEntityHelpers.addObject("minimum", __minimum, ret);
        JSONEntityHelpers.addObject("maximum", __maximum, ret);
        return ret;
    }    
    
    public Integer getMinimum() {
        return __minimum;
    }
    public Integer getMaximum() {
        return __maximum;
    }
    
    public void setMinimum(Integer d) {
        __minimum = d;
    }
    
    public void setMaximum(Integer d ) {
        __maximum = d;
    }
    
}
