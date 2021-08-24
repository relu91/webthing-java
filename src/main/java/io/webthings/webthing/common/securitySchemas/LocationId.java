package io.webthings.webthing.common.securitySchemas;

import io.webthings.webthing.exceptions.InvalidFieldException;
import io.webthings.webthing.exceptions.WoTException;

/**
 * @author Lorenzo
 */
public class LocationId {
    public enum typeId {
        liHeader, liBody, liCookie, liQuery
    }

    public static String decodeTypeId(typeId id) {
        String ret = null;
        if (id == null) {
            return ret;
        }

        switch (id) {
            case liBody:
                ret = "body";
                break;
            case liCookie:
                ret = "cookie";
                break;
            case liHeader:
                ret = "header";
                break;
            case liQuery:
                ret = "query";
                break;
        }

        return ret;
    }

    public static typeId decodeTypeId(String s) throws WoTException {
        typeId ret = null;
        if (s == null || s.length() == 0) {
            return ret;
        }

        switch (s) {
            case "header":
                ret = typeId.liHeader;
                break;
            case "body":
                ret = typeId.liBody;
                break;
            case "query":
                ret = typeId.liQuery;
                break;
            case "cookie":
                ret = typeId.liCookie;
                break;
            default:
                throw new InvalidFieldException("in", s);
        }

        return ret;
    }
}
