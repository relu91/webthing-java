package io.webthings.webthing.forms;

import io.webthings.webthing.JSONEntity;
import io.webthings.webthing.common.JSONEntityHelpers;
import io.webthings.webthing.exceptions.WoTException;

import org.json.JSONObject;

/**
 * @author Lorenzo
 */
public class ExpectedResponse extends JSONEntity {
    private String contentType;

    public ExpectedResponse() {

    }

    public ExpectedResponse(String c) {
        contentType = c;
    }

    public void setContentType(String s) {
        contentType = s;
    }

    public String getContentType() {
        return contentType;
    }

    @Override
    public JSONObject asJSON() {
        JSONObject ret = null;

        if (contentType != null && contentType.length() > 0) {
            ret = new JSONObject();
            ret.put("contentType", contentType);
        }
        return ret;
    }

    @Override
    public JSONEntity fromJSON(JSONObject o) throws WoTException {
        contentType =
                JSONEntityHelpers.readObject(o, "contentType", String.class);
        return this;
    }
}
