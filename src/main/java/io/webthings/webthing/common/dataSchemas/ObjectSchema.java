package io.webthings.webthing.common.dataSchemas;

import io.webthings.webthing.JSONEntity;
import io.webthings.webthing.common.DataSchema;
import io.webthings.webthing.common.JSONEntityHelpers;
import io.webthings.webthing.exceptions.WoTException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

/**
 * @author Lorenzo
 */
public class ObjectSchema extends DataSchema {
    private Map<String, DataSchema> properties;
    private List<String> required;

    public ObjectSchema() {
        super(typeId.tiObject);
    }

    @Override
    public JSONEntity fromJSON(JSONObject o) throws WoTException {
        super.fromJSON(o);
        required = JSONEntityHelpers.readCollection(o,
                                                    "required",
                                                    String.class,
                                                    ArrayList.class);
        return this;
    }

    @Override
    public JSONObject asJSON() {
        final JSONObject ret = super.asJSON();
        JSONEntityHelpers.addCollection("required", required, ret);
        return ret;
    }

    public Map<String, DataSchema> getProperties() {
        return properties;
    }

    public void setProperties(Map<String, DataSchema> o) {
        properties = o;
    }

    public List<String> getRequired() {
        return required;
    }

    public void setRequired(List<String> o) {
        required = o;
    }
}
