/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.webthings.webthing.server;

import fi.iki.elonen.NanoHTTPD;
import fi.iki.elonen.router.RouterNanoHTTPD;
import io.webthings.webthing.affordances.InteractionAffordance;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author Lorenzo
 */
public class PropertyHandler  extends BaseHandler{
    
    public PropertyHandler() {
        
    }
    @Override
    public NanoHTTPD.Response get(
        RouterNanoHTTPD.UriResource uriResource,
        Map<String, String> urlParams,
        NanoHTTPD.IHTTPSession session
    ) {
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


        JSONObject obj = new JSONObject();
        try {
            final Property   p = owner.getProperty(iName);
            
            
              Object value = (p != null ? p.getValue() : null);
            if (value == null) {
                obj.put(iName, JSONObject.NULL);
            } else {
                obj.putOpt(iName, value);
            }
            return corsResponse(NanoHTTPD.newFixedLengthResponse(NanoHTTPD.Response.Status.OK,
                                                                 "application/json",
                                                                 obj.toString()));
        } catch (JSONException e) {
            return corsResponse(NanoHTTPD.newFixedLengthResponse(NanoHTTPD.Response.Status.INTERNAL_ERROR,
                                                                 null,
                                                                 null));
        }
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
        if (!validateHost(uriResource, session)) {
            return NanoHTTPD.newFixedLengthResponse(
                NanoHTTPD.Response.Status.FORBIDDEN,
                null,
                null
            );
        }

        final ManagedThingsCollection mti = ManagedThingsCollection.getInstance();
        final String                path = "/" + uriResource.getUri();
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


        JSONObject json = this.parseBody(session);
        if (json == null) {
            return corsResponse(
                NanoHTTPD.newFixedLengthResponse(
                    NanoHTTPD.Response.Status.BAD_REQUEST,
                    null,
                    null
                )
            );
        }

        if (!json.has(iName)) {
            return corsResponse(
                NanoHTTPD.newFixedLengthResponse(
                    NanoHTTPD.Response.Status.BAD_REQUEST,
                    null,
                    null
                )
            );
        }

        try {
            
            final Property p = owner.getProperty(iName);
            p.setValue(json.get(iName));
            

            JSONObject obj = new JSONObject();
            obj.putOpt(iName, p.getValue());
            return corsResponse(
                NanoHTTPD.newFixedLengthResponse(
                    NanoHTTPD.Response.Status.OK,
                    "application/json",
                    obj.toString()
                )
            );
        } catch (JSONException e) {
            return corsResponse(
                NanoHTTPD.newFixedLengthResponse(
                    NanoHTTPD.Response.Status.INTERNAL_ERROR,
                    null,
                    null
                )
            );
        } 
    }
    
}
