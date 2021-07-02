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
            ds.setI18NTitle("a", "b");
            final JSONObject o = ds.asJSON();
            
            
            final String     s = o.toString();
            System.out.println(s);
            
            
            //append another type
            ds.addType("alfagam");
            ds.setI18NTitle("c", "d");
            JSONObject o2 = ds.asJSON();
            String s2 = o2.toString();
            System.out.println(s2);
            
            final DataSchema ds2 = (DataSchema) new DataSchema().fromJSON(o);
            final DataSchema ds3 = (DataSchema) new DataSchema().fromJSON(o2);
            
            ds.addOneOf(new DataSchema("a","b","c"));
            ds.addOneOf(new DataSchema("c","d","e"));
            
            JSONObject o3 = ds.asJSON();
            String s3 = o3.toString();
            System.out.println(s3);
            final DataSchema ds4 = (DataSchema) new DataSchema().fromJSON(o3);
            
            System.out.println("End");
            
            
        } catch(Exception e ) {
            System.err.println(e);
            e.printStackTrace();
        }
        
    }
}
