package io.webthings.webthing.common.securitySchemas;

import io.webthings.webthing.JSONEntity;
import io.webthings.webthing.common.JSONEntityHelpers;
import io.webthings.webthing.common.SecurityScheme;
import io.webthings.webthing.exceptions.WoTException;

import org.json.JSONObject;

/**
 * @author Lorenzo
 */
public class BasicSecurityScheme extends SecurityScheme {
    public BasicSecurityScheme() {
        super(typeId.siBasic);
    }

    //used by subclasses 

    protected BasicSecurityScheme(typeId id) {
        super(id);
    }

    private LocationId.typeId in;
    private String name;

    public void setIn(LocationId.typeId in) {
        this.in = in;
    }

    public void setName(String s) {
        name = s;
    }

    public LocationId.typeId getIn() {
        return in;
    }

    public String getName() {
        return name;
    }

    @Override
    public JSONObject asJSON() throws WoTException {
        final JSONObject ret = super.asJSON();

        final String sType = LocationId.decodeTypeId(in);
        JSONEntityHelpers.addString("in", sType, ret);
        JSONEntityHelpers.addString("name", name, ret);


        return ret;
    }

    @Override
    public JSONEntity fromJSON(JSONObject o) throws WoTException {
        super.fromJSON(o);
        final String in = JSONEntityHelpers.readObject(o, "in", String.class);
        if (in != null) {
            this.in = LocationId.decodeTypeId(in);
        }

        name = JSONEntityHelpers.readObject(o, "name", String.class);


        return this;
    }
}
