/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.webthings.webthing.server;

/**
 *
 * @author Lorenzo
 */
public class Route {
    public String url;
    public Class<?> handlerClass;
    public Object[] parameters;

    /**
     * Initialize the new route.
     * <p>
     * See: https://github.com/NanoHttpd/nanohttpd/blob/master/nanolets/src/main/java/org/nanohttpd/router/RouterNanoHTTPD.java
     *
     * @param url          URL to match.
     * @param handlerClass Class which will handle the request.
     * @param parameters   Initialization parameters for class instance.
     */
    public Route(String url, Class<?> handlerClass, Object... parameters) {
        this.url = url;
        this.handlerClass = handlerClass;
        this.parameters = parameters;
    }
}

