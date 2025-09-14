package com.example.bankcards.util.validators.card;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = CardNumberValidator.class)
@Documented
public @interface ValidCardNumber {
   String message() default "Invalid card number";
   Class<?>[] groups() default {};
   Class<? extends Payload>[] payload() default {};
}