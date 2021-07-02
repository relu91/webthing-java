/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.webthings.webthing;

import io.webthings.webthing.exceptions.MissingFieldException;
import io.webthings.webthing.exceptions.WoTException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import org.json.JSONObject;

/**
 *
 * @author Lorenzo
 */
public abstract class  JSONEntity {
    public abstract JSONObject asJSON() throws WoTException;
    public abstract JSONEntity fromJSON(JSONObject o) throws WoTException;
    
}
