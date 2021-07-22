/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.webthings.webthing.affordances;

import io.webthings.webthing.JSONEntity;
import io.webthings.webthing.common.DataSchema;
import io.webthings.webthing.forms.Form;
import io.webthings.webthings.Common;
import static jdk.javadoc.internal.tool.JavadocClassFinder.instance;
import org.json.JSONArray;
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
public class ActionAffordanceTest {
    
    public ActionAffordanceTest() {
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
     * Test of getInput method, of class ActionAffordance.
     */
    @Test
    public void testGetInput() {
        System.out.println("getInput");
        final DataSchema ds = DataSchema.newInstance(DataSchema.typeId.tiBoolean);
        Common.checkGetter(ActionAffordance.class, "Input", "__input", ds);
        
    }

    /**
     * Test of setInput method, of class ActionAffordance.
     */
    @Test
    public void testSetInput() {
        System.out.println("setInput");
        final DataSchema ds = DataSchema.newInstance(DataSchema.typeId.tiBoolean);
        Common.checkSetter(ActionAffordance.class, "Input", DataSchema.class, "__input", ds);
    }

    /**
     * Test of getOutput method, of class ActionAffordance.
     */
    @Test
    public void testGetOutput() {
        System.out.println("getOutput");
        final DataSchema ds = DataSchema.newInstance(DataSchema.typeId.tiBoolean);
        Common.checkGetter(ActionAffordance.class, "Output", "__output", ds);

    }

    /**
     * Test of setOutput method, of class ActionAffordance.
     */
    @Test
    public void testSetOutput() {
        System.out.println("setOutput");
        final DataSchema ds = DataSchema.newInstance(DataSchema.typeId.tiBoolean);
        Common.checkSetter(ActionAffordance.class, "Output", DataSchema.class,"__output", ds);
    }

    /**
     * Test of setSafe method, of class ActionAffordance.
     */
    @Test
    public void testSetSafe() {
        System.out.println("setSafe");
        Common.checkSetter(ActionAffordance.class, "Safe",boolean.class, "__safe", true);
        
    }

    /**
     * Test of setIdempotent method, of class ActionAffordance.
     */
    @Test
    public void testSetIdempotent() {
        System.out.println("setIdempotent");
        Common.checkSetter(ActionAffordance.class, "Idempotent", boolean.class,"__idempotent", true);

    }

    /**
     * Test of getSafe method, of class ActionAffordance.
     */
    @Test
    public void testGetSafe() {
        System.out.println("getSafe");
        Common.checkGetter(ActionAffordance.class, "Safe", "__safe", true);
    }

    /**
     * Test of getIdempotent method, of class ActionAffordance.
     */
    @Test
    public void testGetIdempotent() {
        System.out.println("getIdempotent");
        Common.checkGetter(ActionAffordance.class, "Idempotent", "__idempotent", true);
    }

    /**
     * Test of asJSON method, of class ActionAffordance.
     */
    @Test
    public void testAsJSON() {
        System.out.println("asJSON");
        final ActionAffordance aa  = new ActionAffordance();
        try {
            aa.addForm(new Form("http://1.2.3.4"));
            aa.setInput(DataSchema.newInstance(DataSchema.typeId.tiBoolean));
            aa.setOutput(DataSchema.newInstance(DataSchema.typeId.tiNull));
            aa.setDefaultDescription("desc");
            aa.setDefaultTitle("title");

            
            final JSONObject o = aa.asJSON();
            
            assertTrue("Action desc check 1 ",o.has("description"));
            assertTrue("Action desc check 1 ",o.getString("description").equals("desc"));
            assertTrue("Action title check 1 ",o.has("title"));
            assertTrue("Action title check 1 ",o.getString("title").equals("title"));
            assertTrue("Action forms 1", o.getJSONArray("forms").length() == 1);
            assertTrue("Action forms 2" , o.getJSONArray("forms").getJSONObject(0).get("href").equals("http://1.2.3.4"));
            assertTrue("Action Input 1", o.has("input"));
            assertTrue("Action Input 2", o.getJSONObject("input").getString("type").equals("boolean"));
            assertTrue("Action Output 1", o.has("output"));
            assertTrue("Action Output 2", o.getJSONObject("output").getString("type").equals("null"));
            
        } catch(Exception e ) {
            fail("Got exception : " + e.toString());
        }
    }

    /**
     * Test of fromJSON method, of class ActionAffordance.
     */
    @Test
    public void testFromJSON() throws Exception {
        System.out.println("fromJSON");
        final JSONObject o = new JSONObject();
        o.put("title","title");
        o.put("description","desc");
        final JSONArray ja = new JSONArray();
        final JSONObject ja1 = new JSONObject();
        ja1.put("href", "http://1.2.3.4");
        ja.put(ja1);
        o.put("forms", ja);
        final JSONObject input = new JSONObject();
        input.put("type", "boolean");
        o.put("input",input);
        
        final JSONObject output = new JSONObject();
        output.put("type", "null");
        o.put("output",output);
        
        try {
            final ActionAffordance aa = new ActionAffordance();
            aa.fromJSON(o);
            
            assertTrue("Description",aa.getDefaultDescription().equals("desc"));
            assertTrue("Title",aa.getDefaultTitle().equals("title"));
            assertTrue("Forms 1",aa.getForms().size() == 1);
            assertTrue("Forms 2",aa.getForms().get(0).getHref().toString().equals("http://1.2.3.4"));
            assertTrue("Input 1",aa.getInput().getJSONType() == DataSchema.typeId.tiBoolean);
            assertTrue("Output 1",aa.getOutput().getJSONType() == DataSchema.typeId.tiNull);
        } catch(Exception e ) {
            fail("Got exception : " + e.toString());
        }
        
    }
    
}
