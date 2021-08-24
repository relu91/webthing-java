package io.webthings.webthing.common.securitySchemas;

import io.webthings.webthing.exceptions.InvalidFieldException;
import io.webthings.webthing.exceptions.WoTException;

/**
 * @author Lorenzo
 */
public class QOPId {
    public enum typeId {
        qiAuth, qiAuthInt
    }

    public static typeId decodeId(String s) throws WoTException {
        typeId ret = null;
        if (s == null || s.length() == 0) {
            return ret;
        }

        switch (s) {
            case "auth":
                ret = typeId.qiAuth;
                break;
            case "auth-int":
                ret = typeId.qiAuthInt;
                break;
            default:
                throw new InvalidFieldException("qop", s);
        }

        return ret;
    }

    public static String decodeId(typeId id) {
        String ret = null;
        if (id == null) {
            return ret;
        }

        switch (id) {
            case qiAuth:
                ret = "auth";
                break;
            case qiAuthInt:
                ret = "auth-int";
                break;
        }

        return ret;
    }
}
