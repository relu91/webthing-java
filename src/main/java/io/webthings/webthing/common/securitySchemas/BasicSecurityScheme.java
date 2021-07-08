/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.webthings.webthing.common.securitySchemas;

import io.webthings.webthing.JSONEntity;
import io.webthings.webthing.common.JSONEntityHelpers;
import io.webthings.webthing.common.SecurityScheme;
import io.webthings.webthing.exceptions.WoTException;
import org.json.JSONObject;

/**
 *
 * @author Lorenzo
 */
public class BasicSecurityScheme extends SecurityScheme {
    public BasicSecurityScheme() {
        super(typeId.siBasic);
    }
 
    //used by subclasses 
    
    protected BasicSecurityScheme(typeId id) {
        super(id);
    }
    private LocationId.typeId       __in;
    private String                   __name;
    
    public void setIn(LocationId.typeId in) {
        __in = in;
    }
    
    public void setName(String s ) {
        __name = s;
    }
    
    public LocationId.typeId getIn() {
        return __in;
    }
    
    public String getName() {
        return __name;
    }
    
    @Override
    public JSONObject asJSON() throws WoTException {
        final JSONObject ret = super.asJSON();
        
        final String sType = LocationId.decodeTypeId(__in);
        JSONEntityHelpers.addString("in", sType, ret);
        JSONEntityHelpers.addString("name", __name, ret);
        
        
        return ret;
    }
    
    @Override 
    public JSONEntity fromJSON(JSONObject o) throws WoTException {
        super.fromJSON(o);
        final String in = JSONEntityHelpers.readObject(o, "in", String.class);
        if (in != null) {
            __in = LocationId.decodeTypeId(in);
        }
        
        __name = JSONEntityHelpers.readObject(o, "name", String.class);
        
        
        return this;
    }
    
}
