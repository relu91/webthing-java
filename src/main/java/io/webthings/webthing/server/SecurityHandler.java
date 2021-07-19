/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.webthings.webthing.server;

import fi.iki.elonen.NanoHTTPD;
import io.webthings.webthing.common.SecurityScheme;
import io.webthings.webthing.common.ThingData;
import io.webthings.webthing.exceptions.WoTException;
import io.webthings.webthing.server.securityHandlers.exceptions.InvalidSecurityException;
import io.webthings.webthing.forms.Form;
import io.webthings.webthing.server.securityHandlers.BasicSecurityHandler;
import io.webthings.webthing.server.securityHandlers.DigestSecurityHandler;
import io.webthings.webthing.server.securityHandlers.NoSecurityHandler;
import io.webthings.webthing.server.securityHandlers.OAuth2SecurityHandler;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author Lorenzo
 */
public abstract class SecurityHandler {
    
    public abstract boolean doSecurityCheck(NanoHTTPD.IHTTPSession  session) throws WoTException;
    
    public static boolean checkAccess (
        ThingData                   td,
        Form                        f,
        NanoHTTPD.IHTTPSession  session
    )throws WoTException{
        Set<String>    securityNames = f.getSecurity();
        
        if (securityNames == null || securityNames.size() == 0 ) {
            securityNames = td.getSecurities();
        }
        
        if (securityNames == null || securityNames.size() == 0 ) {
            return false;   //a security of some sort must exist, reject loaded thing
        }
        
        try {
            boolean ret = true;
            for(final String name : securityNames) {
                final SecurityHandler sh = SecurityHandler.newInstance(name, td);
                ret = ret && sh.doSecurityCheck(session);
            }
            return ret;
        } catch(InvalidSecurityException e ) {
            return false;
        }
        
        
    }
    
    private static SecurityHandler newInstance(String name,ThingData td) throws InvalidSecurityException{
        //check 
        final Map<String, SecurityScheme> secMap = td.getSecurityDefinitions();
        
        if (secMap ==  null || secMap.size() == 0 ) {
            throw new InvalidSecurityException(name);
        }
        
        final SecurityScheme sc = secMap.get(name);
        
        if (sc == null) {
            throw new InvalidSecurityException(name);
        }
        
        
        final SecurityScheme.typeId id  = sc.getScheme();
        
        SecurityHandler ret = null;
        switch(id) {
            case siApikey:
                break;
            case siBasic:
                ret = new BasicSecurityHandler();
                break;
            case siBearer:
                break;
            case siDigest:
                ret = new DigestSecurityHandler();
                break;
            case siNosec:
                ret = new NoSecurityHandler();
                break;
            case siOauth2:
                ret = new OAuth2SecurityHandler();
                break;
            case siPsk:
                break;
        }
        
        return ret;
    }
   
    protected static String findHeader(String name, NanoHTTPD.IHTTPSession sess) {
        String ret = null;
        final Map<String,String> headers = sess.getHeaders();
        if (headers != null) {
            ret = headers.get(name);
        }
        
        return ret;
    }
}
