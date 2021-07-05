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
public class InvalidFieldException extends WoTException{
    public InvalidFieldException(String name,String value) {
        super(name,"Invalid field : " + value);
    }
}
