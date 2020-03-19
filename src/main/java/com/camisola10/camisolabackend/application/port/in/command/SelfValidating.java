package com.camisola10.camisolabackend.application.port.in.command;

import javax.validation.*;
import java.util.Set;

public abstract class SelfValidating<T> {

    private Validator validator;

    public SelfValidating() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    /**
     * Evaluates all Bean Validations on the attributes of this
     * instance.
     */
    protected void validateSelf() {
        Set<ConstraintViolation<T>> violations = validator.validate((T) this);
        if (!violations.isEmpty()) {
            System.out.println("validation ex");
            throw new ConstraintViolationException(violations);
        }
    }
}
