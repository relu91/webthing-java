package io.webthings.webthing.exceptions;

/**
 * @author Lorenzo
 */
public class WoTException extends Exception {
    public final String fieldName;
    public final String errorDescription;

    public WoTException(String fldName, String err) {
        super(err + " : " + fldName);
        fieldName = fldName;
        errorDescription = err;
    }
}
