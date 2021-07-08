/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.webthings.webthing.common.securitySchemas;

/**
 *
 * @author Lorenzo
 */
public class APIKeySecurityScheme extends BasicSecurityScheme{
    public APIKeySecurityScheme() {
        super(typeId.siApikey);
    }
    
}
