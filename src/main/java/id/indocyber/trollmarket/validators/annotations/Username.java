package id.indocyber.trollmarket.validators.annotations;

import id.indocyber.trollmarket.validators.validatorclass.UsernameValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})                        //tentuin dia bisa di pake dimananya
@Retention(RetentionPolicy.RUNTIME)                 //harus runtime
@Constraint(validatedBy = UsernameValidator.class)  //jangan lupa bikin classnya
public @interface Username {
    String message() default "Username already exist";
    public Class<?>[] groups() default{};
    public Class<? extends Payload>[] payload() default{};
}
