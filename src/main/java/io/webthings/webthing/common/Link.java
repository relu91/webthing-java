package io.webthings.webthing.common;

import io.webthings.webthing.JSONEntity;
import io.webthings.webthing.exceptions.MissingFieldException;
import io.webthings.webthing.exceptions.WoTException;

import org.json.JSONObject;

/**
 * @author Lorenzo
 */
public class Link extends JSONEntity {
    private java.net.URI href;
    private String type;
    private String rel;
    private java.net.URI anchor;

    public java.net.URI getHref() {
        return href;
    }

    public void setHRef(java.net.URI h) {
        href = h;
    }

    public String getType() {
        return type;
    }

    public void setType(String s) {
        type = s;
    }

    public String getRel() {
        return rel;
    }

    public void setRel(String s) {
        rel = s;
    }

    public java.net.URI getAnchor() {
        return anchor;
    }

    public void setAnchor(java.net.URI a) {
        anchor = a;
    }

    @Override
    public JSONObject asJSON() throws WoTException {
        final JSONObject ret = new JSONObject();
        JSONEntityHelpers.addURI("href", href, ret);
        JSONEntityHelpers.addURI("anchor", anchor, ret);
        JSONEntityHelpers.addString("rel", rel, ret);
        JSONEntityHelpers.addString("type", type, ret);


        return ret;
    }

    @Override
    public JSONEntity fromJSON(JSONObject o) throws WoTException {
        rel = JSONEntityHelpers.readObject(o, "rel", String.class);
        type = JSONEntityHelpers.readObject(o, "type", String.class);
        href = JSONEntityHelpers.readURI(o, "href");
        anchor = JSONEntityHelpers.readURI(o, "anchor");

        if (href == null) {
            throw new MissingFieldException("href");
        }
        return this;
    }
}
