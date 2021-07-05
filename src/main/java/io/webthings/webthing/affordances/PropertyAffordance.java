/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.webthings.webthing.affordances;

import io.webthings.webthing.JSONEntity;
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
    private static  Set<Operation.id>   __allowed_ops = new TreeSet<>(Arrays.asList(Operation.id.readproperty, Operation.id.writeproperty, Operation.id.observeproperty, Operation.id.unobserveproperty));
    public Boolean      __observable;
    public PropertyAffordance() {
        super(__allowed_ops);
    }
    
    public Boolean getObservable() {
        return __observable;
    }
    
    public void setObservable(boolean b ) {
        __observable = b;
    }
    
    @Override
    public JSONObject asJSON() {
        final JSONObject  ret = super.asJSON();
        JSONEntityHelpers.addObject("observable", __observable, ret);
        return ret;
    }
    
    @Override
    public JSONEntity fromJSON(JSONObject o ) throws WoTException{
        __observable = JSONEntityHelpers.readObject(o, "observable", Boolean.class);
        super.fromJSON(o);
        return this;
    }
}
