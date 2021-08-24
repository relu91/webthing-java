package io.webthings.webthing.common;

import io.webthings.webthing.JSONEntity;
import io.webthings.webthing.common.securitySchemas.APIKeySecurityScheme;
import io.webthings.webthing.common.securitySchemas.BasicSecurityScheme;
import io.webthings.webthing.common.securitySchemas.BearerSecurityScheme;
import io.webthings.webthing.common.securitySchemas.DigestSecurityScheme;
import io.webthings.webthing.common.securitySchemas.NoSecurityScheme;
import io.webthings.webthing.common.securitySchemas.OAuth2SecurityScheme;
import io.webthings.webthing.common.securitySchemas.PSKSecurityScheme;
import io.webthings.webthing.exceptions.InvalidFieldException;
import io.webthings.webthing.exceptions.WoTException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.json.JSONObject;

/**
 * @author Lorenzo
 */
public class SecurityScheme extends JSONEntity {
    public enum typeId {
        siNosec, siBasic, siDigest, siBearer, siPsk, siOauth2, siApikey
    }

    public static typeId decodeTypeId(String v) {
        typeId ret = null;
        if (v == null || v.length() == 0) {
            return ret;
        }

        switch (v) {
            case "nosec":
                ret = typeId.siNosec;
                break;
            case "basic":
                ret = typeId.siBasic;
                break;
            case "digest":
                ret = typeId.siDigest;
                break;
            case "bearer":
                ret = typeId.siBearer;
                break;
            case "psk":
                ret = typeId.siPsk;
                break;
            case "oauth2":
                ret = typeId.siOauth2;
                break;
            case "apikey":
                ret = typeId.siApikey;
                break;
        }

        return ret;
    }

    public static String decodeTypeId(typeId i) {
        String ret = null;
        if (i == null) {
            return ret;
        }

        switch (i) {
            case siApikey:
                ret = "apikey";
                break;
            case siBasic:
                ret = "basic";
                break;
            case siBearer:
                ret = "bearer";
                break;
            case siDigest:
                ret = "digest";
                break;
            case siNosec:
                ret = "nosec";
                break;
            case siOauth2:
                ret = "oauth2";
                break;
            case siPsk:
                ret = "psk";
                break;
        }

        return ret;
    }

    protected SecurityScheme(typeId id) {
        scheme = id;
    }

    private String description;
    private Map<String, String> descriptions;
    private List<String> types;
    private typeId scheme;
    private java.net.URI proxy;

    public void setType(String t) {
        types = new ArrayList<>();
        types.add(t);
    }

    public String getType() {
        String ret = null;
        if (types != null && types.size() >= 1) {
            ret = types.get(0);
        }
        return ret;
    }

    public List<String> getTypes() {
        return types;
    }

    public void addType(String t) {
        if (types == null) {
            setType(t);
        } else {
            types.add(t);
        }
    }

    public void setDefaultDescription(String d) {
        description = d;
    }

    public String getDefaultDescription() {
        return description;
    }

    public void setI18NDescription(String lang, String d) {
        if (descriptions == null) {
            descriptions = new TreeMap<>();
        }
        descriptions.put(lang, d);
    }

    public String getI18NDescription(String lang) {
        String ret = null;
        if (descriptions != null) {
            ret = descriptions.get(lang);
        }

        return ret;
    }

    public void removeI18NDescription(String lang) {
        if (descriptions != null) {
            descriptions.remove(lang);
        }
    }

    public typeId getScheme() {
        return scheme;
    }

    public java.net.URI getProxy() {
        return proxy;
    }

    public void setProxy(java.net.URI u) {
        proxy = u;
    }

    @Override
    public JSONObject asJSON() throws WoTException {
        final JSONObject ret = new JSONObject();
        JSONEntityHelpers.addSingleItemOrList("@type", types, ret);
        JSONEntityHelpers.addString("description", description, ret);
        JSONEntityHelpers.addCollection("descriptions", descriptions, ret);
        JSONEntityHelpers.addString("scheme", decodeTypeId(scheme), ret);
        JSONEntityHelpers.addURI("proxy", proxy, ret);

        return ret;
    }

    @Override
    public JSONEntity fromJSON(JSONObject o) throws WoTException {
        description =
                JSONEntityHelpers.readObject(o, "description", String.class);
        descriptions = JSONEntityHelpers.readCollection(o,
                                                        "descriptions",
                                                        String.class,
                                                        TreeMap.class);
        types = JSONEntityHelpers.readObjectSingleOrList(o,
                                                         "@type",
                                                         String.class,
                                                         ArrayList.class);
        final String scheme =
                JSONEntityHelpers.readObject(o, "scheme", String.class);
        this.scheme = decodeTypeId(scheme);

        proxy = JSONEntityHelpers.readURI(o, "proxy");


        return this;
    }

    public static SecurityScheme newInstance(JSONObject o) throws WoTException {
        final String scheme =
                JSONEntityHelpers.readObject(o, "scheme", String.class);
        if (scheme == null || scheme.length() == 0) {
            throw new InvalidFieldException("scheme", "");
        }

        final SecurityScheme.typeId id = SecurityScheme.decodeTypeId(scheme);

        final SecurityScheme ret = newInstance(id);
        if (ret != null) {
            ret.fromJSON(o);
        }

        return ret;
    }

    public static SecurityScheme newInstance(typeId id) {
        if (id == null) {
            return null;
        }

        SecurityScheme ret = null;
        switch (id) {
            case siApikey:
                ret = new APIKeySecurityScheme();
                break;
            case siBasic:
                ret = new BasicSecurityScheme();
                break;
            case siBearer:
                ret = new BearerSecurityScheme();
                break;
            case siDigest:
                ret = new DigestSecurityScheme();
                break;
            case siNosec:
                ret = new NoSecurityScheme();
                break;
            case siOauth2:
                ret = new OAuth2SecurityScheme();
                break;
            case siPsk:
                ret = new PSKSecurityScheme();
                break;
        }

        return ret;
    }
}
