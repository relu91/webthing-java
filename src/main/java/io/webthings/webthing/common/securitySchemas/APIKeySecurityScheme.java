package io.webthings.webthing.common.securitySchemas;

/**
 * @author Lorenzo
 */
public class APIKeySecurityScheme extends BasicSecurityScheme {
    public APIKeySecurityScheme() {
        super(typeId.siApikey);
    }
}
