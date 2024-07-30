package id.indocyber.trollmarket.validators.validatorclass;

import id.indocyber.trollmarket.dtos.PasswordComparable;
import id.indocyber.trollmarket.dtos.admin.AdminRegisterDto;
import id.indocyber.trollmarket.dtos.auth.AuthRegisterDto;
import id.indocyber.trollmarket.validators.annotations.ComparePassword;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ComparePasswordValidator implements ConstraintValidator<ComparePassword, PasswordComparable> {

    @Override
    public boolean isValid(PasswordComparable dto, ConstraintValidatorContext context) {
        return dto.getPassword().equals(dto.getConfirmPassword());
    }
}
