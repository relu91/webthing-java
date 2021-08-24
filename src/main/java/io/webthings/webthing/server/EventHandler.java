package io.webthings.webthing.server;

import fi.iki.elonen.NanoHTTPD;

import static fi.iki.elonen.NanoHTTPD.newFixedLengthResponse;

import fi.iki.elonen.NanoWSD;
import fi.iki.elonen.router.RouterNanoHTTPD;
import io.webthings.webthing.affordances.InteractionAffordance;
import io.webthings.webthing.exceptions.WoTException;
import io.webthings.webthing.forms.Form;
import io.webthings.webthing.forms.Operation;
import io.webthings.webthing.server.securityHandlers.exceptions.RequireAuthenticationException;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author Lorenzo
 */
public class EventHandler extends BaseHandler {
    private class BadRequest extends Exception {
    }

    private class BadAccess extends Exception {
    }

    public EventHandler() {

    }

    @Override
    public NanoHTTPD.Response get(RouterNanoHTTPD.UriResource uriResource,
                                  Map<String, String> urlParams,
                                  NanoHTTPD.IHTTPSession session) {
        //handles websocket for observer/unobserver property
        Map<String, String> headers = session.getHeaders();
        if (isWebSocketRequested(session)) {
            if (!NanoWSD.HEADER_WEBSOCKET_VERSION_VALUE.equalsIgnoreCase(headers.get(
                    NanoWSD.HEADER_WEBSOCKET_VERSION))) {
                return corsResponse(newFixedLengthResponse(NanoHTTPD.Response.Status.BAD_REQUEST,
                                                           NanoHTTPD.MIME_PLAINTEXT,
                                                           "Invalid Websocket-Version " +
                                                                   headers.get(
                                                                           NanoWSD.HEADER_WEBSOCKET_VERSION)));
            }

            if (!headers.containsKey(NanoWSD.HEADER_WEBSOCKET_KEY)) {
                return corsResponse(newFixedLengthResponse(NanoHTTPD.Response.Status.BAD_REQUEST,
                                                           NanoHTTPD.MIME_PLAINTEXT,
                                                           "Missing Websocket-Key"));
            }

            final String path = "/" + uriResource.getUri();
            final NanoWSD.WebSocket webSocket =
                    new ThingWebSocket(session, false, path);

            NanoHTTPD.Response handshakeResponse =
                    webSocket.getHandshakeResponse();
            try {
                handshakeResponse.addHeader(NanoWSD.HEADER_WEBSOCKET_ACCEPT,
                                            NanoWSD.makeAcceptKey(headers.get(
                                                    NanoWSD.HEADER_WEBSOCKET_KEY)));
            } catch (NoSuchAlgorithmException e) {
                return corsResponse(newFixedLengthResponse(NanoHTTPD.Response.Status.INTERNAL_ERROR,
                                                           NanoHTTPD.MIME_PLAINTEXT,
                                                           "The SHA-1 Algorithm required for websockets is not available on the server."));
            }

            if (headers.containsKey(NanoWSD.HEADER_WEBSOCKET_PROTOCOL)) {
                handshakeResponse.addHeader(NanoWSD.HEADER_WEBSOCKET_PROTOCOL,
                                            headers.get(NanoWSD.HEADER_WEBSOCKET_PROTOCOL)
                                                   .split(",")[0]);
            }

            final Timer timer = new Timer();
            timer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    try {
                        webSocket.ping(new byte[0]);
                    } catch (IOException e) {
                        timer.cancel();
                    }
                }
            }, WEBSOCKET_PING_INTERVAL, WEBSOCKET_PING_INTERVAL);

            return handshakeResponse;
        }


        return corsResponse(NanoHTTPD.newFixedLengthResponse(NanoHTTPD.Response.Status.BAD_REQUEST,
                                                             null,
                                                             null));
    }

    @Override
    public NanoHTTPD.Response post(RouterNanoHTTPD.UriResource uriResource,
                                   Map<String, String> urlParams,
                                   NanoHTTPD.IHTTPSession session) {
        return corsResponse(NanoHTTPD.newFixedLengthResponse(NanoHTTPD.Response.Status.BAD_REQUEST,
                                                             null,
                                                             null));
    }

    /**
     * Handle a PUT request.
     *
     * @param uriResource The URI resource that was matched
     * @param urlParams   Map of URL parameters
     * @param session     The HTTP session
     * @return The appropriate response.
     */
    @Override
    public NanoHTTPD.Response put(RouterNanoHTTPD.UriResource uriResource,
                                  Map<String, String> urlParams,
                                  NanoHTTPD.IHTTPSession session) {
        return corsResponse(NanoHTTPD.newFixedLengthResponse(NanoHTTPD.Response.Status.BAD_REQUEST,
                                                             null,
                                                             null));
    }
}
