package io.webthings.webthing.exceptions;

/**
 * @author Lorenzo
 */
public class InvalidPropertyValueException extends WoTException {
    public InvalidPropertyValueException(String name, String value) {
        super(name, "Invalid value : " + value);
    }
}
