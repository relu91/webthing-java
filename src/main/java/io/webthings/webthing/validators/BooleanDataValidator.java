/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.webthings.webthing.validators;

import io.webthings.webthing.common.DataSchema;
import io.webthings.webthing.common.dataSchemas.BooleanSchema;
import io.webthings.webthing.exceptions.WoTException;

/**
 *
 * @author Lorenzo
 */
class BooleanDataValidator extends DataValidator{
    public BooleanDataValidator(DataSchema ds) {
        //must simply be convertible to boolean
    }

    @Override
    protected boolean doValidate(Object o) throws WoTException {
        return true;
    }

    @Override
    protected Boolean doCast(Object o) {
        Boolean ret = null;
        try {
            //first, try direct cast 
            ret =  (boolean) o;
        } catch(Exception e ) {
            
        }
        //try indirect cast
        if (ret == null) {
            try {
                ret  = Boolean.valueOf(o.toString());
            } catch(Exception e ) {
                
            }
        }
        
        return ret;
    }
}
