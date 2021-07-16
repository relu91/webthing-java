/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.webthings.webthing.forms;

import io.webthings.webthing.JSONEntity;
import io.webthings.webthing.common.JSONEntityHelpers;
import static io.webthings.webthing.common.JSONEntityHelpers.addSingleItemOrList;
import static io.webthings.webthing.common.JSONEntityHelpers.checkedInitSet;
import io.webthings.webthing.exceptions.InvalidFieldException;
import io.webthings.webthing.exceptions.MissingFieldException;
import io.webthings.webthing.exceptions.WoTException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import org.json.JSONObject;

/**
 *
 * @author Lorenzo
 */
public class Form extends JSONEntity{
    private List<Operation.id>  __ops;
    private java.net.URI        __href;
    private String              __contentType;
    private String              __contentCoding;
    private String              __subprotocol;
    private Set<String>        __security;
    private Set<String>        __scopes;
    private ExpectedResponse   __response;
    private String             __method_name;
    
    //some conveniency constructors
    public Form() {
        
    }
    //constructor with base single items
    public Form(String href) throws URISyntaxException {
        __href = new java.net.URI(href);
    }
    public Form(Operation.id op, String href) throws URISyntaxException {
        this(href);
        __ops = new ArrayList<>();
        __ops.add(op);
        
    }
    public Form(Operation.id op, String href,String contentType, String contentCoding, String subProtocol, String security, String scope, ExpectedResponse resp) throws URISyntaxException { 
        this(op, href);
        __contentType = contentType;
        __contentCoding = contentCoding;
        __subprotocol = subProtocol;
        __security = checkedInitSet(security);
        __scopes = checkedInitSet(scope);
        
        __response = resp;
        
    }
    
    public Form(java.net.URI href) {
        __href =  href;
    }
    public Form(Operation.id op, java.net.URI href)  {
        this(href);
        __ops = new ArrayList<>();
        __ops.add(op);
        
    }
    public Form(Operation.id op, java.net.URI href,String contentType, String contentCoding, String subProtocol, String security, String scope, ExpectedResponse resp)  { 
        this(op, href);
        __contentType = contentType;
        __contentCoding = contentCoding;
        __subprotocol = subProtocol;
        __security = checkedInitSet(security);
        __scopes = checkedInitSet(scope);
        
        __response = resp;
        
    }
    
    public void setHref(java.net.URI s ) {
        __href = s;
    }
    
    public java.net.URI getHref() {
        return __href;
    }
    
    public void setContentType(String s) {
        __contentType =  s;
    }
    
    public String getContentType() {
        return __contentType;
    }
    
    public void setContentCoding(String s ) {
        __contentCoding = s;
    }
    public String getContentCoding() {
        return __contentCoding;
        
    }
    
    public void setSubprotocol(String s ) {
        __subprotocol = s;
    }
    
    public void addSecurity(String s) {
        if (__security == null)
            __security = new TreeSet<>();
        
        __security.add(s);
    }
    
    public void removeSecurity(String s ) {
        if (__security != null)
            __security.remove(s);
    }
    
    public Set<String> getSecurity() {
        return __security;
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
    
    public ExpectedResponse getExpectedResponse() {
        return __response;
    }
    
    public void setExpectedResponse(ExpectedResponse e) {
        __response = e;
    }
    @Override
    public JSONObject asJSON() throws WoTException{
        final JSONObject o = new JSONObject();
        if (__ops != null) {
            final List<String> opList = new ArrayList<>();
            for(Operation.id op : __ops) {
                opList.add(Operation.decodeId(op));
            }
            addSingleItemOrList("op", opList, o);
        }
        if (__href == null || __href.toString().length() == 0 )
            throw new MissingFieldException("href");
        
        JSONEntityHelpers.addURI("href", __href, o);
        JSONEntityHelpers.addString("contentType",__contentType,o);
        JSONEntityHelpers.addString("htv:methodName",__method_name,o);
        JSONEntityHelpers.addString("contentCoding",__contentCoding,o);
        JSONEntityHelpers.addString("subprotocol",__subprotocol,o);
        JSONEntityHelpers.addSingleItemOrList("security", __security, o);
        JSONEntityHelpers.addSingleItemOrList("scopes", __scopes, o);
        JSONEntityHelpers.addJSONEntity("response", __response, o);
        
        return o;
    }

    @Override
    public JSONEntity fromJSON(JSONObject o) throws WoTException {
        __href = JSONEntityHelpers.readURI(o, "href");
        if (__href == null || __href.toString().length() == 0 )
            throw new MissingFieldException("href");
        
        __contentType = JSONEntityHelpers.readObject(o, "contentType", String.class);
        __contentCoding = JSONEntityHelpers.readObject(o, "contentCoding", String.class);
        __subprotocol = JSONEntityHelpers.readObject(o, "subprotocol", String.class);
        __security = JSONEntityHelpers.readCollection(o, "security", String.class, TreeSet.class);
        __scopes = JSONEntityHelpers.readCollection(o, "scopes", String.class, TreeSet.class);
        __response = JSONEntityHelpers.readEntity(o, "response", ExpectedResponse.class);
        final String z  = JSONEntityHelpers.readObject(o, "htv:methodName", String.class);
        this.setHTTPMethodName(z);
        
        final List<String>  op_ids = JSONEntityHelpers.readObjectSingleOrList(o, "op", String.class, ArrayList.class);
        if (op_ids != null) {
            __ops = new ArrayList<>();
            for(final String s : op_ids) {
                __ops.add(Operation.decodeId(s));
            }
        }
        
        return this;
    }
    
    public List<Operation.id> getOperationList() {
        return __ops;
    }
    
    public void setOperationList(List<Operation.id> l ) {
        __ops = l;
    }
    public void setOperation(Operation.id i ) {
        __ops = new ArrayList<>();
        __ops.add(i);
    }
    public void addOperation(Operation.id i ) {
        if (__ops == null)
            setOperation(i);
        else
            __ops.add(i);
    }
    public String getHTTPMethodName() {
        return __method_name;
    }
    
    
    
    public void setHTTPMethodName(String s ) throws InvalidFieldException{
        if (s == null)
            return;
        switch(s) {
            case "GET":
            case "POST":
            case "PUT":
                break;
            default:
                throw new InvalidFieldException("htv:methodName", s);
        }
        __method_name = s;
    }
}
