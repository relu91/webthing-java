package io.webthings.webthing.server.securityHandlers;

import fi.iki.elonen.NanoHTTPD;
import io.webthings.webthing.common.SecurityScheme;
import io.webthings.webthing.exceptions.WoTException;
import io.webthings.webthing.server.SecurityHandler;

/**
 * @author Lorenzo
 */
public class OAuth2SecurityHandler extends SecurityHandler {
    @Override
    public boolean doSecurityCheck(SecurityScheme sc,
                                   NanoHTTPD.IHTTPSession session)
            throws WoTException {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
