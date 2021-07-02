/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.webthings.webthing.example;

import io.webthings.webthing.affordances.ActionAffordance;
import io.webthings.webthing.common.DataSchema;
import org.json.JSONObject;

/**
 *
 * @author Lorenzo
 */
public class ActionTest {
    public static void main(String[] args) {
        
    }
    
    private static void testParse() {
        final JSONObject    root = new JSONObject();
        final JSONObject    action = new JSONObject();
        
        action.put("safe", true);
        action.put("input", new DataSchema("boolean", "awd", "a weird data"));
        root.put("anAction",action);
        
        try {
            final ActionAffordance  a = (ActionAffordance) new ActionAffordance().fromJSON(root);
        } catch(Exception e ) {
            System.err.println(e);
        }
        
        
    }
}
