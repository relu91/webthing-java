/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.webthings.webthing.common;

import io.webthings.webthing.JSONEntity;
import io.webthings.webthing.exceptions.MissingFieldException;
import io.webthings.webthing.exceptions.WoTException;
import org.json.JSONObject;

/**
 *
 * @author Lorenzo
 */
public class Link extends JSONEntity {
    private java.net.URI        __href;
    private String              __type;
    private String              __rel;
    private java.net.URI        __anchor;
    
    
    public java.net.URI getHref() {
        return __href;
    }
    
    public void setHRef(java.net.URI h) {
        __href = h;
    }
    
    public String getType() {
        return __type;
    }
    public void setType(String s ) {
        __type = s;
    }
    
    public String getRel() {
        return  __rel;
    }
    
    public void setRel(String s ) {
        __rel = s;
    }
    
    public java.net.URI getAnchor() {
        return __anchor;
    }
    
    public void setAnchor(java.net.URI a) {
        __anchor = a;
    }

    @Override
    public JSONObject asJSON() throws WoTException {
        final JSONObject ret = new JSONObject();
        JSONEntityHelpers.addURI("href", __href, ret);
        JSONEntityHelpers.addURI("anchor", __anchor, ret);
        JSONEntityHelpers.addString("rel", __rel, ret);
        JSONEntityHelpers.addString("type", __type, ret);
        
        
        return ret;
    }

    @Override
    public JSONEntity fromJSON(JSONObject o) throws WoTException {
        __rel = JSONEntityHelpers.readObject(o, "rel", String.class);
        __type = JSONEntityHelpers.readObject(o, "type", String.class);
        __href = JSONEntityHelpers.readURI(o, "href");
        __anchor = JSONEntityHelpers.readURI(o, "anchor");
        
        if (__href == null) {
            throw new MissingFieldException("href");
        }
        return this;
    }
}
