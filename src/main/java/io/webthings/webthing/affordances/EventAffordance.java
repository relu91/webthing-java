/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.webthings.webthing.affordances;

import io.webthings.webthing.JSONEntity;
import io.webthings.webthing.common.DataSchema;
import io.webthings.webthing.common.JSONEntityHelpers;
import io.webthings.webthing.exceptions.WoTException;
import io.webthings.webthing.forms.Operation;
import java.util.Arrays;
import java.util.Set;
import java.util.TreeSet;
import org.json.JSONObject;

/**
 *
 * @author Lorenzo
 */
public class EventAffordance extends InteractionAffordance  {
    private DataSchema          __subscription;
    private DataSchema          __data;
    private DataSchema          __cancellation;
    private final static  Set<Operation.id>   __allowed_ops = new TreeSet<>(Arrays.asList(Operation.id.subscribeevent, Operation.id.unsubscribeevent));
    
    public EventAffordance() {
        super(__allowed_ops);
        
    }
    
    public EventAffordance(DataSchema s, DataSchema d, DataSchema c) {
        super(__allowed_ops);
        __data =d;
        __subscription = s;
        __cancellation = c;
        
    }
    
    public void setSubscription(DataSchema d) {
        __subscription = d;
    }
    
    public DataSchema getSubscription() {
        return __subscription;
    }
    
    public void setData(DataSchema d) {
        __data = d;
    }
    
    public DataSchema getData() {
        return __data;
    }

    public void setCancellation(DataSchema d) {
        __cancellation = d;
    }
    
    public DataSchema getCancellation() {
        return __cancellation;
    }
    
    @Override
    public JSONObject asJSON() {
        final JSONObject ret = super.asJSON();
        JSONEntityHelpers.addJSONEntity("data", __data, ret);
        JSONEntityHelpers.addJSONEntity("cancellation", __cancellation, ret);
        JSONEntityHelpers.addJSONEntity("subscription", __subscription, ret);
        return ret;
    }
    
    @Override
    public JSONEntity fromJSON(JSONObject o) throws WoTException{
        __data          = JSONEntityHelpers.readEntity(o, "data", DataSchema.class);
        __cancellation  = JSONEntityHelpers.readEntity(o, "cancellation", DataSchema.class);
        __subscription  = JSONEntityHelpers.readEntity(o, "subscription", DataSchema.class);
        super.fromJSON(o);
        
        return this;
    }
  
}
