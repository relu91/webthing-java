/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.webthings.webthing.exceptions;

/**
 *
 * @author Lorenzo
 */
public class MissingFieldException extends WoTException{
    public MissingFieldException(String name) {
        super(name,"Missing required fields");
    }
}
