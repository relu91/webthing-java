package io.webthings.webthing.common.dataSchemas;

import io.webthings.webthing.JSONEntity;
import io.webthings.webthing.common.DataSchema;
import io.webthings.webthing.common.JSONEntityHelpers;
import io.webthings.webthing.exceptions.WoTException;

import org.json.JSONObject;

/**
 * @author Lorenzo
 */
public class IntegerSchema extends DataSchema {
    private Integer minimum;
    private Integer maximum;

    public IntegerSchema() {
        super(typeId.tiInteger);
    }

    @Override
    public JSONEntity fromJSON(JSONObject o) throws WoTException {
        super.fromJSON(o);
        minimum = JSONEntityHelpers.readObject(o, "minimum", Integer.class);
        maximum = JSONEntityHelpers.readObject(o, "maximum", Integer.class);

        return this;
    }

    @Override
    public JSONObject asJSON() {
        final JSONObject ret = super.asJSON();
        JSONEntityHelpers.addObject("minimum", minimum, ret);
        JSONEntityHelpers.addObject("maximum", maximum, ret);
        return ret;
    }

    public Integer getMinimum() {
        return minimum;
    }

    public Integer getMaximum() {
        return maximum;
    }

    public void setMinimum(Integer d) {
        minimum = d;
    }

    public void setMaximum(Integer d) {
        maximum = d;
    }
}
