package io.webthings.webthing;

import io.webthings.webthing.exceptions.WoTException;
import org.json.JSONObject;

/**
 *
 * @author Lorenzo
 */
public abstract class  JSONEntity {
    public abstract JSONObject asJSON() throws WoTException;
    public abstract JSONEntity fromJSON(JSONObject o) throws WoTException;
    
}
