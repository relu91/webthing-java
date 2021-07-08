/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.webthings.webthing.common.securitySchemas;

import io.webthings.webthing.JSONEntity;
import io.webthings.webthing.common.JSONEntityHelpers;
import io.webthings.webthing.exceptions.InvalidFieldException;
import io.webthings.webthing.exceptions.WoTException;
import java.net.URISyntaxException;
import org.json.JSONObject;

/**
 *
 * @author Lorenzo
 */
public class BearerSecurityScheme extends BasicSecurityScheme{
    public BearerSecurityScheme() {
        super(typeId.siBearer);
    }
    
    private java.net.URI            __authorization;
    private AlgorithmId.typeId       __alg;
    private FormatId.typeId          __format;
    public AlgorithmId.typeId getAlgorithm() {
        return __alg;
    }
    
    public FormatId.typeId getFormat() {
        return __format;
    }
    
    public void setAlgorithm(AlgorithmId.typeId id ) {
        __alg = id;
    }
    
    public void setFormat(FormatId.typeId id ) {
        __format = id;
    }
    
    public void setAuthorization(java.net.URI u) {
        __authorization = u;
    }
    
    public java.net.URI getAuthorization() {
        return __authorization;
    }
    @Override
    public JSONObject asJSON() throws WoTException {
        final JSONObject ret = super.asJSON();
        JSONEntityHelpers.addURI("authorization", __authorization , ret);
        JSONEntityHelpers.addString("alg", AlgorithmId.decodeTypeId(__alg), ret);
        JSONEntityHelpers.addString("format", FormatId.decodeTypeId(__format), ret);
        
        
        return ret;
    }
    
    @Override 
    public JSONEntity fromJSON(JSONObject o) throws WoTException {
        super.fromJSON(o);

        __authorization  = JSONEntityHelpers.readURI(o, "authorization");
        
        final String sAlg = JSONEntityHelpers.readObject(o, "alg", String.class);
        __alg = AlgorithmId.decodeTypeId(sAlg);
        
        final String sFmt = JSONEntityHelpers.readObject(o, "format", String.class);
        __format = FormatId.decodeTypeId(sFmt);
        
        return this;
        
        
    }
    
}
