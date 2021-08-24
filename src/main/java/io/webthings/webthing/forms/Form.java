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
 * @author Lorenzo
 */
public class Form extends JSONEntity {
    private List<Operation.id> ops;
    private java.net.URI href;
    private String contentType;
    private String contentCoding;
    private String subprotocol;
    private Set<String> security;
    private Set<String> scopes;
    private ExpectedResponse response;
    private String methodName;

    //some conveniency constructors
    public Form() {

    }

    //constructor with base single items
    public Form(String href) throws URISyntaxException {
        this.href = new java.net.URI(href);
    }

    public Form(Operation.id op, String href) throws URISyntaxException {
        this(href);
        ops = new ArrayList<>();
        ops.add(op);
    }

    public Form(Operation.id op,
                String href,
                String contentType,
                String contentCoding,
                String subProtocol,
                String security,
                String scope,
                ExpectedResponse resp) throws URISyntaxException {
        this(op, href);
        this.contentType = contentType;
        this.contentCoding = contentCoding;
        subprotocol = subProtocol;
        this.security = checkedInitSet(security);
        scopes = checkedInitSet(scope);

        response = resp;
    }

    public Form(java.net.URI href) {
        this.href = href;
    }

    public Form(Operation.id op, java.net.URI href) {
        this(href);
        ops = new ArrayList<>();
        ops.add(op);
    }

    public Form(Operation.id op,
                java.net.URI href,
                String contentType,
                String contentCoding,
                String subProtocol,
                String security,
                String scope,
                ExpectedResponse resp) {
        this(op, href);
        this.contentType = contentType;
        this.contentCoding = contentCoding;
        subprotocol = subProtocol;
        this.security = checkedInitSet(security);
        scopes = checkedInitSet(scope);

        response = resp;
    }

    public void setHref(java.net.URI s) {
        href = s;
    }

    public java.net.URI getHref() {
        return href;
    }

    public void setContentType(String s) {
        contentType = s;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentCoding(String s) {
        contentCoding = s;
    }

    public String getContentCoding() {
        return contentCoding;
    }

    public void setSubprotocol(String s) {
        subprotocol = s;
    }

    public void addSecurity(String s) {
        if (security == null) {
            security = new TreeSet<>();
        }

        security.add(s);
    }

    public void removeSecurity(String s) {
        if (security != null) {
            security.remove(s);
        }
    }

    public Set<String> getSecurity() {
        return security;
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

    public ExpectedResponse getExpectedResponse() {
        return response;
    }

    public void setExpectedResponse(ExpectedResponse e) {
        response = e;
    }

    @Override
    public JSONObject asJSON() throws WoTException {
        final JSONObject o = new JSONObject();
        if (ops != null) {
            final List<String> opList = new ArrayList<>();
            for (Operation.id op : ops) {
                opList.add(Operation.decodeId(op));
            }
            addSingleItemOrList("op", opList, o);
        }
        if (href == null || href.toString().length() == 0) {
            throw new MissingFieldException("href");
        }

        JSONEntityHelpers.addURI("href", href, o);
        JSONEntityHelpers.addString("contentType", contentType, o);
        JSONEntityHelpers.addString("htv:methodName", methodName, o);
        JSONEntityHelpers.addString("contentCoding", contentCoding, o);
        JSONEntityHelpers.addString("subprotocol", subprotocol, o);
        JSONEntityHelpers.addSingleItemOrList("security", security, o);
        JSONEntityHelpers.addSingleItemOrList("scopes", scopes, o);
        JSONEntityHelpers.addJSONEntity("response", response, o);

        return o;
    }

    @Override
    public JSONEntity fromJSON(JSONObject o) throws WoTException {
        href = JSONEntityHelpers.readURI(o, "href");
        if (href == null || href.toString().length() == 0) {
            throw new MissingFieldException("href");
        }

        contentType =
                JSONEntityHelpers.readObject(o, "contentType", String.class);
        contentCoding =
                JSONEntityHelpers.readObject(o, "contentCoding", String.class);
        subprotocol =
                JSONEntityHelpers.readObject(o, "subprotocol", String.class);
        security = JSONEntityHelpers.readCollection(o,
                                                    "security",
                                                    String.class,
                                                    TreeSet.class);
        scopes = JSONEntityHelpers.readCollection(o,
                                                  "scopes",
                                                  String.class,
                                                  TreeSet.class);
        response = JSONEntityHelpers.readEntity(o,
                                                "response",
                                                ExpectedResponse.class);
        final String z =
                JSONEntityHelpers.readObject(o, "htv:methodName", String.class);
        this.setHTTPMethodName(z);

        final List<String> op_ids = JSONEntityHelpers.readObjectSingleOrList(o,
                                                                             "op",
                                                                             String.class,
                                                                             ArrayList.class);
        if (op_ids != null) {
            ops = new ArrayList<>();
            for (final String s : op_ids) {
                ops.add(Operation.decodeId(s));
            }
        }

        return this;
    }

    public List<Operation.id> getOperationList() {
        return ops;
    }

    public void setOperationList(List<Operation.id> l) {
        ops = l;
    }

    public void setOperation(Operation.id i) {
        ops = new ArrayList<>();
        ops.add(i);
    }

    public void addOperation(Operation.id i) {
        if (ops == null) {
            setOperation(i);
        } else {
            ops.add(i);
        }
    }

    public String getHTTPMethodName() {
        return methodName;
    }

    public void setHTTPMethodName(String s) throws InvalidFieldException {
        if (s == null) {
            return;
        }
        switch (s) {
            case "GET":
            case "POST":
            case "PUT":
                break;
            default:
                throw new InvalidFieldException("htv:methodName", s);
        }
        methodName = s;
    }
}
