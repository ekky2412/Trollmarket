package id.indocyber.trollmarket.models;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class MyPasswordEncoder extends BCryptPasswordEncoder {
}
