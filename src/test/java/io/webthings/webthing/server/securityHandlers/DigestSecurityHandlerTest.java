/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.webthings.webthing.server.securityHandlers;

import fi.iki.elonen.NanoHTTPD;
import io.webthings.webthing.common.SecurityScheme;
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
public class DigestSecurityHandlerTest {
    
    public DigestSecurityHandlerTest() {
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
     * Test of doSecurityCheck method, of class DigestSecurityHandler.
     */
/*    
    @Test
    public void testDoSecurityCheck() throws Exception {
        System.out.println("doSecurityCheck");
        SecurityScheme sc = null;
        NanoHTTPD.IHTTPSession session = null;
        DigestSecurityHandler instance = new DigestSecurityHandler();
        boolean expResult = false;
        boolean result = instance.doSecurityCheck(sc, session);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
*/
    /**
     * Test of checkDigest method, of class DigestSecurityHandler.
     */
    @Test
    public void testCheckDigest() throws Exception {
        
        /*
            username="",
            realm="",
            nonce="",
            uri="",
            qop=auth,
            nc=,
            cnonce="",
            response="",
            opaque="       
        
        */        
        System.out.println("checkDigest");
        final String username = "Mufasa";
        final String realm = "testrealm@host.com";
        final String response = "6629fae49393a05397450978507c4ef1";
        final String nonce = "dcd98b7102dd2f0e8b11d0f600bfb0c093";
        final String nc = "00000001";
        final String cnonce = "0a4f113b";
        final String qop = "auth";
        final String method = "GET";
        final String uri = "/dir/index.html";
        final String body = "";
        final String opaque = "5ccc069c403ebaf9f0171e9517f40e41";
        boolean expResult = true;
        DigestSecurityHandler.init(realm, "Circle Of Life", opaque, nonce);
        
        boolean result = DigestSecurityHandler.checkDigest(username, realm, response, nonce, nc, cnonce, qop, method, uri, body,opaque);
        assertEquals("Digest Result",expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        
    }
    
}
