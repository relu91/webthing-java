package io.webthings.webthing.common.dataSchemas;

import io.webthings.webthing.common.DataSchema;

import org.json.JSONObject;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

import io.webthings.Common;

/**
 * @author Lorenzo
 */
public class IntegerSchemaTest {
    /**
     * Test of getJSONType method, of class IntegerSchema.
     */
    @Test
    public void testGetJSONType() {
        System.out.println("getJSONType");
        final IntegerSchema ds = new IntegerSchema();
        assertTrue("Type", ds.getJSONType() == DataSchema.typeId.tiInteger);
    }

    /**
     * Test of fromJSON method, of class IntegerSchema.
     */
    @Test
    public void testFromJSON() throws Exception {
        System.out.println("fromJSON");
        final JSONObject o = new JSONObject();
        o.put("type", "integer");
        o.put("minimum", 16);
        o.put("maximum", 36);


        IntegerSchema ds = new IntegerSchema();
        ds.fromJSON(o);

        assertTrue("1. Type", ds.getJSONType() == DataSchema.typeId.tiInteger);
        assertTrue("1. Min 1", ds.getMinimum() != null);

        assertTrue("1. Min 2", ds.getMinimum() == 16);

        assertTrue("1. Max 1", ds.getMaximum() != null);

        assertTrue("1. Max 2", ds.getMaximum() == 36);


        ds = (IntegerSchema)DataSchema.newInstance(o);

        assertTrue("2. Type", ds.getJSONType() == DataSchema.typeId.tiInteger);
        assertTrue("2. Min 1", ds.getMinimum() != null);

        assertTrue("2. Min 2", ds.getMinimum() == 16);

        assertTrue("2. Max 1", ds.getMaximum() != null);

        assertTrue("2. Max 2", ds.getMaximum() == 36);

    }

    /**
     * Test of asJSON method, of class IntegerSchema.
     */
    @Test
    public void testAsJSON() {
        System.out.println("asJSON");
        final IntegerSchema ds = new IntegerSchema();
        ds.setMaximum(36);
        ds.setMinimum(16);

        final JSONObject o = ds.asJSON();

        assertTrue("Type 1", o.has("type"));
        assertTrue("Type 2", o.getString("type").equals("integer"));

        assertTrue("Min 1", o.has("minimum"));
        assertTrue("Min 2", o.getInt("minimum") == 16);

        assertTrue("Max 1", o.has("maximum"));
        assertTrue("Max 2", o.getInt("maximum") == 36);
    }

    /**
     * Test of getMinimum method, of class IntegerSchema.
     */
    @Test
    public void testGetMinimum() {
        System.out.println("getMinimum");
        final Integer i = 12;
        Common.checkGetter(IntegerSchema.class, "Minimum", "minimum", i);
    }

    /**
     * Test of getMaximum method, of class IntegerSchema.
     */
    @Test
    public void testGetMaximum() {
        System.out.println("getMaximum");
        final Integer i = 12;
        Common.checkGetter(IntegerSchema.class, "Maximum", "maximum", i);
    }

    /**
     * Test of setMinimum method, of class IntegerSchema.
     */
    @Test
    public void testSetMinimum() {
        System.out.println("setMinimum");
        final Integer i = 12;
        Common.checkSetter(IntegerSchema.class, "Minimum", "minimum", i);
    }

    /**
     * Test of setMaximum method, of class IntegerSchema.
     */
    @Test
    public void testSetMaximum() {
        System.out.println("setMaximum");
        final Integer i = 12;
        Common.checkSetter(IntegerSchema.class, "Maximum", "maximum", i);
    }
}
