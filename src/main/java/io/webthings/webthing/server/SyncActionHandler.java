package io.webthings.webthing.server;

import fi.iki.elonen.NanoHTTPD;

/**
 * @author Lorenzo
 */
public class SyncActionHandler extends ActionHandler {
    @Override
    protected NanoHTTPD.Response executeAction(Action a) {
        a.run();
        //return ok !
        return corsResponse(NanoHTTPD.newFixedLengthResponse(NanoHTTPD.Response.Status.OK,
                                                             "application/json",
                                                             null));
    }
}
