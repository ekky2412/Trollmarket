package id.indocyber.trollmarket.utils;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;
import java.util.stream.Collectors;

public class SecurityUtil {

    public static void updateAuthorities(String username, List<String> newRoles) {
        Authentication currentAuth = SecurityContextHolder.getContext().getAuthentication();

        if (currentAuth != null && currentAuth.getName().equals(username)) {
            List<SimpleGrantedAuthority> updatedAuthorities = newRoles.stream()
                    .map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toList());

            Authentication newAuth = new UsernamePasswordAuthenticationToken(
                    currentAuth.getPrincipal(),
                    currentAuth.getCredentials(),
                    updatedAuthorities
            );

            SecurityContextHolder.getContext().setAuthentication(newAuth);
        }
    }
}