/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.webthings.webthing.example;

import io.webthings.webthing.common.DataSchema;
import org.json.JSONObject;

/**
 *
 * @author Lorenzo
 */
public class DataSchemaTest {
    public static void main(String[] args) {
        try {
            final DataSchema ds = new DataSchema("boolean","aData","a weird data schema");
            final JSONObject o = ds.asJSON();
            final String     s = o.toString();
            System.out.println(s);
        } catch(Exception e ) {
            System.err.println(e);
            e.printStackTrace();
        }
        
    }
}
