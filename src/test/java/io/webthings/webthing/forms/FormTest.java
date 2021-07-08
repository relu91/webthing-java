/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.webthings.webthing.forms;

import io.webthings.webthings.Common;
import static io.webthings.webthings.Common.checkGetter;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Lorenzo
 */
public class FormTest {
    
    public FormTest() {
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
     * Test of setHref method, of class Form.
     */
    @Test
    public void testSetHref() {
        System.out.println("setHref");
        Common.checkSetter(Form.class, "Href", "__href", "http://1.2.3.4");
        
    }

    /**
     * Test of getHref method, of class Form.
     */
    @Test
    public void testGetHref() {
        System.out.println("getHref");
        checkGetter(Form.class, "Href","__href","http://3.4.5.6");
    }

    /**
     * Test of setContentType method, of class Form.
     */
    @Test
    public void testSetContentType() {
        System.out.println("setContentType");
        Common.checkSetter(Form.class, "ContentType", "__contentType", "application/json");
    }

    /**
     * Test of getContentType method, of class Form.
     */
    @Test
    public void testGetContentType() {
        System.out.println("getContentType");
        checkGetter(Form.class, "ContentType", "__contentType", "application/json");
    }

    /**
     * Test of setContentCoding method, of class Form.
     */
    @Test
    public void testSetContentCoding() {
        System.out.println("setContentCoding");
        Common.checkSetter(Form.class, "ContentCoding", "__contentCoding", "UTF-8");
    }

    /**
     * Test of getContentCoding method, of class Form.
     */
    @Test
    public void testGetContentCoding() {
        System.out.println("getContentCoding");
        checkGetter(Form.class, "ContentCoding", "__contentCoding", "UTF-8");
    }

    /**
     * Test of setSubprotocol method, of class Form.
     */
    @Test
    public void testSetSubprotocol() {
        System.out.println("setSubprotocol");
        Common.checkSetter(Form.class,"Subprotocol", "__subprotocol", "SUBPROTO");
    }

    /**
     * Test of addSecurity method, of class Form.
     */
    @Test
    public void testAddSecurity() {
        System.out.println("addSecurity");
        Common.checkAddToCollection(Form.class, "addSecurity", "__security", "BasicAuth");
    }

    /**
     * Test of removeSecurity method, of class Form.
     */
    @Test
    public void testRemoveSecurity() {
        System.out.println("removeSecurity");
        Common.checkRemoveFromCollection(Form.class, "removeSecurity", "__security", "BasicAuth");
    }

    /**
     * Test of getSecurity method, of class Form.
     */
    @Test
    public void testGetSecurity() {
        System.out.println("getSecurity");
        final Set<String> secs = new TreeSet<>();
        secs.add("Basic");
        secs.add("Extended");
        
        checkGetter(Form.class, "Security", "__security", secs);
    }

    /**
     * Test of addScope method, of class Form.
     */
    @Test
    public void testAddScope() {
        System.out.println("addScope");
        Common.checkAddToCollection(Form.class, "addScope", "__scopes", "A Scope");
    }

    /**
     * Test of removeScope method, of class Form.
     */
    @Test
    public void testRemoveScope() {
        System.out.println("removeScope");
        Common.checkRemoveFromCollection(Form.class, "removeScope", "__scopes", "A Scope");
    }

    /**
     * Test of getScope method, of class Form.
     */
    @Test
    public void testGetScope() {
        System.out.println("getScope");
        
        
        final Set<String> scopes = new TreeSet<>();
        scopes.add("A Scope");
        scopes.add("Another Scoped");
        
        checkGetter(Form.class, "Scope", "__scopes", scopes);
        
    }

    /**
     * Test of getExpectedResponse method, of class Form.
     */
    @Test
    public void testGetExpectedResponse() {
        System.out.println("getExpectedResponse");
        checkGetter(Form.class, "ExpectedResponse", "__response", new ExpectedResponse("Resp"));
    }

    /**
     * Test of setExpectedResponse method, of class Form.
     */
    @Test
    public void testSetExpectedResponse() {
        System.out.println("setExpectedResponse");
        Common.checkSetter(Form.class, "ExpectedResponse", "__response", new ExpectedResponse("Resp"));
    }

    /**
     * Test of asJSON method, of class Form.
     */
    @Test
    
    public void testAsJSON() throws Exception {
        System.out.println("AsJSON");
        
        final String href = "http;//4.5.6.7";
        final String contentCoding = "UTF-8";
        
        final Map<Common.TestFieldId,Object> expVals = new TreeMap<>();
        Common.addTestFieldVal("href", "__href", href, expVals);
        Common.addTestFieldVal("contentCoding", "__contentCoding", contentCoding, expVals);
        
        
        
        Common.checkToJSON(Form.class, expVals);
        
    }

    /**
     * Test of fromJSON method, of class Form.
     */
    @Test
    public void testFromJSON() throws Exception {
        System.out.println("fromJSON");
        
        final String href = "http;//4.5.6.7";
        final String contentCoding = "UTF-8";
        
        final Map<Common.TestFieldId,Object> expVals = new TreeMap<>();
        Common.addTestFieldVal("href", "__href", href, expVals);
        Common.addTestFieldVal("contentCoding", "__contentCoding", contentCoding, expVals);
        
        
        
        Common.checkFromJSON(Form.class, expVals);
        
    }
    
}
