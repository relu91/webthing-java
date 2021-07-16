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
public class PropertyAffordance extends InteractionAffordance{
    private final static  Set<Operation.id>   __allowed_ops = new TreeSet<>(Arrays.asList(Operation.id.readproperty, Operation.id.writeproperty, Operation.id.observeproperty, Operation.id.unobserveproperty));
    private Boolean                     __observable;
    private DataSchema                  __ds;
    public PropertyAffordance() {
        super(__allowed_ops);
    }
    
    public Boolean getObservable() {
        return __observable;
    }
    
    public void setObservable(boolean b ) {
        __observable = b;
    }
    
    public void setDataSchema(DataSchema ds) {
        __ds = ds;
    }
    
    public DataSchema getDataSchema() {
        return __ds;
    }
    @Override
    public JSONObject asJSON() {
        final JSONObject  ret = super.asJSON();
        final JSONObject  jsDS = (__ds == null ? null : __ds.asJSON());
        
        //do a conditioned copy
        if (jsDS != null) {
            final Set<String> dsKeys = jsDS.keySet();
            
           for(final String key : dsKeys )  {
               final Object o = jsDS.get(key);
               if ( o != null) {
                   ret.put(key, o);
                   continue;
               }
           }
        }
        JSONEntityHelpers.addObject("observable", __observable, ret);
        return ret;
    }
    
    @Override
    public JSONEntity fromJSON(JSONObject o ) throws WoTException{
        __observable = JSONEntityHelpers.readObject(o, "observable", Boolean.class);
        super.fromJSON(o);

        __ds = DataSchema.newInstance(o);
        return this;
    }
}
