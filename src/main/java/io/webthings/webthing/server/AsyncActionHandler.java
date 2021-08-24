package io.webthings.webthing.server;

import fi.iki.elonen.NanoHTTPD;

/**
 * @author Lorenzo
 */
public class AsyncActionHandler extends ActionHandler {
    @Override
    protected NanoHTTPD.Response executeAction(Action a) {
        //start worker thread
        final Thread t = new Thread(a);
        t.start();

        return corsResponse(NanoHTTPD.newFixedLengthResponse(NanoHTTPD.Response.Status.OK,
                                                             "application/json",
                                                             null));
    }
}
