/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.webthings.webthing.common.securitySchemas;

import io.webthings.webthing.exceptions.InvalidFieldException;
import io.webthings.webthing.exceptions.WoTException;

/**
 *
 * @author Lorenzo
 */
public class FormatId {
    public enum typeId {
        fiJWT,
        fiCWT,
        fiJWE,
        fiJWS
    }
    
    public static typeId decodeTypeId(String s ) throws WoTException {
        if (s == null || s.length() == 0 )
            return null;
        
        typeId ret = null;
        
        switch(s) {
            case "jwt":
                ret = typeId.fiJWT;
                break;
            case "cwt":
                ret = typeId.fiCWT;
                break;
            case "jwe":
                ret = typeId.fiJWE;
                break;
            case "jws":
                ret = typeId.fiJWS;
                break;
            default:
                throw new InvalidFieldException("alg",s);
        }
        
        return ret;
    }
    
    
    public static String decodeTypeId(typeId id  ) {
        if (id == null)
            return null;
        
        String ret = null;
        switch(id) {
            case fiCWT:
                ret = "cwt";
                break;
            case fiJWE:
                ret = "jwe";
                break;
            case fiJWS: 
                ret = "jws";
                break;
            case fiJWT:
                ret = "jwt";
                break;
        }
        
        return ret;
    }
}
