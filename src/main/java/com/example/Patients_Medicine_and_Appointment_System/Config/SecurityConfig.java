package com.example.Patients_Medicine_and_Appointment_System.Config;

import com.example.Patients_Medicine_and_Appointment_System.Service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
    @Autowired
    private CustomUserDetailsService patientDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // 1) In‐memory users for ADMIN & DOCTOR
    @Bean
    public InMemoryUserDetailsManager inMemoryUserDetailsManager(PasswordEncoder encoder) {
        return new InMemoryUserDetailsManager(
                org.springframework.security.core.userdetails.User.withUsername("admin@example.com")
                        .password(encoder.encode("admin123"))
                        .roles("ADMIN")
                        .build(),
                org.springframework.security.core.userdetails.User.withUsername("doctor@example.com")
                        .password(encoder.encode("doc123"))
                        .roles("DOCTOR")
                        .build()
        );
    }

    // 2) DAO provider for PATIENT via your JPA service
    @Bean
    public DaoAuthenticationProvider patientAuthProvider(PasswordEncoder encoder) {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(patientDetailsService);
        provider.setPasswordEncoder(encoder);
        return provider;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http,
                                           InMemoryUserDetailsManager inMem,
                                           DaoAuthenticationProvider patientAuthProvider) throws Exception {
        http
                // disable CSRF for your REST endpoint (or entirely if you prefer)
                .csrf(csrf -> csrf
                        .ignoringRequestMatchers("/api/patients/register")
                )
                .authorizeHttpRequests(auth -> auth
                        // allow open access to your REST registration
                        .requestMatchers("/api/patients/register").permitAll()
                        // Swagger/OpenAPI
                        .requestMatchers(
                                "/v3/api-docs/**",
                                "/swagger-ui.html",
                                "/swagger-ui/**"
                        ).permitAll()
                        // your existing public pages
                        .requestMatchers("/", "/login", "/register", "/css/**", "/js/**").permitAll()
                        // everything else requires login
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/default", true)
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/")
                        .invalidateHttpSession(true)
                        .clearAuthentication(true)
                        .permitAll()
                );

        // register both providers
        http.authenticationProvider(inMemAuthenticationProvider(inMem))
                .authenticationProvider(patientAuthProvider);

        return http.build();
    }

    // Wrap the in‐memory manager in a DaoAuthenticationProvider
    private DaoAuthenticationProvider inMemAuthenticationProvider(InMemoryUserDetailsManager inMem) {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(inMem);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }
}
