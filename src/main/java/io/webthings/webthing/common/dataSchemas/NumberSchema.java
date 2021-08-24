package io.webthings.webthing.common.dataSchemas;

import io.webthings.webthing.JSONEntity;
import io.webthings.webthing.common.DataSchema;
import io.webthings.webthing.common.JSONEntityHelpers;
import io.webthings.webthing.exceptions.WoTException;

import org.json.JSONObject;

/**
 * @author Lorenzo
 */
public class NumberSchema extends DataSchema {
    private Double minimum;
    private Double maximum;

    public NumberSchema() {
        super(typeId.tiNumber);
    }

    @Override
    public JSONEntity fromJSON(JSONObject o) throws WoTException {
        super.fromJSON(o);
        minimum = JSONEntityHelpers.readObject(o, "minimum", Double.class);
        maximum = JSONEntityHelpers.readObject(o, "maximum", Double.class);

        return this;
    }

    @Override
    public JSONObject asJSON() {
        final JSONObject ret = super.asJSON();
        JSONEntityHelpers.addObject("minimum", minimum, ret);
        JSONEntityHelpers.addObject("maximum", maximum, ret);
        return ret;
    }

    public Double getMinimum() {
        return minimum;
    }

    public Double getMaximum() {
        return maximum;
    }

    public void setMinimum(Double d) {
        minimum = d;
    }

    public void setMaximum(Double d) {
        maximum = d;
    }
}
