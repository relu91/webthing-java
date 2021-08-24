package io.webthings.webthing.server;

import io.webthings.webthing.affordances.ActionAffordance;
import io.webthings.webthing.affordances.EventAffordance;
import io.webthings.webthing.affordances.PropertyAffordance;
import io.webthings.webthing.common.SecurityScheme;
import io.webthings.webthing.common.ExposeThingInit;
import io.webthings.webthing.exceptions.WoTException;
import io.webthings.webthing.forms.Form;
import io.webthings.webthing.forms.Operation;

import java.net.Authenticator;
import java.net.HttpURLConnection;
import java.net.PasswordAuthentication;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;
import org.junit.Assert;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;

/**
 * @author Lorenzo
 */
public class ThingServerIT {
    private static void addProperty(String title,
                                    String desc,
                                    String href,
                                    Operation.id op,
                                    ExposeThingInit tgt)
            throws URISyntaxException, WoTException {
        final PropertyAffordance pa = new PropertyAffordance();
        pa.setDefaultTitle(title);
        pa.setDefaultDescription(desc);
        final Form f = new Form(href);


        if (op != null) {
            f.setOperation(op);
        }

        pa.addForm(f);
        tgt.addProperty("name", pa);
    }

    private static void addMetadataForm(String href,
                                        Operation.id op,
                                        ExposeThingInit tgt)
            throws URISyntaxException, WoTException {
        final Form f = new Form(href);

        if (op != null) {
            f.setOperation(op);
        }

        tgt.addForm(f);
    }

    public static List<ExposedWebThing> makeThing()
            throws URISyntaxException, WoTException {
        final List<ExposedWebThing> ret = new ArrayList<>();
        final ExposeThingInit td = new ExposeThingInit();
        addProperty("name",
                    "The real name",
                    "/single/name",
                    Operation.id.readproperty,
                    td);
        addMetadataForm("/single/allprops", Operation.id.readallproperties, td);

        final ActionAffordance aa_toggle = new ActionAffordance();
        aa_toggle.setDefaultTitle("Toggle");
        aa_toggle.setDefaultDescription("Toggle Action");
        final Form aa_form = new Form("/single/toggle");
        aa_form.setHTTPMethodName("GET");
        aa_toggle.addForm(aa_form);

        td.addAction("toggle", aa_toggle);

        final EventAffordance ee_onoff = new EventAffordance();
        ee_onoff.addForm(new Form("/single/toggled"));
        ee_onoff.setDefaultTitle("Toggled");
        ee_onoff.setDefaultDescription("Toggled Action");
        td.addEvent("toggled", ee_onoff);

        td.addSecurity("basic_sc");
        final SecurityScheme sc =
                SecurityScheme.newInstance(SecurityScheme.typeId.siBasic);
        sc.setType("basic");
        sc.setDefaultDescription("Basic Security");
        td.addSecurityDefinition("basic_sc", sc);

        final ExposedWebThing to = new ExposedWebThing(td);
        to.addAction(new ToggleAction("toggle",
                                      aa_toggle,
                                      SyncActionHandler.class));
        to.addEvent(new ToggleEvent("toggled", ee_onoff));

        ret.add(to);
        return ret;
    }


    @Test
    public void testWebSocket() {
        //first, creates a thread which start web server

        try {
            // If adding more than one thing, use MultipleThings() with a name.
            // In the single thing case, the thing's name will be broadcast.
            final List<ExposedWebThing> thing = makeThing();
            final ThingServer server = new ThingServer(thing, 8888);

            Runtime.getRuntime().addShutdownHook(new Thread(server::stop));

            server.start(false);
        } catch (Exception e) {
            System.out.println(e.toString());
            e.printStackTrace(System.err);
            System.exit(1);
            fail("Server start error: " + e.toString());
        }


        //next, creates a websocket for event
        final WebsocketClient cli = new WebsocketClient(URI.create(
                "ws://127.0.0.1:8888/single/toggled"));

        try {
            Thread.sleep(2000);
        } catch (Exception e) {

        }

        try {
            //next, send an action which eventually will fire the event:
            final URL url = new URL("http://127.0.0.1:8888/single/toggle");
            final HttpURLConnection con =
                    (HttpURLConnection)url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("Content-Type", "application/json");
            Authenticator.setDefault(new Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication("user",
                                                      "pwd".toCharArray());
                }
            });

            int status = con.getResponseCode();
            assertTrue("Status ok", status == 200);
        } catch (Exception e) {
            fail("GET invocation error : " + e.toString());
        }
        //final HttpURLConnection   actionConnection = HttpURLConnection.

        //eventually, check that event got notified
        final Thread t = new Thread() {
            public void run() {
                synchronized (cli) {
                    try {
                        cli.wait();
                        System.out.println(
                                "Received msg : " + cli.getLastMsg());

                        final JSONObject o = new JSONObject(cli.getLastMsg());
                        Assert.assertTrue("Exist data ", o.has("toggled"));
                        Assert.assertTrue("Exist content ",
                                          o.getString("toggled")
                                           .equals("Changed state !!"));
                    } catch (Exception e) {
                        System.err.println(e);
                        fail("WEB Socket response error : " + e.toString());
                    }
                }
            }
        };


        t.start();

        try {
            t.join(10000);
        } catch (Exception e) {
            fail("Thread exception : " + e.toString());
        }


    }

    public void testThingObject() {
        try {
            // If adding more than one thing, use MultipleThings() with a name.
            // In the single thing case, the thing's name will be broadcast.
            final List<ExposedWebThing> thing = makeThing();
            final ThingServer server = new ThingServer(thing, 8888);

            Runtime.getRuntime().addShutdownHook(new Thread(server::stop));

            server.start(false);
        } catch (Exception e) {
            System.out.println(e.toString());
            e.printStackTrace(System.err);
            System.exit(1);
            fail("Server start error: " + e.toString());
        }
    }
}
