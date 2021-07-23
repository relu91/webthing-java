/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.webthings.webthing.validators;

import io.webthings.webthing.common.DataSchema;
import io.webthings.webthing.common.dataSchemas.IntegerSchema;
import io.webthings.webthing.exceptions.WoTException;

/**
 *
 * @author Lorenzo
 */
public class IntegerDataValidator extends DataValidator {
    private final   IntegerSchema       __ds;
    public IntegerDataValidator(DataSchema ds) {
        __ds = (IntegerSchema) ds;
    }
    @Override
    protected boolean doValidate(Object o) throws WoTException {
        final Integer val = (Integer) o;
        boolean ret = true;
        if (__ds.getMinimum() != null) {
            ret = ret && (val >= __ds.getMinimum());
        }
        
        
        if (ret == true && __ds.getMaximum() != null) {
            ret = val <= __ds.getMaximum();
        }
 
        return ret;
    }

    @Override
    protected Integer doCast(Object o) {
        Integer ret = null;
        
        //try direct cast 
        try {
            ret = (int) o;
        } catch(Exception e ) {
            
        }
        
        if (ret == null) {
            try {
                 ret = Integer.valueOf(o.toString());
            } catch(Exception e ) {
                
            }
        }
        
        return ret;
    }
    
}
