package com.bootcamp.usermanager.util;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.constraints.Email;

import org.hibernate.validator.constraints.CompositionType;
import org.hibernate.validator.constraints.ConstraintComposition;

@Email
@Phone
@ConstraintComposition(CompositionType.OR)
public @interface EmailOrPhone {
	String message() default "Invalid email or phone number";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
