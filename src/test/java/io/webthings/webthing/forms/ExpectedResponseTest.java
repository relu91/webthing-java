package io.webthings.webthing.forms;

import io.webthings.Common;

import java.util.Map;
import java.util.TreeMap;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author Lorenzo
 */
public class ExpectedResponseTest {
    /**
     * Test of setContentType method, of class ExpectedResponse.
     */
    @Test
    public void testSetContentType() {
        System.out.println("setContentType");
        Common.checkSetter(ExpectedResponse.class,
                           "ContentType",
                           "contentType",
                           "application/json");
    }

    /**
     * Test of getContentType method, of class ExpectedResponse.
     */
    @Test
    public void testGetContentType() {
        System.out.println("getContentType");
        Common.checkGetter(ExpectedResponse.class,
                           "ContentType",
                           "contentType",
                           "application/json");
    }

    /**
     * Test of asJSON method, of class ExpectedResponse.
     */
    @Test
    public void testAsJSON() {
        System.out.println("asJSON");

        final String contentType = "application/json";

        final Map<Common.TestFieldId, Object> expVals = new TreeMap<>();
        Common.addTestFieldVal("contentType",
                               "contentType",
                               contentType,
                               expVals);


        Common.checkToJSON(ExpectedResponse.class, expVals);
    }

    /**
     * Test of fromJSON method, of class ExpectedResponse.
     */
    @Test
    public void testFromJSON() throws Exception {
        System.out.println("fromJSON");


        final String contentType = "application/json";

        final Map<Common.TestFieldId, Object> expVals = new TreeMap<>();
        Common.addTestFieldVal("contentType",
                               "contentType",
                               contentType,
                               expVals);


        Common.checkFromJSON(ExpectedResponse.class, expVals);
    }
}
