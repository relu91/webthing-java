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
public class Action {
    private String              __name;
    private ActionAffordance    __data;
    private Class               __handler;
    
    public Action(String name, ActionAffordance data,Class h) {
        __name = name;
        __data = data;
        __handler = h;
    }
    
    public ActionAffordance getData() {
        return __data;
    }
    
    public void setData(ActionAffordance d) {
        __data = d;
    }
    
    public void setName(String s ) {
        __name = s;
    }
    
    public String getName() {
        return __name;
    }
    
    public Class getHandler() {
        return __handler;
    }
    
    public void setHandler(Class c ) {
        __handler = c;
    }
}
