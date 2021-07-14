/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.webthings.webthing.server;

import fi.iki.elonen.NanoHTTPD;
import fi.iki.elonen.router.RouterNanoHTTPD;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import org.json.JSONObject;

/**
 *
 * @author Lorenzo
 */
public class BaseHandler implements RouterNanoHTTPD.UriResponder {
        /**
         * Add necessary CORS headers to response.
         *
         * @param response Response to add headers to
         * @return The Response object.
         */
        public NanoHTTPD.Response corsResponse(NanoHTTPD.Response response) {
            response.addHeader("Access-Control-Allow-Origin", "*");
            response.addHeader("Access-Control-Allow-Headers",
                               "Origin, X-Requested-With, Content-Type, Accept");
            response.addHeader("Access-Control-Allow-Methods",
                               "GET, HEAD, PUT, POST, DELETE");
            return response;
        }

        /**
         * Handle a GET request.
         *
         * @param uriResource The URI resource that was matched
         * @param urlParams   Map of URL parameters
         * @param session     The HTTP session
         * @return 405 Method Not Allowed response.
         */
        public NanoHTTPD.Response get(RouterNanoHTTPD.UriResource uriResource,
                            Map<String, String> urlParams,
                            NanoHTTPD.IHTTPSession session) {
            return corsResponse(NanoHTTPD.newFixedLengthResponse(NanoHTTPD.Response.Status.METHOD_NOT_ALLOWED,
                                                                 null,
                                                                 null));
        }

        /**
         * Handle a PUT request.
         *
         * @param uriResource The URI resource that was matched
         * @param urlParams   Map of URL parameters
         * @param session     The HTTP session
         * @return 405 Method Not Allowed response.
         */
        public NanoHTTPD.Response put(RouterNanoHTTPD.UriResource uriResource,
                            Map<String, String> urlParams,
                            NanoHTTPD.IHTTPSession session) {
            return corsResponse(NanoHTTPD.newFixedLengthResponse(NanoHTTPD.Response.Status.METHOD_NOT_ALLOWED,
                                                                 null,
                                                                 null));
        }

        /**
         * Handle a POST request.
         *
         * @param uriResource The URI resource that was matched
         * @param urlParams   Map of URL parameters
         * @param session     The HTTP session
         * @return 405 Method Not Allowed response.
         */
        public NanoHTTPD.Response post(RouterNanoHTTPD.UriResource uriResource,
                             Map<String, String> urlParams,
                             NanoHTTPD.IHTTPSession session) {
            return corsResponse(NanoHTTPD.newFixedLengthResponse(NanoHTTPD.Response.Status.METHOD_NOT_ALLOWED,
                                                                 null,
                                                                 null));
        }

        /**
         * Handle a DELETE request.
         *
         * @param uriResource The URI resource that was matched
         * @param urlParams   Map of URL parameters
         * @param session     The HTTP session
         * @return 405 Method Not Allowed response.
         */
        public NanoHTTPD.Response delete(RouterNanoHTTPD.UriResource uriResource,
                               Map<String, String> urlParams,
                               NanoHTTPD.IHTTPSession session) {
            return corsResponse(NanoHTTPD.newFixedLengthResponse(NanoHTTPD.Response.Status.METHOD_NOT_ALLOWED,
                                                                 null,
                                                                 null));
        }

        /**
         * Handle any other request.
         *
         * @param method      The HTTP method
         * @param uriResource The URI resource that was matched
         * @param urlParams   Map of URL parameters
         * @param session     The HTTP session
         * @return 405 Method Not Allowed response.
         */
        public NanoHTTPD.Response other(String method,
                              RouterNanoHTTPD.UriResource uriResource,
                              Map<String, String> urlParams,
                              NanoHTTPD.IHTTPSession session) {
            if (method.equals("OPTIONS")) {
                return corsResponse(NanoHTTPD.newFixedLengthResponse(NanoHTTPD.Response.Status.NO_CONTENT,
                                                                     null,
                                                                     null));
            }

            return corsResponse(NanoHTTPD.newFixedLengthResponse(NanoHTTPD.Response.Status.METHOD_NOT_ALLOWED,
                                                                 null,
                                                                 null));
        }

        /**
         * Parse a JSON body.
         *
         * @param session The HTTP session
         * @return The parsed JSON body as a JSONObject, or null on error.
         */
        public JSONObject parseBody(NanoHTTPD.IHTTPSession session) {
            int contentLength = Integer.parseInt(session.getHeaders()
                                                        .get("content-length"));
            byte[] buffer = new byte[contentLength];
            try {
                session.getInputStream().read(buffer, 0, contentLength);
                return new JSONObject(new String(buffer));
            } catch (IOException e) {
                return null;
            }
        }

  
        /**
         * Validate Host header.
         *
         * @param uriResource The URI resource that was matched
         * @param session     The HTTP session
         * @return Boolean indicating validation success.
         */
        public boolean validateHost(RouterNanoHTTPD.UriResource uriResource,
                                    NanoHTTPD.IHTTPSession session) {
            boolean disableHostValidation =
                    uriResource.initParameter(2, Boolean.class);

            if (disableHostValidation) {
                return true;
            }

            List<String> hosts = uriResource.initParameter(0, List.class);

            String host = session.getHeaders().get("host");
            return (host != null && hosts.contains(host.toLowerCase()));
        }

        /**
         * Determine whether or not this request is HTTPS.
         *
         * @param uriResource The URI resource that was matched
         * @return Boolean indicating whether or not the request is secure.
         */
        public boolean isSecure(RouterNanoHTTPD.UriResource uriResource) {
            return uriResource.initParameter(2, Boolean.class);
        }
    }

