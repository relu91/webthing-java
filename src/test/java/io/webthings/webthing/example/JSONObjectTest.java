/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.webthings.webthing.example;

import java.util.Map;
import org.json.JSONObject;

/**
 *
 * @author Lorenzo
 */
public class JSONObjectTest {
    public static void main(String [] args) {
        testSingle();
        testDouble();
        testComposite();
    }
    
    private static void testSingle() {
        JSONObject o = new JSONObject();
        
        o.put("key", "value");
        
        System.out.println(o.toString());
        
    }
    private static void testDouble() {
        JSONObject o = new JSONObject();
        
        o.put("key1", "value1");
        o.put("key2", "value2");
        
        System.out.println(o.toString());
        
    }
    
    private static void testComposite() {
        JSONObject o = new JSONObject();
        o.put("key", "value");
        
        JSONObject o3 = new JSONObject();
        o3.put("key3", "value3");
        
        JSONObject o2 = new JSONObject();
        o2.append("root", o);
        o2.append("root", o3);
        
        
        System.out.println(o2.toString());
        
        Map m2 = o2.toMap();
        Map m1 = o.toMap();
        Map m3 = o3.toMap();
        
        
    }
    
}
