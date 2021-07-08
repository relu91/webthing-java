/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.webthings.webthing.common.dataSchemas;

import io.webthings.webthing.common.DataSchema;

/**
 *
 * @author Lorenzo
 */
public class StringSchema extends DataSchema{
    @Override
    public typeId getJSONType() {
        return typeId.tiString;
    }
}
