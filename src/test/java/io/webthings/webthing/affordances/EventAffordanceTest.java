/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.webthings.webthing.affordances;

import io.webthings.webthing.common.DataSchema;
import io.webthings.webthing.common.dataSchemas.BooleanSchema;
import io.webthings.webthing.common.dataSchemas.IntegerSchema;
import io.webthings.webthing.common.dataSchemas.NullSchema;
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
public class EventAffordanceTest {
    
    public EventAffordanceTest() {
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
     * Test of setSubscription method, of class EventAffordance.
     */
    @Test
    public void testSetSubscription() {
        System.out.println("setSubscription");
        final DataSchema ds = DataSchema.newInstance(DataSchema.typeId.tiBoolean);
        Common.checkSetter(EventAffordance.class, "Subscription", DataSchema.class, "__subscription", ds);
    }

    /**
     * Test of getSubscription method, of class EventAffordance.
     */
    @Test
    public void testGetSubscription() {
        System.out.println("getSubscription");
        final DataSchema ds = DataSchema.newInstance(DataSchema.typeId.tiBoolean);
        Common.checkGetter(EventAffordance.class, "Subscription", "__subscription", ds);
    }

    /**
     * Test of setData method, of class EventAffordance.
     */
    @Test
    public void testSetData() {
        System.out.println("setData");
        final DataSchema ds = DataSchema.newInstance(DataSchema.typeId.tiBoolean);
        Common.checkSetter(EventAffordance.class, "Data", DataSchema.class, "__data", ds);
    }

    /**
     * Test of getData method, of class EventAffordance.
     */
    @Test
    public void testGetData() {
        System.out.println("getData");
        final DataSchema ds = DataSchema.newInstance(DataSchema.typeId.tiBoolean);
        Common.checkGetter(EventAffordance.class, "Data", "__data", ds);
    }

    /**
     * Test of setCancellation method, of class EventAffordance.
     */
    @Test
    public void testSetCancellation() {
        System.out.println("setCancellation");
        final DataSchema ds = DataSchema.newInstance(DataSchema.typeId.tiBoolean);
        Common.checkSetter(EventAffordance.class, "Cancellation", DataSchema.class, "__cancellation", ds);
    }

    /**
     * Test of getCancellation method, of class EventAffordance.
     */
    @Test
    public void testGetCancellation() {
        System.out.println("getCancellation");
        final DataSchema ds = DataSchema.newInstance(DataSchema.typeId.tiBoolean);
        Common.checkGetter(EventAffordance.class, "Cancellation", "__cancellation", ds);
    }

    /**
     * Test of asJSON method, of class EventAffordance.
     */
    @Test
    public void testAsJSON() {
        final EventAffordance ea = new EventAffordance();
        try {
            ea.setDefaultTitle("title");
            ea.setDefaultDescription("desc");
            ea.setCancellation(DataSchema.newInstance(DataSchema.typeId.tiBoolean));
            ea.setData(DataSchema.newInstance(DataSchema.typeId.tiInteger));
            ea.setSubscription(DataSchema.newInstance(DataSchema.typeId.tiNull));
            ea.addForm(new Form("http://1.2.3.4"));
            final JSONObject o = ea.asJSON();
            
            assertTrue("Title 1", o.has("title"));
            assertTrue("Title 2", o.getString("title").equals("title"));
            assertTrue("Description 1", o.has("description"));
            assertTrue("Description 2", o.getString("description").equals("desc"));

            assertTrue("Forms 1", o.has("forms"));
            final JSONArray forms = o.getJSONArray("forms");
            assertTrue("Forms 2", forms.length() == 1);
            assertTrue("Forms 3", forms.getJSONObject(0).has("href"));
            assertTrue("Forms 3", forms.getJSONObject(0).get("href").toString().equals("http://1.2.3.4"));
            
            
            final JSONObject canc = o.getJSONObject("cancellation");
            assertTrue("Cancellation 1", canc != null);
            assertTrue("Cancellation 2", canc.has("type"));
            assertTrue("Cancellation 2", canc.getString("type").equals("boolean"));
            
            final JSONObject sub = o.getJSONObject("subscription");
            assertTrue("Subscription 1", sub != null);
            assertTrue("Subscription 2", sub.has("type"));
            assertTrue("Subscription 2", sub.getString("type").equals("null"));
            
            final JSONObject data = o.getJSONObject("data");
            assertTrue("Data 1", data != null);
            assertTrue("Data 2", data.has("type"));
            assertTrue("Data 2", data.getString("type").equals("integer"));
            
            

        } catch(Exception e ) {
            fail("Got Exception : " + e.toString());
        }    
        
    }
    
    /**
     * Test of fromJSON method, of class EventAffordance.
     */
    @Test
    public void testFromJSON() throws Exception {
        final JSONObject o = new JSONObject();
        o.put("title", "title");
        o.put("description", "desc");
        final JSONObject form = new JSONObject();
        form.put("href","http://1.2.3.4");
        final JSONArray forms = new JSONArray();
        forms.put(form);
        o.put("forms",forms);
        final JSONObject    canc = new JSONObject();
        canc.put("type", "boolean");
        o.put("cancellation",canc);
        
        final JSONObject    sub = new JSONObject();
        sub.put("type", "null");
        o.put("subscription",sub);
        
        
        final JSONObject    data = new JSONObject();
        data.put("type", "integer");
        o.put("data",data);
        
        
        final EventAffordance pa = new EventAffordance();
        pa.fromJSON(o);
        
        assertTrue("Title", pa.getDefaultTitle().equals("title"));
        assertTrue("Description", pa.getDefaultDescription().equals("desc"));
        assertTrue("Forms 1", pa.getForms() != null);
        assertTrue("Forms 2", pa.getForms().size() == 1);     
        assertTrue("Forms 3", pa.getForms().get(0).getHref().toString().equals("http://1.2.3.4"));      
        
        DataSchema ds = pa.getCancellation();
        assertTrue("Cancellation 1 ", ds != null);
        assertTrue("Cancellation 2 ", ds instanceof BooleanSchema);
        assertTrue("Cancellation 3 ", ds.getJSONType() == DataSchema.typeId.tiBoolean);
        
        ds = pa.getSubscription();
        assertTrue("Subscription 1 ", ds != null);
        assertTrue("Subscription 2 ", ds instanceof NullSchema);
        assertTrue("Subscription 3 ", ds.getJSONType() == DataSchema.typeId.tiNull);        
        
        ds = pa.getData();
        assertTrue("Data 1 ", ds != null);
        assertTrue("Data 2 ", ds instanceof IntegerSchema);
        assertTrue("Data 3 ", ds.getJSONType() == DataSchema.typeId.tiInteger);        
        
        
    }
    
}
