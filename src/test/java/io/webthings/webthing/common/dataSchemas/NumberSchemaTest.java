package io.webthings.webthing.common.dataSchemas;

import io.webthings.webthing.common.DataSchema;
import io.webthings.Common;

import org.json.JSONObject;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Lorenzo
 */
public class NumberSchemaTest {
    /**
     * Test of getJSONType method, of class NumberSchema.
     */
    @Test
    public void testGetJSONType() {
        System.out.println("getJSONType");
        final NumberSchema ds = new NumberSchema();
        assertTrue("Type", ds.getJSONType() == DataSchema.typeId.tiNumber);
    }

    /**
     * Test of fromJSON method, of class NumberSchema.
     */
    @Test
    public void testFromJSON() throws Exception {
        System.out.println("fromJSON");
        final JSONObject o = new JSONObject();
        o.put("type", "number");
        o.put("minimum", 16.0);
        o.put("maximum", 36.0);


        NumberSchema ds = new NumberSchema();
        ds.fromJSON(o);

        assertTrue("1. Type", ds.getJSONType() == DataSchema.typeId.tiNumber);
        assertTrue("1. Min 1", ds.getMinimum() != null);

        assertTrue("1. Min 2", ds.getMinimum() == 16.0);

        assertTrue("1. Max 1", ds.getMaximum() != null);

        assertTrue("1. Max 2", ds.getMaximum() == 36.0);


        ds = (NumberSchema)DataSchema.newInstance(o);

        assertTrue("2. Type", ds.getJSONType() == DataSchema.typeId.tiNumber);
        assertTrue("2. Min 1", ds.getMinimum() != null);

        assertTrue("2. Min 2", ds.getMinimum() == 16.0);

        assertTrue("2. Max 1", ds.getMaximum() != null);

        assertTrue("2. Max 2", ds.getMaximum() == 36.0);

    }

    /**
     * Test of asJSON method, of class NumberSchema.
     */
    @Test
    public void testAsJSON() {
        System.out.println("asJSON");
        final NumberSchema ds = new NumberSchema();
        ds.setMaximum(36.0);
        ds.setMinimum(16.0);

        final JSONObject o = ds.asJSON();

        assertTrue("Type 1", o.has("type"));
        assertTrue("Type 2", o.getString("type").equals("number"));

        assertTrue("Min 1", o.has("minimum"));
        assertTrue("Min 2", o.getInt("minimum") == 16.0);

        assertTrue("Max 1", o.has("maximum"));
        assertTrue("Max 2", o.getInt("maximum") == 36.0);
    }

    /**
     * Test of getMinimum method, of class NumberSchema.
     */
    @Test
    public void testGetMinimum() {
        System.out.println("getMinimum");
        final Double i = 12.0;
        Common.checkGetter(NumberSchema.class, "Minimum", "minimum", i);
    }

    /**
     * Test of getMaximum method, of class NumberSchema.
     */
    @Test
    public void testGetMaximum() {
        System.out.println("getMaximum");
        final Double i = 12.0;
        Common.checkGetter(NumberSchema.class, "Maximum", "maximum", i);
    }

    /**
     * Test of setMinimum method, of class NumberSchema.
     */
    @Test
    public void testSetMinimum() {
        System.out.println("setMinimum");
        final Double i = 12.0;
        Common.checkSetter(NumberSchema.class, "Minimum", "minimum", i);
    }

    /**
     * Test of setMaximum method, of class NumberSchema.
     */
    @Test
    public void testSetMaximum() {
        System.out.println("setMaximum");
        final Double i = 12.0;
        Common.checkSetter(NumberSchema.class, "Maximum", "maximum", i);
    }
}
