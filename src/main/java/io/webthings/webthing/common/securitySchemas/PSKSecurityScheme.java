package io.webthings.webthing.common.securitySchemas;

import io.webthings.webthing.JSONEntity;
import io.webthings.webthing.common.JSONEntityHelpers;
import io.webthings.webthing.common.SecurityScheme;
import io.webthings.webthing.exceptions.WoTException;

import org.json.JSONObject;

/**
 * @author Lorenzo
 */
public class PSKSecurityScheme extends SecurityScheme {
    public PSKSecurityScheme() {
        super(typeId.siPsk);
    }

    private String identity;

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String s) {
        identity = s;
    }

    @Override
    public JSONObject asJSON() throws WoTException {
        final JSONObject ret = super.asJSON();
        JSONEntityHelpers.addString("identity", identity, ret);

        return ret;
    }

    @Override
    public JSONEntity fromJSON(JSONObject o) throws WoTException {
        super.fromJSON(o);
        identity = JSONEntityHelpers.readObject(o, "identity", String.class);
        return this;
    }
}
