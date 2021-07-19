/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.webthings.webthing.server.securityHandlers;

import fi.iki.elonen.NanoHTTPD;
import io.webthings.webthing.exceptions.WoTException;
import io.webthings.webthing.server.SecurityHandler;

/**
 *
 * @author Lorenzo
 */
public class DigestSecurityHandler extends SecurityHandler {

    @Override
    public boolean doSecurityCheck(NanoHTTPD.IHTTPSession session) throws WoTException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
