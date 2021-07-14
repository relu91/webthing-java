/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.webthings.webthing.common;

import io.webthings.webthing.JSONEntity;
import io.webthings.webthing.exceptions.WoTException;
import org.json.JSONObject;

/**
 *
 * @author Lorenzo
 */
public class VersionInfo extends JSONEntity {
    private String      __instance;
    public String getInstance() {
        return __instance;
    }
    
    public void setInstance(String s ) {
        __instance = s;
    }

    @Override
    public JSONObject asJSON() throws WoTException {
        final JSONObject ret  = new JSONObject();
        JSONEntityHelpers.addString("instance", __instance, ret);
        return ret;
    }

    @Override
    public JSONEntity fromJSON(JSONObject o) throws WoTException {
        __instance = JSONEntityHelpers.readObject(o, "instance", String.class);
        return this;
    }
    
}
