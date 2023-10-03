package com.oicapivara.gerenciadorprocessos.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = OABValidator.class)
public @interface ValidOAB {
    String message() default "A OAB é obrigatória para a criação de um usuario com perfil advogado.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
