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
import org.json.JSONObject;

/**
 *
 * @author Lorenzo
 */
public class ArraySchema extends DataSchema{
    
    private List<DataSchema>        __items;
    private Integer                 __minItems;
    private Integer                 __maxItems;
    @Override
    public typeId getJSONType() {
        return typeId.tiArray;
    }
    
    @Override
    public JSONEntity fromJSON(JSONObject o) throws WoTException {
        super.fromJSON(o);
        __minItems = JSONEntityHelpers.readObject(o, "minItems", Integer.class);
        __maxItems = JSONEntityHelpers.readObject(o, "maxItems", Integer.class);
        __items = JSONEntityHelpers.readSingleEntityOrCollection(o, "items", DataSchema.class, ArrayList.class);
        
        return this;
    }
    
    @Override
    public JSONObject   asJSON() {
        final JSONObject ret = super.asJSON();
        JSONEntityHelpers.addObject("minItems", __minItems, ret);
        JSONEntityHelpers.addObject("maxItems", __maxItems, ret);
        JSONEntityHelpers.addJSONSingleEntityOrCollection("items",__items, ret);
        
        return ret;
    }

    public Integer getMaxItems() {
        return __maxItems;
    }
    
    public Integer getMinItems() {
        return __minItems;
    }
    
    public List<DataSchema>     getItems() {
        return  __items;
    }
    
    public void setMaxItems(Integer i) {
        __maxItems = i;
    }
    public void setMinItems(Integer i) {
        __minItems = i ;
    }
    public void setItems(List<DataSchema> l ) {
        __items = l ;
    }
}
