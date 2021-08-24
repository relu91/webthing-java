package io.webthings.webthing.common.securitySchemas;

import io.webthings.webthing.JSONEntity;
import io.webthings.webthing.common.JSONEntityHelpers;
import io.webthings.webthing.common.SecurityScheme;
import io.webthings.webthing.exceptions.WoTException;

import java.util.Set;
import java.util.TreeSet;

import org.json.JSONObject;

/**
 * @author Lorenzo
 */
public class OAuth2SecurityScheme extends SecurityScheme {
    public OAuth2SecurityScheme() {
        super(typeId.siOauth2);
    }

    private java.net.URI authorization;
    private java.net.URI token;
    private java.net.URI refresh;
    private Set<String> scopes;
    private String __flow = "code";

    public java.net.URI getAuthorization() {
        return authorization;
    }

    public void setAuthorization(java.net.URI u) {
        authorization = u;
    }

    public java.net.URI getToken() {
        return token;
    }

    public void setToken(java.net.URI u) {
        token = u;
    }

    public java.net.URI getRefresh() {
        return refresh;
    }

    public void setRefresh(java.net.URI u) {
        refresh = u;
    }

    public void addScope(String s) {
        if (scopes == null) {
            scopes = new TreeSet<>();
        }

        scopes.add(s);
    }

    public void removeScope(String s) {
        if (scopes != null) {
            scopes.remove(s);
        }
    }

    public Set<String> getScope() {
        return scopes;
    }

    public String getFlow() {
        return __flow;
    }

    public void setFlow(String s) {
        __flow = s;
    }

    @Override
    public JSONObject asJSON() throws WoTException {
        final JSONObject ret = super.asJSON();
        JSONEntityHelpers.addURI("authorization", authorization, ret);
        JSONEntityHelpers.addURI("token", token, ret);
        JSONEntityHelpers.addURI("refresh", refresh, ret);
        JSONEntityHelpers.addSingleItemOrList("scopes", scopes, ret);
        JSONEntityHelpers.addString("flow", __flow, ret);
        return ret;
    }

    @Override
    public JSONEntity fromJSON(JSONObject o) throws WoTException {
        super.fromJSON(o);
        authorization = JSONEntityHelpers.readURI(o, "authorization");
        token = JSONEntityHelpers.readURI(o, "token");
        refresh = JSONEntityHelpers.readURI(o, "refresh");
        scopes = JSONEntityHelpers.readCollection(o,
                                                  "scopes",
                                                  String.class,
                                                  TreeSet.class);
        __flow = JSONEntityHelpers.readObject(o, "flow", String.class);


        return this;
    }
}
