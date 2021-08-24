package io.webthings.webthing.validators;

import io.webthings.webthing.common.DataSchema;
import io.webthings.webthing.common.dataSchemas.IntegerSchema;
import io.webthings.webthing.exceptions.WoTException;

/**
 * @author Lorenzo
 */
public class IntegerDataValidator extends DataValidator {
    private final IntegerSchema integerSchema;

    public IntegerDataValidator(DataSchema ds) {
        integerSchema = (IntegerSchema)ds;
    }

    @Override
    protected boolean doValidate(Object o) throws WoTException {
        final Integer val = (Integer)o;
        boolean ret = true;
        if (integerSchema.getMinimum() != null) {
            ret = ret && (val >= integerSchema.getMinimum());
        }


        if (ret == true && integerSchema.getMaximum() != null) {
            ret = val <= integerSchema.getMaximum();
        }

        return ret;
    }

    @Override
    protected Integer doCast(Object o) {
        Integer ret = null;

        //try direct cast 
        try {
            ret = (int)o;
        } catch (Exception e) {

        }

        if (ret == null) {
            try {
                ret = Integer.valueOf(o.toString());
            } catch (Exception e) {

            }
        }

        return ret;
    }
}
