/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.webthings.webthing.common.securitySchemas;

import io.webthings.webthing.JSONEntity;
import io.webthings.webthing.common.JSONEntityHelpers;
import io.webthings.webthing.common.SecurityScheme;
import io.webthings.webthing.exceptions.WoTException;
import java.util.Set;
import java.util.TreeSet;
import org.json.JSONObject;

/**
 *
 * @author Lorenzo
 */
public class OAuth2SecurityScheme extends SecurityScheme{
    public OAuth2SecurityScheme() {
        super(typeId.siOauth2);
        
    }
    
    private java.net.URI        __authorization;
    private java.net.URI        __token;
    private java.net.URI        __refresh;
    private Set<String>         __scopes;
    private String              __flow      =   "code";
    
    
    public  java.net.URI getAuthorization() {
        return  __authorization;
    }
    public void setAuthorization(java.net.URI u) {
        __authorization = u;
    }
    
    public  java.net.URI getToken() {
        return  __token;
    }
    public void setToken(java.net.URI u) {
        __token = u;
    }
    
    public  java.net.URI getRefresh() {
        return  __refresh;
    }
    public void setRefresh(java.net.URI u) {
        __refresh = u;
    }
    
    public void addScope(String s) {
        if (__scopes == null)
            __scopes = new TreeSet<>();
        
        __scopes.add(s);
    }
    
    public void removeScope(String s ) {
        if (__scopes != null)
            __scopes.remove(s);
    }
    
    public Set<String> getScope() {
        return __scopes;
    }

    public String getFlow() {
        return __flow;
    }
    
    public void setFlow(String s ) {
        __flow = s;
    }
    
    @Override
    public JSONObject asJSON() throws WoTException {
        final JSONObject ret = super.asJSON();
        JSONEntityHelpers.addURI("authorization", __authorization, ret);
        JSONEntityHelpers.addURI("token", __token, ret);
        JSONEntityHelpers.addURI("refresh", __refresh, ret);
        JSONEntityHelpers.addSingleItemOrList("scopes", __scopes, ret);
        JSONEntityHelpers.addString("flow", __flow, ret);
        return ret;
    }
    
    @Override 
    public JSONEntity fromJSON(JSONObject o) throws WoTException {
        super.fromJSON(o);
        __authorization = JSONEntityHelpers.readURI(o, "authorization");
        __token = JSONEntityHelpers.readURI(o, "token");
        __refresh = JSONEntityHelpers.readURI(o, "refresh");
        __scopes = JSONEntityHelpers.readCollection(o, "scopes", String.class, TreeSet.class);
        __flow = JSONEntityHelpers.readObject(o, "flow", String.class);
        
        
        return this;
    }
    
}
