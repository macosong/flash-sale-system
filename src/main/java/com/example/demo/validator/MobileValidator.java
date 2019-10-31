package com.example.demo.validator;

import com.example.demo.utils.ValidatorUtil;
import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.annotation.Annotation;

/**
 * MobileValidator class
 *
 * @author maco
 * @data 2019/10/24
 */
public class MobileValidator implements ConstraintValidator<MobileCheck, String>{

    private boolean require = false;

    @Override
    public void initialize(MobileCheck isMobile) {
        require = isMobile.required();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        if (require){
            return ValidatorUtil.isMobile(value);
        }else {
            if (StringUtils.isEmpty(value)){
                return true;
            }else{
                return ValidatorUtil.isMobile(value);
            }
        }
    }
}
