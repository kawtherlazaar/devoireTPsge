package com.example.sge.config;

import com.example.sge.security.JwtAuthFilter;
import com.example.sge.security.UserDetailsServiceImpl;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;

import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import org.springframework.security.config.http.SessionCreationPolicy;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtAuthFilter jwtAuthFilter;
    private final UserDetailsServiceImpl userDetailsService;

    public SecurityConfig(
            JwtAuthFilter jwtAuthFilter,
            UserDetailsServiceImpl userDetailsService
    ) {
        this.jwtAuthFilter = jwtAuthFilter;
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http)
            throws Exception {

        http

                // disable csrf
                .csrf(csrf -> csrf.disable())

                // use session for form login
                .sessionManagement(session ->
                        session.sessionCreationPolicy(
                                SessionCreationPolicy.IF_REQUIRED
                        )
                )

                // permissions
                .authorizeHttpRequests(auth -> auth

                        // auth endpoints
                        .requestMatchers("/auth/**", "/auth/login", "/login", "/css/**", "/js/**")
                        .permitAll()

                        // filière et module management (admin only)
                        .requestMatchers("/filieres/**")
                        .hasRole("ADMIN")
                        .requestMatchers("/modules/**")
                        .hasRole("ADMIN")

                        // notes form only for admin
                        .requestMatchers("/notes/form")
                        .hasRole("ADMIN")

                        // notes endpoints
                        .requestMatchers(HttpMethod.POST, "/notes/**")
                        .hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/notes/**")
                        .hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/notes/**")
                        .hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/notes/**")
                        .hasAnyRole("ADMIN", "USER")

                        // bulletins and student pages
                        .requestMatchers("/bulletins/**", "/etudiants/**")
                        .hasAnyRole("ADMIN", "USER")

                        .anyRequest()
                        .authenticated()
                )

                // form login for web pages
                .formLogin(form -> form
                        .loginPage("/auth/login")
                        .loginProcessingUrl("/login")
                        .defaultSuccessUrl("/", true)
                        .permitAll()
                )

                // logout support
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/")
                        .permitAll()
                )

                // authentication provider
                .authenticationProvider(authenticationProvider())

                // jwt filter
                .addFilterBefore(
                        jwtAuthFilter,
                        UsernamePasswordAuthenticationFilter.class
                );

        return http.build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {

        DaoAuthenticationProvider provider =
                new DaoAuthenticationProvider();

        provider.setUserDetailsService(userDetailsService);

        provider.setPasswordEncoder(passwordEncoder());

        return provider;
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration config
    ) throws Exception {

        return config.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {

        return new BCryptPasswordEncoder();
    }
}