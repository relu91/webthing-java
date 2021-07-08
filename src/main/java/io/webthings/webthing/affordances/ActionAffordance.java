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
public class ActionAffordance extends InteractionAffordance {
    private DataSchema                  __input;
    private DataSchema                  __output;
    private Boolean                     __safe;
    private Boolean                     __idempotent;
    private final static  Set<Operation.id>   __allowed_ops = new TreeSet<>(Arrays.asList(Operation.id.invokeaction));
    
    public ActionAffordance() {
        super(__allowed_ops);
    }
    public DataSchema   getInput() {
        return  __input;
    }
    
    public void  setInput(DataSchema    i)  {
        __input = i;
    }
    
    public DataSchema   getOutput() {
        return  __output;
    }
    
    public void         setOutput(DataSchema i) {
        __output = i;
    }
    
    public void         setSafe(boolean f ) {
        __safe = f;
    }
    
    public void         setIdempotent(boolean f ) {
        __idempotent = f;
    }
    
    public Boolean      getSafe() {
        return  __safe;
    }
    public Boolean      getIdempotent() {
        return  __idempotent;
    }
    
    @Override
    public JSONObject   asJSON() { 
        final JSONObject ret = super.asJSON();
        
        JSONEntityHelpers.addObject("input", __input, ret);
        JSONEntityHelpers.addObject("output", __output, ret);
        JSONEntityHelpers.addObject("safe",__safe , ret);
        JSONEntityHelpers.addObject("idempotent",__idempotent , ret);
        
        return ret;
    }
    
    @Override 
    public JSONEntity fromJSON(JSONObject root ) throws WoTException {
        __input = JSONEntityHelpers.readEntity(root, "input", DataSchema.class);
        __output = JSONEntityHelpers.readEntity(root, "output", DataSchema.class);
        __safe = JSONEntityHelpers.readObject(root, "safe", Boolean.class);
        __idempotent = JSONEntityHelpers.readObject(root, "idempotent", Boolean.class);
        
        super.fromJSON(root);
        return this;
    }
}
