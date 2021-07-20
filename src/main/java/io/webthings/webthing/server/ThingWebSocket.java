/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.webthings.webthing.server;

import fi.iki.elonen.NanoHTTPD.IHTTPSession;
import fi.iki.elonen.NanoWSD;
import java.io.IOException;
import org.json.JSONObject;

/**
 *
 * @author Lorenzo
 */
public class ThingWebSocket extends NanoWSD.WebSocket {
    private final   boolean __is_property;
    private final   String  __path;
    public ThingWebSocket(IHTTPSession handshakeRequest,boolean isProperty,String path) {
        super(handshakeRequest);
        __is_property = isProperty;
        __path = path;
    }
    
    private IObservable findObservable() {
        final ManagedThingsCollection mti = ManagedThingsCollection.getInstance();
        if (mti == null) 
            return null;
        final ThingObject               to = mti.getInteractionOwner(__path);        
        if (to == null)
            return null;
        
        final String                    iName = mti.getInteractionName(__path);        
        if (iName == null) 
            return null;
        
        IObservable ret = null;
        if (__is_property)  {
            final Property p = to.getProperty(iName);
            ret = p;
        } else {
            final Event e = to.getEvent(iName);
            ret = e;
            
        }
        
        return ret;
            
    }
    @Override
    protected void onOpen() {
        final IObservable tgt = findObservable();
        if (tgt != null) {
            tgt.addSuscriber(this);
        }
        
    }

    /**
     * Handle a close event on the socket.
     *
     * @param code              The close code
     * @param reason            The close reason
     * @param initiatedByRemote Whether or not the client closed the
     *                          socket
     */
    @Override
    protected void onClose(
        NanoWSD.WebSocketFrame.CloseCode    code,
        String                              reason,
        boolean                             initiatedByRemote
    ) {
        final IObservable tgt = findObservable();
        if (tgt != null) {
            tgt.removeSubscriber(this);
        }

    }

    /**
     * Handle an incoming message.
     *
     * @param message The message to handle
     */
    @Override
    protected void onMessage(NanoWSD.WebSocketFrame message) {
        message.setUnmasked();
        String data = message.getTextPayload();
        JSONObject json = new JSONObject(data);
        if (json != null && json.has("type")) {
            final String type = json.getString("type");
            if (type.equals("unobserveproperty") || type.equals("unsubscribeevent")) {
                final IObservable tgt = findObservable();
                if (tgt != null) {
                    tgt.removeSubscriber(this);
                }
                
            }
        }
        
    }

    @Override
    protected void onPong(NanoWSD.WebSocketFrame wsf) {
        return;
    }

    @Override
    protected void onException(IOException ioe) {
        return;
    }
}
