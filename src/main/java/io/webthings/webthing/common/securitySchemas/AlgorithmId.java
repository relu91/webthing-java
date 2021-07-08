/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.webthings.webthing.common.securitySchemas;

import static io.webthings.webthing.common.securitySchemas.AlgorithmId.typeId.aiES256;
import static io.webthings.webthing.common.securitySchemas.AlgorithmId.typeId.aiMD5;
import io.webthings.webthing.exceptions.InvalidFieldException;
import io.webthings.webthing.exceptions.WoTException;

/**
 *
 * @author Lorenzo
 */
public class AlgorithmId {
    public enum typeId {
        aiMD5,
        aiES256,
        aiES512_256
    }
    
    public static typeId decodeTypeId(String s ) throws WoTException {
        if (s == null || s.length() == 0 )
            return null;
        
        typeId ret = null;
        
        switch(s) {
            case "MD5":
                ret = typeId.aiMD5;
                break;
            case "ES256":
                ret = typeId.aiES256;
                break;
            case "ES512-256":
                ret = typeId.aiES512_256;
                break;
            default:
                throw new InvalidFieldException("alg",s);
        }
        
        return ret;
    }
    
    public static String decodeTypeId(typeId id) {
        if (id == null)
            return null;
        
        String ret = null;
       
        switch(id) {
            case aiES256:
                ret = "ES256";
                break;
            case aiES512_256:
                ret = "ES512-256";
                break;
            case aiMD5:
                ret = "MD5";
                break;
        }
        
        return ret;
    }
}
