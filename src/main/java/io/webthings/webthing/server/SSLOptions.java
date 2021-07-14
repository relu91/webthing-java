/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.webthings.webthing.server;

import fi.iki.elonen.NanoHTTPD;
import java.io.IOException;
import javax.net.ssl.SSLServerSocketFactory;

/**
 *
 * @author Lorenzo
 */
public class SSLOptions {
    private final String path;
    private final String password;
    private final String[] protocols;

    /**
     * Initialize the object.
     *
     * @param keystorePath     Path to the Java keystore (.jks) file
     * @param keystorePassword Password to open the keystore
     */
    public SSLOptions(String keystorePath, String keystorePassword) {
        this(keystorePath, keystorePassword, null);
    }

    /**
     * Initialize the object.
     *
     * @param keystorePath     Path to the Java keystore (.jks) file
     * @param keystorePassword Password to open the keystore
     * @param protocols        List of protocols to enable. Documentation
     *                         found here: https://docs.oracle.com/javase/8/docs/api/javax/net/ssl/SSLServerSocket.html#setEnabledProtocols-java.lang.String:A-
     */
    public SSLOptions(String keystorePath,
                      String keystorePassword,
                      String[] protocols) {
        this.path = keystorePath;
        this.password = keystorePassword;
        this.protocols = protocols;
    }

    /**
     * Create an SSLServerSocketFactory as required by NanoHTTPD.
     *
     * @return The socket factory.
     * @throws IOException If server fails to bind.
     */
    public SSLServerSocketFactory getSocketFactory() throws IOException {
        return NanoHTTPD.makeSSLSocketFactory(this.path,
                                              this.password.toCharArray());
    }

    /**
     * Get the list of enabled protocols.
     *
     * @return The list of protocols.
     */
    public String[] getProtocols() {
        return this.protocols;
    }
}