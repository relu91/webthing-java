package io.webthings.webthing.server.securityHandlers;

import fi.iki.elonen.NanoHTTPD;
import io.webthings.webthing.common.SecurityScheme;
import io.webthings.webthing.server.SecurityHandler;

/**
 * @author Lorenzo
 */
public class NoSecurityHandler extends SecurityHandler {
    @Override
    public boolean doSecurityCheck(SecurityScheme sc,
                                   NanoHTTPD.IHTTPSession session) {
        return true;
    }
}
