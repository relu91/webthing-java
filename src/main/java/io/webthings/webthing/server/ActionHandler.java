/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.webthings.webthing.server;

import fi.iki.elonen.NanoHTTPD;
import fi.iki.elonen.router.RouterNanoHTTPD;
import io.webthings.webthing.affordances.InteractionAffordance;
import io.webthings.webthing.forms.Form;
import java.util.Map;
import org.json.JSONObject;

/**
 *
 * @author Lorenzo
 */
public abstract class ActionHandler extends BaseHandler implements Runnable{
   protected    JSONObject  __request_body = null;
    @Override
   public NanoHTTPD.Response get(
        RouterNanoHTTPD.UriResource uriResource,
        Map<String, String> urlParams,
        NanoHTTPD.IHTTPSession session
    )    {
       return handleAction(uriResource, urlParams, session,"GET");
    }
   @Override
   public NanoHTTPD.Response put(
        RouterNanoHTTPD.UriResource uriResource,
        Map<String, String> urlParams,
        NanoHTTPD.IHTTPSession session
    )    {
        return handleAction(uriResource, urlParams, session,"PUT");       
    }

   @Override
   public NanoHTTPD.Response post(
        RouterNanoHTTPD.UriResource uriResource,
        Map<String, String> urlParams,
        NanoHTTPD.IHTTPSession session
    )    {
        return handleAction(uriResource, urlParams, session,"POST");          
    }
   
   private   NanoHTTPD.Response handleAction(
        RouterNanoHTTPD.UriResource     uriResource,
        Map<String, String>             urlParams,
        NanoHTTPD.IHTTPSession          session,
        String                          method
    )    {
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

        //check method
        for(final Form f : ia.getForms()) {
            //find which form was called
            final String fPath = f.getHref().toString();
            if (fPath.equals(path)) {
                if (f.getHTTPMethodName() != null) {
                    if (f.getHTTPMethodName().equals(method) == false ) {
                        return NanoHTTPD.newFixedLengthResponse(
                            NanoHTTPD.Response.Status.BAD_REQUEST,
                            null,
                            null
                        );
                        
                    }
                }
            }
        }
        //parse body (if any)
        try {
            __request_body = this.parseBody(session);
        } catch(Exception e ) {
            
        }
        
        //start worker thread
        final Thread t = new Thread(this);
        t.start();
        
        //return ok !
        return corsResponse(
            NanoHTTPD.newFixedLengthResponse(
                NanoHTTPD.Response.Status.OK,
                "application/json",
                null
            )
        );
        
    }

   
}
