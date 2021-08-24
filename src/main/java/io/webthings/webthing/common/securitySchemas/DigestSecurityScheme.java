package io.webthings.webthing.common.securitySchemas;

import io.webthings.webthing.JSONEntity;
import io.webthings.webthing.common.JSONEntityHelpers;
import io.webthings.webthing.exceptions.WoTException;

import org.json.JSONObject;

/**
 * @author Lorenzo
 */
public class DigestSecurityScheme extends BasicSecurityScheme {
    public DigestSecurityScheme() {
        super(typeId.siDigest);
    }

    private QOPId.typeId qop;

    public QOPId.typeId getQOP() {
        return qop;
    }

    public void setQOP(QOPId.typeId id) {
        qop = id;
    }

    @Override
    public JSONObject asJSON() throws WoTException {
        final JSONObject ret = super.asJSON();
        final String s = QOPId.decodeId(qop);
        JSONEntityHelpers.addObject("qop", s, ret);
        return ret;
    }

    @Override
    public JSONEntity fromJSON(JSONObject o) throws WoTException {
        super.fromJSON(o);
        final String s = JSONEntityHelpers.readObject(o, "qop", String.class);
        qop = QOPId.decodeId(s);

        return this;
    }
}
