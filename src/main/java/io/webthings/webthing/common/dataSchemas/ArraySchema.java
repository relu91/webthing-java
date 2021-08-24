package io.webthings.webthing.common.dataSchemas;

import io.webthings.webthing.JSONEntity;
import io.webthings.webthing.common.DataSchema;
import io.webthings.webthing.common.JSONEntityHelpers;
import io.webthings.webthing.exceptions.WoTException;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

/**
 * @author Lorenzo
 */
public class ArraySchema extends DataSchema {
    private List<DataSchema> items;
    private Integer minItems;
    private Integer maxItems;

    public ArraySchema() {
        super(typeId.tiArray);
    }

    @Override
    public JSONEntity fromJSON(JSONObject o) throws WoTException {
        super.fromJSON(o);
        minItems = JSONEntityHelpers.readObject(o, "minItems", Integer.class);
        maxItems = JSONEntityHelpers.readObject(o, "maxItems", Integer.class);
        items = JSONEntityHelpers.readSingleEntityOrCollection(o,
                                                               "items",
                                                               DataSchema.class,
                                                               ArrayList.class);

        return this;
    }

    @Override
    public JSONObject asJSON() {
        final JSONObject ret = super.asJSON();
        JSONEntityHelpers.addObject("minItems", minItems, ret);
        JSONEntityHelpers.addObject("maxItems", maxItems, ret);
        JSONEntityHelpers.addJSONSingleEntityOrCollection("items", items, ret);

        return ret;
    }

    public Integer getMaxItems() {
        return maxItems;
    }

    public Integer getMinItems() {
        return minItems;
    }

    public List<DataSchema> getItems() {
        return items;
    }

    public void setMaxItems(Integer i) {
        maxItems = i;
    }

    public void setMinItems(Integer i) {
        minItems = i;
    }

    public void setItems(List<DataSchema> l) {
        items = l;
    }
}
