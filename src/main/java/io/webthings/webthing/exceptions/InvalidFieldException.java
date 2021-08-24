package io.webthings.webthing.exceptions;

/**
 * @author Lorenzo
 */
public class InvalidFieldException extends WoTException {
    public InvalidFieldException(String name, String value) {
        super(name, "Invalid field : " + value);
    }
}
