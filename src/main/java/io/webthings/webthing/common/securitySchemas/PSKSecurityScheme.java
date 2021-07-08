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
public class PSKSecurityScheme extends SecurityScheme{
    public PSKSecurityScheme() {
        super(typeId.siPsk);
    }
    
    private String      __identity;
    public String   getIdentity() {
        return  __identity;
    }
    
    public void setIdentity(String s) {
        __identity = s;
    }
    
    @Override
    public JSONObject asJSON() throws WoTException {
        final JSONObject ret = super.asJSON();
        JSONEntityHelpers.addString("identity", __identity, ret);
        
        return ret;
    }
    
    @Override 
    public JSONEntity fromJSON(JSONObject o) throws WoTException {
        super.fromJSON(o);
        __identity = JSONEntityHelpers.readObject(o, "identity", String.class);
        return this;
    }
    
}
