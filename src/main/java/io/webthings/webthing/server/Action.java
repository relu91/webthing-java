package io.webthings.webthing.server;

import io.webthings.webthing.affordances.ActionAffordance;

/**
 * @author Lorenzo
 */
public abstract class Action implements Runnable {
    private String name;
    private ActionAffordance data;
    private Class handler;
    private ExposedWebThing owner;

    public ExposedWebThing getOwner() {
        return owner;
    }

    public void setOwner(ExposedWebThing o) {
        owner = o;
    }

    public Action(String name, ActionAffordance data, Class h) {
        this.name = name;
        this.data = data;
        handler = h;
    }

    public ActionAffordance getData() {
        return data;
    }

    public void setData(ActionAffordance d) {
        data = d;
    }

    public void setName(String s) {
        name = s;
    }

    public String getName() {
        return name;
    }

    public Class getHandler() {
        return handler;
    }

    public void setHandler(Class c) {
        handler = c;
    }
}
