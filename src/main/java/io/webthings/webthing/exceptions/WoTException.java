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
public class WoTException extends Exception  {
    public final   String   FieldName;
    public final   String   ErrorDescription;
    public WoTException(String fldName, String err) {
        super(err + " : " + fldName);
        FieldName = fldName;
        ErrorDescription = err;
    }
}
