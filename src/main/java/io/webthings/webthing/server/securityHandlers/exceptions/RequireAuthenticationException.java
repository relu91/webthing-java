/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.webthings.webthing.server.securityHandlers.exceptions;

import io.webthings.webthing.exceptions.WoTException;

/**
 * 
 * @author Lorenzo
 */
public class RequireAuthenticationException extends WoTException{
    private String  __headerContent = "Needs Authentication";
    public RequireAuthenticationException(String headerContent) {
        super("", "");
        __headerContent = headerContent;
    }
    public String getHeaderContent() {
        return __headerContent;
    }
}
