package io.webthings.webthing.common.dataSchemas;

import io.webthings.webthing.common.DataSchema;

/**
 * @author Lorenzo
 */
public class NullSchema extends DataSchema {
    public NullSchema() {
        super(typeId.tiNull);
    }
}
