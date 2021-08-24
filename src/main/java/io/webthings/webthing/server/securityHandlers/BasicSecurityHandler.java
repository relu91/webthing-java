package io.webthings.webthing.server.securityHandlers;

import fi.iki.elonen.NanoHTTPD;
import io.webthings.webthing.common.SecurityScheme;
import io.webthings.webthing.exceptions.WoTException;
import io.webthings.webthing.server.SecurityHandler;
import io.webthings.webthing.server.securityHandlers.exceptions.InvalidSecurityException;
import io.webthings.webthing.server.securityHandlers.exceptions.RequireAuthenticationException;

import java.util.Base64;

/**
 * @author Lorenzo
 */
public class BasicSecurityHandler extends SecurityHandler {
    private static String userName = "user";
    private static String pwd = "pwd";
    private static String authString = Base64.getEncoder()
                                             .encodeToString((userName +
                                                                         ":" +
                                                                         pwd).getBytes());
    private static final String BASIC_MARKER = "Basic";

    public static void init(String usr, String pwd) {
        userName = usr;
        BasicSecurityHandler.pwd = pwd;
        authString = Base64.getEncoder()
                           .encodeToString((userName + ":" +
                                                       BasicSecurityHandler.pwd).getBytes());
    }

    @Override
    public boolean doSecurityCheck(SecurityScheme sc,
                                   NanoHTTPD.IHTTPSession session)
            throws WoTException {
        final String sHeader = findHeader("authorization", session);
        if (sHeader == null) {
            throw new RequireAuthenticationException(
                    "Basic : realm=\"Needs user and pwd \"");
        }

        if (sHeader.contains(BASIC_MARKER) == false) {
            throw new InvalidSecurityException("basic");
        }

        final int pos = sHeader.indexOf(BASIC_MARKER);
        final int startAuthPos = pos + BASIC_MARKER.length();

        final String authString = sHeader.substring(startAuthPos).trim();


        return authString.equals(BasicSecurityHandler.authString);
    }
}
