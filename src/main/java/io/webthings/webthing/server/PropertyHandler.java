/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
 *
 * @author Lorenzo
 */
public class PropertyHandler  extends BaseHandler{
    private class BadRequest extends Exception {
        
    }
    private class BadAccess extends Exception {
        
    }
    
    public PropertyHandler() {
        
    }
    @Override
    public NanoHTTPD.Response get(
        RouterNanoHTTPD.UriResource uriResource,
        Map<String, String> urlParams,
        NanoHTTPD.IHTTPSession session
    ) {
        //handles websocket for observer/unobserver property
            Map<String, String> headers = session.getHeaders();
            if (isWebSocketRequested(session)) {
                if (!NanoWSD.HEADER_WEBSOCKET_VERSION_VALUE.equalsIgnoreCase(
                        headers.get(NanoWSD.HEADER_WEBSOCKET_VERSION))) {
                    return corsResponse(
                            newFixedLengthResponse(
                                NanoHTTPD.Response.Status.BAD_REQUEST,
                                NanoHTTPD.MIME_PLAINTEXT,
                                "Invalid Websocket-Version " +
                                headers.get(NanoWSD.HEADER_WEBSOCKET_VERSION)
                        )
                    );
                }

                if (!headers.containsKey(NanoWSD.HEADER_WEBSOCKET_KEY)) {
                    return corsResponse(
                        newFixedLengthResponse(
                            NanoHTTPD.Response.Status.BAD_REQUEST,
                            NanoHTTPD.MIME_PLAINTEXT,
                            "Missing Websocket-Key"
                        )
                    );
                }

                final String                path = "/" +  uriResource.getUri();
                final NanoWSD.WebSocket webSocket = new ThingWebSocket(session,true,path);
                
                NanoHTTPD.Response handshakeResponse = webSocket.getHandshakeResponse();
                try {
                    handshakeResponse.addHeader(
                        NanoWSD.HEADER_WEBSOCKET_ACCEPT,
                        NanoWSD.makeAcceptKey(
                            headers.get(
                            NanoWSD.HEADER_WEBSOCKET_KEY
                            )
                        )
                    );
                } catch (NoSuchAlgorithmException e) {
                    return corsResponse(
                        newFixedLengthResponse(
                            NanoHTTPD.Response.Status.INTERNAL_ERROR,
                            NanoHTTPD.MIME_PLAINTEXT,
                            "The SHA-1 Algorithm required for websockets is not available on the server."
                        )
                    );
                }

                if (headers.containsKey(NanoWSD.HEADER_WEBSOCKET_PROTOCOL)) {
                    handshakeResponse.addHeader(
                        NanoWSD.HEADER_WEBSOCKET_PROTOCOL,
                        headers.get(NanoWSD.HEADER_WEBSOCKET_PROTOCOL).split(",")[0]
                    );
                }

                final Timer timer = new Timer();
                timer.scheduleAtFixedRate(
                    new TimerTask() {
                        @Override
                        public void run() {
                            try {
                                webSocket.ping(new byte[0]);
                            } catch (IOException e) {
                                timer.cancel();
                            }
                        }
                    },
                    WEBSOCKET_PING_INTERVAL,
                    WEBSOCKET_PING_INTERVAL
                );

                return handshakeResponse;
            }
        
        
        
        return handleProperty(uriResource, urlParams, session, "GET");
    }

    @Override
    public NanoHTTPD.Response post(
        RouterNanoHTTPD.UriResource uriResource,
        Map<String, String> urlParams,
        NanoHTTPD.IHTTPSession session
    )    {
        return handleProperty(uriResource, urlParams, session, "POST");
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
    public NanoHTTPD.Response put(
        RouterNanoHTTPD.UriResource uriResource,
        Map<String, String> urlParams,
        NanoHTTPD.IHTTPSession session
    ) {
        return handleProperty(uriResource, urlParams, session, "PUT");
    }
    
    //inner generic handle
    
    private NanoHTTPD.Response handleProperty(
        RouterNanoHTTPD.UriResource uriResource,
        Map<String, String>         urlParams,
        NanoHTTPD.IHTTPSession      session,
        String                      methodName
    ){
        if (!validateHost(uriResource, session)) {
            return NanoHTTPD.newFixedLengthResponse(
                NanoHTTPD.Response.Status.FORBIDDEN,
                null,
                null
            );
        }
        
        final ManagedThingsCollection mti = ManagedThingsCollection.getInstance();
        final String                path = "/" +  uriResource.getUri();
        final InteractionAffordance ia = mti.getInteraction(path);
        final String                iName = mti.getInteractionName(path);
        final ThingObject           owner = mti.getInteractionOwner(path);
        
        
        if (ia == null || iName == null || owner == null) {
            return corsResponse(NanoHTTPD.newFixedLengthResponse(
                    NanoHTTPD.Response.Status.NOT_FOUND,
                    null,
                    null)
            );
        }

        //look through forms to find what the hell is he asking for
        Operation.id        thisOp = null;
        boolean             found  = false;
        Form                thisForm = null;
        for(final Form f : ia.getForms()) {
            final String thisPath = f.getHref().toString();
            String thisMethod = f.getHTTPMethodName();
            if (path.equals(thisPath)) {
                final List<Operation.id> oplist = f.getOperationList();
                thisOp = oplist != null && oplist.size() > 0 ? oplist.get(0) : null;
                if (thisOp != null) {
                    if (thisMethod == null || thisMethod.length() == 0 )    {
                        switch(thisOp) {
                            case readproperty:
                                thisMethod = "GET";
                                break;
                            case writeproperty:
                                thisMethod = "PUT";
                                break;
                        }
                        
                        if (methodName.equals(thisMethod)) {
                            found = true;
                            thisForm = f;
                            break;
                        }
                    }
                }
            }
            
        }
        
        if (found == false) {
            return NanoHTTPD.newFixedLengthResponse(
                NanoHTTPD.Response.Status.BAD_REQUEST,
                null,
                null
            );
        }
        
        try {
            if (SecurityHandler.checkAccess(owner.getData(), thisForm, session) == false )  {
                return NanoHTTPD.newFixedLengthResponse(
                    NanoHTTPD.Response.Status.UNAUTHORIZED,
                    null,
                    null
                );

            }
        } catch(RequireAuthenticationException re) {
            final NanoHTTPD.Response resp = NanoHTTPD.newFixedLengthResponse(
                NanoHTTPD.Response.Status.UNAUTHORIZED,
                null,
                null
            );
            
            resp.addHeader("WWW-Authenticate", re.getHeaderContent());
            return resp;
            
        } catch(WoTException we ) {
            return NanoHTTPD.newFixedLengthResponse(
                NanoHTTPD.Response.Status.INTERNAL_ERROR,
                null,
                null
            );
            
        }

        try {
            JSONObject obj = null;
            switch(thisOp) {
                case readproperty: {
                    obj = readProperty(uriResource, urlParams, session);
                    break;
                    
                }
                case writeproperty: {
                    obj = new JSONObject();
                    writeProperty(uriResource, urlParams, session, obj);
                    break;
                }

            }
            
            return corsResponse(
                    NanoHTTPD.newFixedLengthResponse(
                        NanoHTTPD.Response.Status.OK,
                        "application/json",
                        obj.toString()
                    )
            );
        } catch(JSONException e ) {
            return corsResponse(
                NanoHTTPD.newFixedLengthResponse(
                    NanoHTTPD.Response.Status.INTERNAL_ERROR,
                    null,
                    null
                )
            );
            
        } catch(BadRequest e) {
            return corsResponse(
                NanoHTTPD.newFixedLengthResponse(
                    NanoHTTPD.Response.Status.BAD_REQUEST,
                    null,
                    null
                )
            );
            
        } catch(BadAccess  e) {
            return corsResponse(
                NanoHTTPD.newFixedLengthResponse(
                    NanoHTTPD.Response.Status.BAD_REQUEST,
                    null,
                    null
                )
            );
            
        }
    }
    
    
    
    private JSONObject  readProperty(
        RouterNanoHTTPD.UriResource uriResource,
        Map<String, String> urlParams,
        NanoHTTPD.IHTTPSession session
    )throws JSONException,BadRequest,BadAccess{
        final ManagedThingsCollection mti = ManagedThingsCollection.getInstance();
        final String                path = "/" + uriResource.getUri();
        final InteractionAffordance ia = mti.getInteraction(path);
        final String                iName = mti.getInteractionName(path);
        final ThingObject           owner = mti.getInteractionOwner(path);        
        
        JSONObject obj = new JSONObject();
        
        final Property   p = owner.getProperty(iName);
        checkRead(p);

        Object value = (p != null ? p.getValue() : null);
        if (value == null) {
            obj.put(iName, JSONObject.NULL);
        } else {
            obj.putOpt(iName, value);
        }
        
        return obj;
    }         
    private void checkRead(Property p ) throws BadAccess {
        if (p.getData().getDataSchema() != null) {
            if (p.getData().getDataSchema().getWriteOnly() == true) {
                //canmot read a write only property
                throw new BadAccess();
            }
        }
        
    }
    
    private void checkWrite(Property p ) throws BadAccess {
        if (p.getData().getDataSchema() != null) {
            if (p.getData().getDataSchema().getReadOnly() == true) {
                //cannot write a read only property
                throw new BadAccess();
            }
        }
        
    }
    private void writeProperty(
        RouterNanoHTTPD.UriResource uriResource,
        Map<String, String>         urlParams,
        NanoHTTPD.IHTTPSession      session,
        JSONObject                  jresp
            
    ) throws JSONException,BadRequest,BadAccess {
        final ManagedThingsCollection mti = ManagedThingsCollection.getInstance();
        final String                path = "/" + uriResource.getUri();
        final InteractionAffordance ia = mti.getInteraction(path);
        final String                iName = mti.getInteractionName(path);
        final ThingObject           owner = mti.getInteractionOwner(path);
        
        JSONObject json = this.parseBody(session);
        if (json == null) {
            throw new BadRequest();
        }
        

        if (!json.has(iName)) {
            throw new BadRequest();
        }



        final Property p = owner.getProperty(iName);
        checkWrite(p);
        
        p.setValue(json.get(iName));


        
        jresp.putOpt(iName, p.getValue());
        
    }
}
