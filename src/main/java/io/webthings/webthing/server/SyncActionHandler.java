/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.webthings.webthing.server;

import fi.iki.elonen.NanoHTTPD;

/**
 *
 * @author Lorenzo
 */
public class SyncActionHandler extends ActionHandler{

    @Override
    protected NanoHTTPD.Response executeAction(Action a) {
        a.run();
        //return ok !
        return corsResponse(
            NanoHTTPD.newFixedLengthResponse(
                NanoHTTPD.Response.Status.OK,
                "application/json",
                null
            )
        );
        
    }
    
}
