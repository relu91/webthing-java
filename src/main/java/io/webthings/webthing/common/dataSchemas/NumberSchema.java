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
public class NumberSchema extends DataSchema{
    private Double  __minimum;
    private Double  __maximum;
    
    public NumberSchema() {
        super(typeId.tiNumber);
    }
    
    @Override
    public JSONEntity fromJSON(JSONObject o) throws WoTException {
        super.fromJSON(o);
        __minimum = JSONEntityHelpers.readObject(o, "minimum", Double.class);
        __maximum = JSONEntityHelpers.readObject(o, "maximum", Double.class);
        
        return this;
    }

    @Override
    public JSONObject   asJSON() {
        final JSONObject ret = super.asJSON();
        JSONEntityHelpers.addObject("minimum", __minimum, ret);
        JSONEntityHelpers.addObject("maximum", __maximum, ret);
        return ret;
    }    
    
    public Double getMinimum() {
        return __minimum;
    }
    public Double getMaximum() {
        return __maximum;
    }
    
    public void setMinimum(Double d) {
        __minimum = d;
    }
    
    public void setMaximum(Double d ) {
        __maximum = d;
    }
    
}
