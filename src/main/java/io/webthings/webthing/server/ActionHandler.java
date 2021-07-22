/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.webthings.webthing.server;

import fi.iki.elonen.NanoHTTPD;
import fi.iki.elonen.router.RouterNanoHTTPD;
import io.webthings.webthing.affordances.InteractionAffordance;
import io.webthings.webthing.exceptions.WoTException;
import io.webthings.webthing.forms.Form;
import io.webthings.webthing.forms.Operation;
import io.webthings.webthing.server.securityHandlers.exceptions.RequireAuthenticationException;
import java.util.List;
import java.util.Map;
import org.json.JSONObject;

/**
 *
 * @author Lorenzo
 */
public abstract class ActionHandler extends BaseHandler {
   protected    JSONObject  __request_body = null;
   protected    ThingObject __owner;
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
        
        //check security
        
        final ManagedThingsCollection mti = ManagedThingsCollection.getInstance();
        final String                path = "/" +  uriResource.getUri();
        final InteractionAffordance ia = mti.getInteraction(path);
        final String                iName = mti.getInteractionName(path);
        final ThingObject           owner = mti.getInteractionOwner(path);
        __owner = owner;
        
        
            
        if (ia == null || iName == null || owner == null) {
            return corsResponse(NanoHTTPD.newFixedLengthResponse(
                    NanoHTTPD.Response.Status.NOT_FOUND,
                    null,
                    null)
            );
        }

        //check method
        boolean fCheck = false;
        Operation.id    opid = null;
        Form            thisForm = null;

        for(final Form f : ia.getForms()) {
            //find which form was called
            final String fPath = f.getHref().toString();
            if (fPath.equals(path)) {
                String formMethod = "POST";
                if (f.getHTTPMethodName() != null) {
                    formMethod = f.getHTTPMethodName();
                } 
                
                if (formMethod.equals(method) == true ) {
                    fCheck = true;
                    final List<Operation.id> oplist = f.getOperationList();
                    opid = oplist != null && oplist.size() > 0  ? f.getOperationList().get(0) : Operation.id.invokeaction;
                    thisForm = f;
                    break;
                }
                
            }
        }

        if (fCheck == false) {
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
        //parse body (if any)
        final Action act = __owner.getAction(iName);
        try {
            __request_body = this.parseBody(session);
        } catch(Exception e ) {
            
        }
        
        return executeAction(act);
    }

   abstract protected NanoHTTPD.Response executeAction(Action a );
}
