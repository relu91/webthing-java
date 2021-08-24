package io.webthings.webthing.validators;

import io.webthings.webthing.common.DataSchema;
import io.webthings.webthing.exceptions.WoTException;

/**
 * @author Lorenzo
 */
public abstract class DataValidator {
    protected abstract boolean doValidate(Object o) throws WoTException;

    protected abstract <__T> __T doCast(Object o);

    public boolean validate(Object o) throws WoTException {
        if (o == null) {
            return false;
        }

        boolean ret = isDataTypeCorrect(o);
        if (ret) {
            ret = doValidate(o);
        }

        return ret;
    }

    protected <__T> boolean isDataTypeCorrect(Object o) {
        boolean ret = false;
        try {
            final __T t = doCast(o);
            if (t != null) {
                ret = true;
            }
        } catch (Exception e) {

        }

        return ret;
    }

    public DataValidator newInstance(DataSchema ds) {
        Class c = null;
        switch (ds.getJSONType()) {
            case tiBoolean:
                c = BooleanDataValidator.class;
                break;
        }

        DataValidator ret = null;
        try {
            if (c != null) {
                ret = (DataValidator)c.getDeclaredConstructor(DataSchema.class)
                                      .newInstance(ds);
            }
        } catch (Exception e) {
            System.err.println(e);
        }
        return ret;
    }
}
