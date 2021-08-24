package io.webthings.webthing.forms;

import io.webthings.webthing.exceptions.InvalidFieldException;

/**
 * @author Lorenzo
 */
public class Operation {
    public enum id {
        readproperty,
        writeproperty,
        observeproperty,
        unobserveproperty,
        invokeaction,
        subscribeevent,
        unsubscribeevent,
        readallproperties,
        writeallproperties,
        readmultipleproperties,
        writemultipleproperties
    }

    ;

    public static id decodeId(String i) throws InvalidFieldException {

        id ret = null;

        switch (i) {
            case "readproperty":
                ret = id.readproperty;
                break;
            case "writeproperty":
                ret = id.writeproperty;
                break;
            case "observeproperty":
                ret = id.observeproperty;
                break;
            case "unobserveproperty":
                ret = id.unobserveproperty;
                break;
            case "invokeaction":
                ret = id.invokeaction;
                break;
            case "subscribeevent":
                ret = id.subscribeevent;
                break;
            case "unsubscribeevent":
                ret = id.unsubscribeevent;
                break;
            case "readallproperties":
                ret = id.readallproperties;
                break;
            case "writeallproperties":
                ret = id.writeallproperties;
                break;

            case "readmultipleproperties":
                ret = id.readmultipleproperties;
                break;
            case "writemultipleproperties":
                ret = id.writemultipleproperties;
                break;
            default:
                throw new InvalidFieldException("operation", i);
        }

        return ret;
    }

    public static String decodeId(id i) throws InvalidFieldException {
        String ret = null;
        switch (i) {
            case readproperty:
                ret = "readproperty";
                break;
            case writeproperty:
                ret = "writeproperty";
                break;
            case observeproperty:
                ret = "observeproperty";
                break;
            case unobserveproperty:
                ret = "unobserveproperty";
                break;
            case invokeaction:
                ret = "invokeaction";
                break;
            case subscribeevent:
                ret = "subscribeevent";
                break;
            case unsubscribeevent:
                ret = "unsubscribeevent";
                break;
            case readallproperties:
                ret = "readallproperties";
                break;
            case writeallproperties:
                ret = "writeallproperties";
                break;

            case readmultipleproperties:
                ret = "readmultipleproperties";
                break;
            case writemultipleproperties:
                ret = "writemultipleproperties";
                break;
            default:
                throw new InvalidFieldException("operation", i.toString());
        }

        return ret;
    }
}
