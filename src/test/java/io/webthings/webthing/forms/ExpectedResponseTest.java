/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.webthings.webthing.forms;

import io.webthings.webthing.JSONEntity;
import io.webthings.webthings.Common;
import java.util.Map;
import java.util.TreeMap;
import org.json.JSONObject;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Lorenzo
 */
public class ExpectedResponseTest {
    
    public ExpectedResponseTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of setContentType method, of class ExpectedResponse.
     */
    @Test
    public void testSetContentType() {
        System.out.println("setContentType");
        Common.checkSetter(ExpectedResponse.class, "ContentType",  "__contentType", "application/json");
    }

    /**
     * Test of getContentType method, of class ExpectedResponse.
     */
    @Test
    public void testGetContentType() {
        System.out.println("getContentType");
        Common.checkGetter(ExpectedResponse.class, "ContentType",  "__contentType", "application/json");
    }

    /**
     * Test of asJSON method, of class ExpectedResponse.
     */
    @Test
    public void testAsJSON() {
        System.out.println("asJSON");
        
        final String contentType = "application/json";
        
        final Map<Common.TestFieldId,Object> expVals = new TreeMap<>();
        Common.addTestFieldVal("contentType", "__contentType", contentType, expVals);
        
        
        
        Common.checkToJSON(ExpectedResponse.class, expVals);
    }

    /**
     * Test of fromJSON method, of class ExpectedResponse.
     */
    @Test
    public void testFromJSON() throws Exception {
        System.out.println("fromJSON");
        
        
        final String contentType = "application/json";
        
        final Map<Common.TestFieldId,Object> expVals = new TreeMap<>();
        Common.addTestFieldVal("contentType", "__contentType", contentType, expVals);
        
        
        
        Common.checkFromJSON(ExpectedResponse.class, expVals);
    }
    
}
