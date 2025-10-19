package za.ac.cput.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf ->
                        csrf.disable())// Disable Spring security that is set to default
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/admin/**").permitAll()
                        .requestMatchers("/api/regularUser/**").permitAll()
                        .requestMatchers("/api/budget/**").permitAll()
                        .requestMatchers("/api/transactions/**").permitAll()
                        .requestMatchers("/api/recurringTransactions/**").permitAll()
                        .requestMatchers("/api/category/**").permitAll()
                        .anyRequest()
                        .authenticated()

                )//close of authorizeHttpRequests
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))   //We want stateless session, because we are using JWT token for authentication
                .httpBasic(Customizer.withDefaults())
                .build(); //Has to be the last in the chain
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();

    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}