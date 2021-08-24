/**
 * Java Web Thing server implementation.
 */
package io.webthings.webthing.server;

import fi.iki.elonen.NanoHTTPD;
import fi.iki.elonen.router.RouterNanoHTTPD;
import io.webthings.webthing.Utils;
import io.webthings.webthing.common.ExposeThingInit;
import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.jmdns.JmDNS;
import javax.jmdns.ServiceInfo;
import org.json.JSONObject;

/**
 * Server to represent a Web Thing over HTTP.
 */
public class ThingServer extends RouterNanoHTTPD {
    private static final int SOCKET_READ_TIMEOUT = 30 * 1000;
    private static final int WEBSOCKET_PING_INTERVAL = 20 * 1000;
    private final int port;
    private final List<ExposedWebThing> things;
    private String hostname;
    private final boolean disableHostValidation;
    private final List<String> hosts;
    private final boolean isTls;
    private JmDNS jmdns;

    /**
     * Initialize the ThingServer on port 80.
     *
     * @param things List of Things managed by this server
     * @throws IOException          If server fails to bind.
     * @throws NullPointerException If something bad happened.
     */
    public ThingServer(List<ExposedWebThing> things) throws IOException, NullPointerException {
        this(things, 80, null, null,  false);
    }

    /**
     * Initialize the ThingServer.
     *
     * @param things List of Things managed by this server
     * @param port   Port to listen on
     * @throws IOException          If server fails to bind.
     * @throws NullPointerException If something bad happened.
     */
    public ThingServer(List<ExposedWebThing> things, int port)  throws IOException, NullPointerException {
        this(things, port, null, null, false);
    }

    /**
     * Initialize the ThingServer.
     *
     * @param things   List of Things managed by this server
     * @param port     Port to listen on
     * @param hostname Host name, i.e. mything.com
     * @throws IOException          If server fails to bind.
     * @throws NullPointerException If something bad happened.
     */
    public ThingServer(List<ExposedWebThing> things, int port, String hostname) throws IOException, NullPointerException {
        this(things, port, hostname, null, false);
    }

    /**
     * Initialize the ThingServer.
     *
     * @param things     List of Things managed by this server
     * @param port       Port to listen on
     * @param hostname   Host name, i.e. mything.com
     * @param sslOptions SSL options to pass to the NanoHTTPD server
     * @throws IOException          If server fails to bind.
     * @throws NullPointerException If something bad happened.
     */
    public ThingServer(
        List<ExposedWebThing>   things,
        int                 port,
        String              hostname,
        SSLOptions          sslOptions
    ) throws IOException, NullPointerException {
        this(things, port, hostname, sslOptions, false);
    }



    /**
     * Initialize the ThingServer.
     *
     * @param things                List of Things managed by this server
     * @param port                  Port to listen on
     * @param hostname              Host name, i.e. mything.com
     * @param sslOptions            SSL options to pass to the NanoHTTPD server
     * @param disableHostValidation Whether or not to disable host validation --
     *                              note that this can lead to DNS rebinding
     *                              attacks
     * @throws IOException          If server fails to bind.
     * @throws NullPointerException If something bad happened.
     */
    

    public ThingServer(
        List<ExposedWebThing> things,
        int port,
        String hostname,
        SSLOptions sslOptions,
        boolean disableHostValidation
    ) throws IOException, NullPointerException {
        super(port);
        this.port = port;
        this.things = things;
        this.isTls = sslOptions != null;
        this.hostname = hostname;
        this.disableHostValidation = disableHostValidation;

        this.hosts = new ArrayList<>();
        this.hosts.add("localhost");
        this.hosts.add(String.format("localhost:%d", this.port));

        for (String address : Utils.getAddresses()) {
            this.hosts.add(address);
            this.hosts.add(String.format("%s:%d", address, this.port));
        }

        if (this.hostname != null) {
            this.hostname = this.hostname.toLowerCase();
            this.hosts.add(this.hostname);
            this.hosts.add(String.format("%s:%d", this.hostname, this.port));
        }

        if (this.isTls) {
            super.makeSecure(sslOptions.getSocketFactory(),
                             sslOptions.getProtocols());
        }

        this.setRoutePrioritizer(new RouterNanoHTTPD.InsertionOrderRoutePrioritizer());
        //load all thing data
        final ManagedThingsCollection mti = ManagedThingsCollection.getInstance();

        for(final ExposedWebThing to : this.things) {
            final ExposeThingInit td = to.getData();
            mti.add(to);
             
            
        }
       
        //register all URLs
        loadURLMap(mti.getActionsURL());
        loadURLMap(mti.getPropertiesURL());
        loadURLMap(mti.getEventsURL());
        loadURLMap(mti.getRootFormsURL());
        
        


        setNotFoundHandler(RouterNanoHTTPD.Error404UriHandler.class);
    }
    
    private void loadURLMap(Map<String,Class> coll) {
        for(final Map.Entry<String,Class> e : coll.entrySet()) {
            final String u = e.getKey();
            final Class  c = e.getValue();
            System.out.println("Added route for " + u + " for handler " + c.getName());
            addRoute(
                u, 
                c,                      
                this.hosts,
                this.isTls,
                this.disableHostValidation
            );

        }
    }
    /**
     * Start listening for incoming connections.
     *
     * @param daemon Whether or not to daemonize the server
     * @throws IOException on failure to listen on port
     */
    public void start(boolean daemon) throws IOException {
        this.jmdns = JmDNS.create(hostname == null ?
                                  InetAddress.getLocalHost() :
                                  InetAddress.getByName(hostname));

        String systemHostname = this.jmdns.getHostName();
        if (systemHostname.endsWith(".")) {
            systemHostname =
                    systemHostname.substring(0, systemHostname.length() - 1);
        }
        this.hosts.add(systemHostname);
        this.hosts.add(String.format("%s:%d", systemHostname, this.port));

        Map txt = new HashMap();
        txt.put("path", "/");

        if (this.isTls) {
            txt.put("tls", "1");
        }

        ServiceInfo serviceInfo = ServiceInfo.create(
            "_webthing._tcp.local",
            "WTS",
            null,
            this.port,
            0,
            0,
            txt
        );
        this.jmdns.registerService(serviceInfo);

        super.start(ThingServer.SOCKET_READ_TIMEOUT, daemon);
    }

    /**
     * Stop listening.
     */
    public void stop() {
        this.jmdns.unregisterAllServices();
        super.stop();
    }

    


    /**
     * Parse a JSON body.
     *
     * @param session The HTTP session
     * @return The parsed JSON body as a JSONObject, or null on error.
     */
    public JSONObject parseBody(NanoHTTPD.IHTTPSession session) {
        int contentLength = Integer.parseInt(session.getHeaders()
                                                    .get("content-length"));
        byte[] buffer = new byte[contentLength];
        try {
            session.getInputStream().read(buffer, 0, contentLength);
            return new JSONObject(new String(buffer));
        } catch (IOException e) {
            return null;
        }
    }


    /**
     * Validate Host header.
     *
     * @param uriResource The URI resource that was matched
     * @param session     The HTTP session
     * @return Boolean indicating validation success.
     */
    public boolean validateHost(RouterNanoHTTPD.UriResource uriResource,
                                NanoHTTPD.IHTTPSession session) {
        boolean disableHostValidation =
                uriResource.initParameter(2, Boolean.class);

        if (disableHostValidation) {
            return true;
        }

        List<String> hosts = uriResource.initParameter(0, List.class);

        String host = session.getHeaders().get("host");
        return (host != null && hosts.contains(host.toLowerCase()));
    }

    /**
     * Determine whether or not this request is HTTPS.
     *
     * @param uriResource The URI resource that was matched
     * @return Boolean indicating whether or not the request is secure.
     */
    public boolean isSecure(RouterNanoHTTPD.UriResource uriResource) {
        return uriResource.initParameter(1, Boolean.class);
    }

}
