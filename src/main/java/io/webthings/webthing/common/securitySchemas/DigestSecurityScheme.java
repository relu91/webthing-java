package io.webthings.webthing.common.securitySchemas;

import io.webthings.webthing.JSONEntity;
import io.webthings.webthing.common.JSONEntityHelpers;
import io.webthings.webthing.exceptions.WoTException;
import org.json.JSONObject;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Lorenzo
 */
public class DigestSecurityScheme extends BasicSecurityScheme{
    public DigestSecurityScheme() {
        super(typeId.siDigest);
    }
    private QOPId.typeId           __qop;
    
    public QOPId.typeId     getQOP() {
        return  __qop;
    }
    
    public void setQOP(QOPId.typeId id) {
        __qop = id;
    }
    @Override
    public JSONObject asJSON() throws WoTException {
        final JSONObject ret = super.asJSON();
        final String s = QOPId.decodeId(__qop);
        JSONEntityHelpers.addObject("qop", s, ret);
        return ret;
    }
    
    @Override 
    public JSONEntity fromJSON(JSONObject o) throws WoTException {
        super.fromJSON(o);
        final String s = JSONEntityHelpers.readObject(o, "qop", String.class);
        __qop = QOPId.decodeId(s);
        
        return this;
    }
}
