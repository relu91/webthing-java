/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.webthings.webthing.affordances;

import io.webthings.webthing.common.DataSchema;
import io.webthings.webthing.common.dataSchemas.BooleanSchema;
import io.webthings.webthing.forms.Form;
import io.webthings.webthings.Common;
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
public class PropertyAffordanceTest {

    public PropertyAffordanceTest() {
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
     * Test of getObservable method, of class PropertyAffordance.
     */
    @Test
    public void testGetObservable() {
        System.out.println("getObservable");
        final Boolean b = true;
        Common.checkGetter(PropertyAffordance.class, "Observable", "__observable", b);
    }

    /**
     * Test of setObservable method, of class PropertyAffordance.
     */
    @Test
    public void testSetObservable() {
        System.out.println("setObservable");
        Common.checkSetter(PropertyAffordance.class, "Observable", boolean.class, "__observable", true);
    }

    /**
     * Test of setDataSchema method, of class PropertyAffordance.
     */
    @Test
    public void testSetDataSchema() {
        System.out.println("setDataSchema");
        final DataSchema ds = DataSchema.newInstance(DataSchema.typeId.tiBoolean);
        Common.checkSetter(PropertyAffordance.class, "DataSchema", DataSchema.class,"__ds", ds);
    }

    /**
     * Test of getDataSchema method, of class PropertyAffordance.
     */
    @Test
    public void testGetDataSchema() {
        System.out.println("getDataSchema");
        final DataSchema ds = DataSchema.newInstance(DataSchema.typeId.tiBoolean);
        Common.checkGetter(PropertyAffordance.class, "DataSchema", "__ds", ds);
    }

    /**
     * Test of asJSON method, of class PropertyAffordance.
     */
    @Test
    public void testAsJSON() {
        try {
            final PropertyAffordance pa = new PropertyAffordance();
            pa.setDefaultTitle("title");
            pa.setDefaultDescription("desc");
            pa.setObservable(true);
            final Form f = new Form("http://1.2.3.4");
            pa.addForm(f);
            final DataSchema ds = DataSchema.newInstance(DataSchema.typeId.tiBoolean);
            pa.setDataSchema(ds);
            
            
            final JSONObject o = pa.asJSON();
            
            assertTrue("Title 1", o.has("title"));
            assertTrue("Title 2", o.getString("title").equals("title"));
            assertTrue("Description 1", o.has("description"));
            assertTrue("Description 2", o.getString("description").equals("desc"));
            assertTrue("Observable 1 ", o.has("observable"));
            assertTrue("Observable 2 ", o.getBoolean("observable"));
            assertTrue("Forms 1", o.has("forms"));
            final JSONArray forms = o.getJSONArray("forms");
            assertTrue("Forms 2", forms.length() == 1);
            assertTrue("Forms 3", forms.getJSONObject(0).has("href"));
            assertTrue("Forms 3", forms.getJSONObject(0).get("href").toString().equals("http://1.2.3.4"));
            
            assertTrue("DS 1",o.has("type"));
            assertTrue("DS 2", o.getString("type").equals("boolean"));
            
            
            
        } catch(Exception e ) {
            fail("Got exception : " + e.toString());
        }
    }

    /**
     * Test of fromJSON method, of class PropertyAffordance.
     */
    @Test
    public void testFromJSON() throws Exception {
        System.out.println("fromJSON");
        JSONObject o = new JSONObject();
        o.put("title", "title");
        o.put("description", "desc");
        o.put("observable",true);
        o.put("type", "boolean");
        JSONObject form = new JSONObject();
        form.put("href","http://1.2.3.4");
        JSONArray forms = new JSONArray();
        forms.put(form);
        o.put("forms",forms);
        
        final PropertyAffordance pa = new PropertyAffordance();
        pa.fromJSON(o);
        
        assertTrue("Title", pa.getDefaultTitle().equals("title"));
        assertTrue("Description", pa.getDefaultDescription().equals("desc"));
        assertTrue("Observable", pa.getObservable());
        assertTrue("Forms 1", pa.getForms() != null);
        assertTrue("Forms 2", pa.getForms().size() == 1);     
        assertTrue("Forms 3", pa.getForms().get(0).getHref().toString().equals("http://1.2.3.4"));        
        assertTrue("DS 1 ", pa.getDataSchema() != null);
        assertTrue("DS 2 ", pa.getDataSchema() instanceof BooleanSchema);
        assertTrue("DS 3 ", pa.getDataSchema().getJSONType() == DataSchema.typeId.tiBoolean);
        
    }

}
