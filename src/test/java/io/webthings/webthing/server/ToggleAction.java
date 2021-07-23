/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.webthings.webthing.server;

import io.webthings.webthing.affordances.ActionAffordance;



/**
 *
 * @author Lorenzo
 */
public class ToggleAction extends Action {
    private boolean  __state = false;
    public ToggleAction(String name, ActionAffordance data,Class h) {
        super(name, data, h);
    }

    public void run() {
        System.out.println("Current state : " + __state);
        __state = !__state;
        System.out.println("New  state : " + __state);

        //fire toggled event
        
        
        final Event e = this.getOwner().getEvent("toggled");
        if (e != null)
            e.notifyEvent();

    }

}
