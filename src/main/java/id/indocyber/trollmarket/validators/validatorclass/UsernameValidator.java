package id.indocyber.trollmarket.validators.validatorclass;

import id.indocyber.trollmarket.services.AuthService;
import id.indocyber.trollmarket.validators.annotations.Username;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class UsernameValidator implements ConstraintValidator<Username, String> {
    private final AuthService authService;

    public UsernameValidator(AuthService authService) {
        this.authService = authService;
    }

    @Override
    public boolean isValid(String inputUsername, ConstraintValidatorContext context) {
        return !authService.isUsernameExist(inputUsername);
    }
}
