/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.webthings.webthing.server.securityHandlers;

import fi.iki.elonen.NanoHTTPD;
import io.webthings.webthing.exceptions.WoTException;
import io.webthings.webthing.server.SecurityHandler;
import io.webthings.webthing.server.securityHandlers.exceptions.InvalidSecurityException;
import io.webthings.webthing.server.securityHandlers.exceptions.RequireAuthenticationException;
import java.util.Base64;

/**
 *
 * @author Lorenzo
 */
public class BasicSecurityHandler extends SecurityHandler{
    private static  String  __user_name =   "user";
    private static  String  __pwd       =   "pwd";
    private static  String  __auth_string   = Base64.getEncoder().encodeToString(  (__user_name + ":" + __pwd).getBytes());
    
    private static final String     BASIC_MARKER = "Basic";
    
    public static void init(String usr, String pwd) {
        __user_name = usr;
        __pwd = pwd;
        __auth_string   = Base64.getEncoder().encodeToString(  (__user_name + ":" + __pwd).getBytes());
    }
    @Override
    public boolean doSecurityCheck(NanoHTTPD.IHTTPSession session) throws WoTException {
        final String sHeader = findHeader("authorization", session);
        if (sHeader == null)
            throw new RequireAuthenticationException("Basic : realm=\"Needs user and pwd \"");
        
        if (sHeader.contains(BASIC_MARKER) == false) {
            throw new InvalidSecurityException("basic");
        }
        
        final int pos = sHeader.indexOf(BASIC_MARKER);
        final int startAuthPos = pos + BASIC_MARKER.length();
        
        final String authString = sHeader.substring(startAuthPos).trim();
        
        
        return authString.equals(__auth_string);
    }
    
}
