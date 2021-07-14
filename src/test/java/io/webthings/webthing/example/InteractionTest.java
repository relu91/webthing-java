/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.webthings.webthing.example;

import io.webthings.webthing.affordances.InteractionAffordance;
import io.webthings.webthing.forms.Form;
import io.webthings.webthing.forms.Operation.id;
import org.json.JSONObject;

/**
 *
 * @author Lorenzo
 */
public class InteractionTest {
    public static void main(String[] args  ) {
        try {
            final InteractionAffordance i = new InteractionAffordance("boolean", "AInt", "a Weird interaction ", new Form("/pippo.pluto"));
            System.out.println(i.asJSON().toString());
            
            i.addForm(new Form(id.invokeaction,"http://giug"));
            final JSONObject o2 = i.asJSON();

            System.out.println(o2.toString());
        } catch(Exception e ) {
            System.err.println(e);
        }
    }
}
