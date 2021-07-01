/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.webthings.webthing.forms;

import io.webthings.webthing.JSONEntity;
import org.json.JSONObject;

/**
 *
 * @author Lorenzo
 */
public class ExpectedResponse extends JSONEntity {
    private String  __contentType;
    
    public ExpectedResponse() {
        
    }
    public ExpectedResponse(String c) {
        __contentType = c;
    }
    
    public void setContentType(String s ) {
        __contentType = s;
    }
    
    public String getContentType() {
        return __contentType;
    }

    @Override
    public JSONObject asJSON() {
        JSONObject ret = null;
        
        if (__contentType != null && __contentType.length() >0 ) {
            ret = new JSONObject();
            ret.put("contentType", __contentType);
        }
        return ret;
    }
}
