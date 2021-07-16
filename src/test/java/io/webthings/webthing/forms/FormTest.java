/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.webthings.webthing.forms;

import io.webthings.webthings.Common;
import static io.webthings.webthings.Common.checkGetter;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
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
    public void testSetHref()throws Exception {
        System.out.println("setHref");
        Common.checkSetter(Form.class, "Href", "__href", new URI("http://1.2.3.4"));
        
    }

    /**
     * Test of getHref method, of class Form.
     */
    @Test
    public void testGetHref() throws Exception{
        System.out.println("getHref");
        checkGetter(Form.class, "Href","__href", new URI("http://3.4.5.6"));
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
        
        final URI href = new URI("http;//4.5.6.7");
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
        Common.addTestFieldVal("href", "__href", new URI( href), expVals);
        Common.addTestFieldVal("contentCoding", "__contentCoding", contentCoding, expVals);
        
        
        
        Common.checkFromJSON(Form.class, expVals);
        
    }

    /**
     * Test of getOperationList method, of class Form.
     */
    @Test
    public void testGetOperationList() {
        System.out.println("getOperationList");
        
        final List<Operation.id> ops = new ArrayList<>();
        ops.add(Operation.id.writeproperty);
        ops.add(Operation.id.readproperty);
        
        
        checkGetter(Form.class, "OperationList", "__ops", ops);
        
    }

    /**
     * Test of setOperationList method, of class Form.
     */
    @Test
    public void testSetOperationList() {
        System.out.println("setOperationList");
        List<Operation.id> s = new ArrayList<>();
        s.add(Operation.id.readproperty);
        s.add(Operation.id.readallproperties);
        
        Common.checkSetter(Form.class, "OperationList", List.class, "__ops", s);
    }

    /**
     * Test of setOperation method, of class Form.
     */
    @Test
    public void testSetOperation() {
        System.out.println("setOperation");
        Common.checkAddToCollection(Form.class, "setOperation", "__ops", Operation.id.readallproperties);

    }

    /**
     * Test of addOperation method, of class Form.
     */
    @Test
    public void testAddOperation() {
        System.out.println("addOperation");
        
        System.out.println("setOperation");
        Common.checkAddToCollection(Form.class, "addOperation", "__ops", Operation.id.readallproperties);
        
    }

    /**
     * Test of getHTTPMethodName method, of class Form.
     */
    @Test
    public void testGetHTTPMethodName() {
        System.out.println("getHTTPMethodName");
        // TODO review the generated test code and remove the default call to fail.
        Common.checkGetter(Form.class, "HTTPMethodName", "__method_name", "PUT");
        
    }

    /**
     * Test of setHTTPMethodName method, of class Form.
     */
    @Test
    public void testSetHTTPMethodName() throws Exception {
        System.out.println("setHTTPMethodName");
        Common.checkSetter(Form.class,"HTTPMethodName","__method_name","GET");
    
    }
    
}
