package io.webthings.webthing.exceptions;

/**
 * @author Lorenzo
 */
public class MissingFieldException extends WoTException {
    public MissingFieldException(String name) {
        super(name, "Missing required fields");
    }
}
