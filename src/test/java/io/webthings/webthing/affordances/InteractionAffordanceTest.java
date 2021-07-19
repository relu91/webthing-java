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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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
public class InteractionAffordanceTest {
    
    public InteractionAffordanceTest() {
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
     * Test of setType method, of class InteractionAffordance.
     */
    @Test
    public void testSetType() {
        System.out.println("setType");
        Common.checkSetterOnCollection(InteractionAffordance.class, "Type", "__types", "a type", List.class);
    }

    /**
     * Test of getType method, of class InteractionAffordance.
     */
    @Test
    public void testGetType() {
        System.out.println("getType");
        Common.checkGetterOnCollection(InteractionAffordance.class, "Type", "__types", "a type",ArrayList.class);
    }

    /**
     * Test of getTypes method, of class InteractionAffordance.
     */
    @Test
    public void testGetTypes() {
        System.out.println("getTypes");
        final List<String> result = new ArrayList<>();
        result.add("a type");
        result.add("b type");
  
        Common.checkGetter(InteractionAffordance.class, "Types", "__types", result);
        
    
    }

    /**
     * Test of addType method, of class InteractionAffordance.
     */
    @Test
    public void testAddType() {
        System.out.println("addType");
        Common.checkAddToCollection(InteractionAffordance.class, "addType", "__types", "a type");
    }

    /**
     * Test of setDefaultTitle method, of class InteractionAffordance.
     */
    @Test
    public void testSetDefaultTitle() {
        
        System.out.println("setDefaultTitle");
        Common.checkSetter(InteractionAffordance.class, "DefaultTitle", "__title", "A title");
    }

    /**
     * Test of getDefaultTitle method, of class InteractionAffordance.
     */
    @Test
    public void testGetDefaultTitle() {
        System.out.println("getDefaultTitle");
        Common.checkGetter(InteractionAffordance.class, "DefaultTitle", "__title", "A title");
    }

    /**
     * Test of setI18NTitle method, of class InteractionAffordance.
     */
    @Test
    public void testSetI18NTitle() {
        System.out.println("setI18NTitle");
        Common.checkAddToCollection(InteractionAffordance.class, "setI18NTitle", "__titles", "EN", "A title");
        
    }

    /**
     * Test of getI18NTitle method, of class InteractionAffordance.
     */
    @Test
    public void testGetI18NTitle() {
        System.out.println("getI18NTitle");
        Common.checkGetterOnMap(InteractionAffordance.class, "I18NTitle", "__titles", "IT", "A title");
    }

    /**
     * Test of removeI18NTitle method, of class InteractionAffordance.
     */
    @Test
    public void testRemoveI18NTitle() {
        System.out.println("removeI18NTitle");
        Common.checkRemoveFromCollection(InteractionAffordance.class, "removeI18NTitle", "__titles", "IT", "Un titolo");
    }

    /**
     * Test of setDefaultDescription method, of class InteractionAffordance.
     */
    @Test
    public void testSetDefaultDescription() {
        System.out.println("setDefaultDescription");
        Common.checkSetter(InteractionAffordance.class, "DefaultDescription", "__description", "A description");
    }

    /**
     * Test of getDefaultDescription method, of class InteractionAffordance.
     */
    @Test
    public void testGetDefaultDescription() {
        System.out.println("getDefaultDescription");
        Common.checkGetter(InteractionAffordance.class, "DefaultDescription", "__description", "A description");    
    }

    /**
     * Test of setI18NDescription method, of class InteractionAffordance.
     */
    @Test
    public void testSetI18NDescription() {
        System.out.println("setI18NDescription");
        Common.checkAddToCollection(InteractionAffordance.class, "setI18NDescription", "__descriptions", "EN", "A description");
    }

    /**
     * Test of getI18NDescription method, of class InteractionAffordance.
     */
    @Test
    public void testGetI18NDescription() {
        System.out.println("getI18NDescription");
        Common.checkGetterOnMap(InteractionAffordance.class, "I18NDescription", "__descriptions", "IT", "A descriptions"); 
    }

    /**
     * Test of removeI18NDescription method, of class InteractionAffordance.
     */
    @Test
    public void testRemoveI18NDescription() {
        System.out.println("removeI18NDescription");
        Common.checkRemoveFromCollection(InteractionAffordance.class, "removeI18NDescription", "__descriptions", "IT", "Una descrizione");
    }

    /**
     * Test of addForm method, of class InteractionAffordance.
     */
    @Test
    public void testAddForm() throws Exception {
        System.out.println("addForm");
        Form f = null;
        InteractionAffordance instance = new InteractionAffordance();
        instance.addForm(f);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getForms method, of class InteractionAffordance.
     */
    @Test
    public void testGetForms() {
        System.out.println("getForms");
        InteractionAffordance instance = new InteractionAffordance();
        List<Form> expResult = null;
        List<Form> result = instance.getForms();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getUriVariables method, of class InteractionAffordance.
     */
    @Test
    public void testGetUriVariables() {
        System.out.println("getUriVariables");
        InteractionAffordance instance = new InteractionAffordance();
        Map<String, DataSchema> expResult = null;
        Map<String, DataSchema> result = instance.getUriVariables();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getUriVariable method, of class InteractionAffordance.
     */
    @Test
    public void testGetUriVariable() {
        System.out.println("getUriVariable");
        String s = "";
        InteractionAffordance instance = new InteractionAffordance();
        DataSchema expResult = null;
        DataSchema result = instance.getUriVariable(s);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of putUriVariable method, of class InteractionAffordance.
     */
    @Test
    public void testPutUriVariable() {
        System.out.println("putUriVariable");
        String s = "";
        DataSchema d = null;
        InteractionAffordance instance = new InteractionAffordance();
        instance.putUriVariable(s, d);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of asJSON method, of class InteractionAffordance.
     */
    @Test
    public void testAsJSON() {
        System.out.println("asJSON");
        InteractionAffordance instance = new InteractionAffordance();
        JSONObject expResult = null;
        JSONObject result = instance.asJSON();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of fromJSON method, of class InteractionAffordance.
     */
    @Test
    public void testFromJSON() throws Exception {
        System.out.println("fromJSON");
        JSONObject o = null;
        InteractionAffordance instance = new InteractionAffordance();
        JSONEntity expResult = null;
        JSONEntity result = instance.fromJSON(o);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
