package id.indocyber.trollmarket.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(request ->
                request.requestMatchers("/resources/**", "/logout").permitAll()
                        .requestMatchers("/login", "/register/**").anonymous()
                        .requestMatchers("/profile/**").hasAnyAuthority("SELLER", "BUYER")
                        .requestMatchers("/shop/**", "/cart/**").hasAuthority("BUYER")
                        .requestMatchers("/shipment/**", "/admin/**", "/history/**").hasAuthority("ADMIN")
                        .requestMatchers("/merchandise/**").hasAuthority("SELLER")
                        .anyRequest().authenticated()
        ).formLogin(login -> login
                    .loginPage("/login")
                    .loginProcessingUrl("/authenticating")
                    .failureUrl("/login?error=true")
        ).logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login")
        ).csrf(request -> request.disable());
        return http.build();
    }
}
