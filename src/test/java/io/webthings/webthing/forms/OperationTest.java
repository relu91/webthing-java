package io.webthings.webthing.forms;

import io.webthings.webthing.exceptions.InvalidFieldException;

import java.util.Arrays;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Lorenzo
 */
public class OperationTest {
    /**
     * Test of decodeId method, of class Operation.
     */
    @Test

    public void testDecodeId_String() throws Exception {
        final List<String> allowedOps = Arrays.asList(new String[]{
                "readproperty",
                "writeproperty",
                "observeproperty",
                "unobserveproperty",
                "invokeaction",
                "subscribeevent",
                "unsubscribeevent",
                "readallproperties",
                "writeallproperties",
                "readmultipleproperties",
                "writemultipleproperties"
        });


        System.out.println("decodeId");

        for (String i : allowedOps) {
            Operation.id id = null;
            try {
                id = Operation.decodeId(i);
            } catch (InvalidFieldException e) {
            }
            assertTrue("Operation id not decoded : " + i, id != null);
        }


        Operation.id id = null;
        try {
            id = Operation.decodeId("not an id");
        } catch (Exception e) {

        }
        assertTrue("Wrong operation decoded ", id == null);
    }

    /**
     * Test of decodeId method, of class Operation.
     */
    @Test
    public void testDecodeId_Operationid() throws Exception {
        System.out.println("decodeId");

        for (Operation.id i : Operation.id.values()) {
            final String s = Operation.decodeId(i);
            assertTrue("Id not decoded : " + i.toString(),
                       s != null && s.length() > 0);
        }
    }
}
