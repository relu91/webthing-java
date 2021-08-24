package io.webthings.webthing.common.securitySchemas;

import io.webthings.webthing.common.SecurityScheme;

/**
 * @author Lorenzo
 */
public class NoSecurityScheme extends SecurityScheme {
    public NoSecurityScheme() {
        super(typeId.siNosec);
    }
}
