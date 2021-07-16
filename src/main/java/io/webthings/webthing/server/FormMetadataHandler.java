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
import io.webthings.webthing.forms.Operation;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author Lorenzo
 */
public class FormMetadataHandler  extends BaseHandler{
    private class BadRequest extends Exception {
        
    }
    private class BadAccess extends Exception {
        
    }
    
    public FormMetadataHandler() {
        
    }
    @Override
    public NanoHTTPD.Response get(
        RouterNanoHTTPD.UriResource uriResource,
        Map<String, String> urlParams,
        NanoHTTPD.IHTTPSession session
    ) {
        
        return handleRootForm(uriResource, urlParams, session, "GET");
    }

    @Override
    public NanoHTTPD.Response post(
        RouterNanoHTTPD.UriResource uriResource,
        Map<String, String> urlParams,
        NanoHTTPD.IHTTPSession session
    )    {
        return handleRootForm(uriResource, urlParams, session, "POST");
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
        return handleRootForm(uriResource, urlParams, session, "PUT");
    }
    
    //inner generic handle
    
    private NanoHTTPD.Response handleRootForm(
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
        final Form                  fd = mti.getRootForm(path);
        final ThingObject           owner = mti.getRootFormOwner(path);
        
        
        if (fd == null || owner == null) {
            return corsResponse(NanoHTTPD.newFixedLengthResponse(
                    NanoHTTPD.Response.Status.NOT_FOUND,
                    null,
                    null)
            );
        }

        //look through forms to find what the hell is he asking for
        Operation.id        thisOp = null;
        boolean             found  = false;
        final String thisPath = fd.getHref().toString();
        String thisMethod = fd.getHTTPMethodName();
        if (path.equals(thisPath)) {
            final List<Operation.id> oplist = fd.getOperationList();
            thisOp = oplist != null && oplist.size() > 0 ? oplist.get(0) : null;
            if (thisOp != null) {
                if (thisMethod == null || thisMethod.length() == 0 )    {
                    switch(thisOp) {
                        case readallproperties:
                        case readmultipleproperties:
                            thisMethod = "GET";
                            break;
                        case writeallproperties:
                        case writemultipleproperties:
                            thisMethod = "PUT";
                            break;
                    }

                    if (methodName.equals(thisMethod)) {
                        found = true;
                        
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
            JSONObject obj = null;
            switch(thisOp) {
                case readmultipleproperties: {
                    break;
                }
                case readallproperties: {
                    obj = new JSONObject();
                    for(final Map.Entry<String,Property> e : owner.getProperties().entrySet()) {
                        try {
                            final String name   = e.getKey();
                            final Property   p  = e.getValue();
                            checkRead(p);
                        
                            Object value = (p != null ? p.getValue() : null);
                            if (value == null) {
                                obj.put(name, JSONObject.NULL);
                            } else {
                                obj.putOpt(name, value);
                            }
                        } catch(BadAccess ee ) {
                            continue;
                        }
                        
                    }
                    break;
                }
                case writemultipleproperties: {
                    break;
                }
                
                case writeallproperties: {
                    final JSONObject json = this.parseBody(session);
                    if (json == null)
                        throw new BadRequest();
                    //first pass, check that all properties are specified 
                    final Set<String> jsonKeys = new TreeSet<String>(json.keySet());
                    final Set<String> propertyKeys = new TreeSet<String>(owner.getProperties().keySet());
                    
                    for(final String k : json.keySet()) {
                        if (propertyKeys.contains(k)) {
                            propertyKeys.remove(k);
                            jsonKeys.remove(k);
                        }
                    }
                    
                    //now.remove all properties that cannot be written
                    for(final String k : owner.getProperties().keySet()) {
                        try {
                            final Property p = owner.getProperty(k);
                            checkWrite(p);
                        } catch(BadAccess ee ) {
                            //read-only, removes
                            propertyKeys.remove(k);
                        }
                    }
                    
                    if (jsonKeys.size() > 0 || propertyKeys.size() > 0 ) {
                        throw new BadRequest();
                    }
                    
                    
                    //second pass, do a write
                    obj = new JSONObject();
                    
                    for(final String k : json.keySet()) {
                        final Object o = json.get(k);
                        final Property p = owner.getProperty(k);
                        p.setValue(o);
                        
                        obj.put(k, p.getValue());
                        
                    }
                    
                    
                    
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
            
        } 
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
}
