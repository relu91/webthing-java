package io.webthings.webthing.common.securitySchemas;

import io.webthings.webthing.JSONEntity;
import io.webthings.webthing.common.JSONEntityHelpers;
import io.webthings.webthing.exceptions.WoTException;

import org.json.JSONObject;

/**
 * @author Lorenzo
 */
public class BearerSecurityScheme extends BasicSecurityScheme {
    public BearerSecurityScheme() {
        super(typeId.siBearer);
    }

    private java.net.URI authorization;
    private AlgorithmId.typeId alg;
    private FormatId.typeId format;

    public AlgorithmId.typeId getAlgorithm() {
        return alg;
    }

    public FormatId.typeId getFormat() {
        return format;
    }

    public void setAlgorithm(AlgorithmId.typeId id) {
        alg = id;
    }

    public void setFormat(FormatId.typeId id) {
        format = id;
    }

    public void setAuthorization(java.net.URI u) {
        authorization = u;
    }

    public java.net.URI getAuthorization() {
        return authorization;
    }

    @Override
    public JSONObject asJSON() throws WoTException {
        final JSONObject ret = super.asJSON();
        JSONEntityHelpers.addURI("authorization", authorization, ret);
        JSONEntityHelpers.addString("alg",
                                    AlgorithmId.decodeTypeId(alg),
                                    ret);
        JSONEntityHelpers.addString("format",
                                    FormatId.decodeTypeId(format),
                                    ret);


        return ret;
    }

    @Override
    public JSONEntity fromJSON(JSONObject o) throws WoTException {
        super.fromJSON(o);

        authorization = JSONEntityHelpers.readURI(o, "authorization");

        final String sAlg =
                JSONEntityHelpers.readObject(o, "alg", String.class);
        alg = AlgorithmId.decodeTypeId(sAlg);

        final String sFmt =
                JSONEntityHelpers.readObject(o, "format", String.class);
        format = FormatId.decodeTypeId(sFmt);

        return this;
    }
}
