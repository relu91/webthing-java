/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.webthings.webthing.server;

import io.webthings.webthing.affordances.EventAffordance;
import org.json.JSONObject;

/**
 *
 * @author Lorenzo
 */
public class ToggleEvent extends Event {
    public ToggleEvent(String name, EventAffordance p) {
        super(name, p);
    }
    public ToggleEvent(String name, EventAffordance p,Class h) {
        super(name, p, h);

    }

    @Override
    protected JSONObject makeEventData() {
        final JSONObject ret = new JSONObject();
        ret.put("toggled", "Changed state !!");

        return ret;
    }

}
