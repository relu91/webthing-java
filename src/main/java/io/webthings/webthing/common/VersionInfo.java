package io.webthings.webthing.common;

import io.webthings.webthing.JSONEntity;
import io.webthings.webthing.exceptions.WoTException;

import org.json.JSONObject;

/**
 * @author Lorenzo
 */
public class VersionInfo extends JSONEntity {
    private String instance;

    public String getInstance() {
        return instance;
    }

    public void setInstance(String s) {
        instance = s;
    }

    @Override
    public JSONObject asJSON() throws WoTException {
        final JSONObject ret = new JSONObject();
        JSONEntityHelpers.addString("instance", instance, ret);
        return ret;
    }

    @Override
    public JSONEntity fromJSON(JSONObject o) throws WoTException {
        instance = JSONEntityHelpers.readObject(o, "instance", String.class);
        return this;
    }
}
