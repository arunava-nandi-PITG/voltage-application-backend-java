package com.example.voltage.clientimtation.config;

import com.example.voltage.clientimtation.jwt.AuthEntryPointJwt;
import com.example.voltage.clientimtation.jwt.AuthTokenFilter;
import com.example.voltage.clientimtation.service.JwtService.UserDetailsServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
@EnableMethodSecurity
public class WebSecurityConfig {

    private final UserDetailsServiceImpl userDetailsService;
    private final AuthEntryPointJwt unauthorizedHandler;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf()
                .disable()
                .cors().configurationSource(new CorsConfigurationSource() {
                    @Override
                    public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
                        CorsConfiguration cfg = new CorsConfiguration();

                        cfg.setAllowedOriginPatterns(Collections.singletonList("*"));
                        cfg.setAllowedMethods(Collections.singletonList("*"));
                        cfg.setAllowCredentials(true);
                        cfg.setAllowedHeaders(Collections.singletonList("*"));
                        cfg.setExposedHeaders(List.of(
                                "Authorization"
                        ));
                        cfg.setMaxAge(3600L);
                        return cfg;
                    }
                }).and()
                .authorizeHttpRequests().requestMatchers("/api/v1/auth/**").permitAll()
                .requestMatchers("/v2/api-docs/**").permitAll()
                .requestMatchers(HttpMethod.GET,"/api/v1/demo/**").hasRole("USER")
//                .requestMatchers("/api/v1/employee/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/v1/employee/**").hasAnyRole("USER","ADMIN")
                .requestMatchers(HttpMethod.POST, "/api/v1/employee/**").hasAnyRole("USER","ADMIN")
                .requestMatchers(HttpMethod.PUT, "/api/v1/employee/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/api/v1/employee/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.GET,"/api/v1/authentication/userAuth").hasAnyRole("USER","ADMIN")
                .requestMatchers(HttpMethod.GET,"/api/v1/authentication/adminAuth").hasAnyRole("ADMIN")
                .anyRequest()
                .authenticated()
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(unauthorizedHandler)
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(authenticationJwtTokenFilter(),UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    // TODO :: TASK ERROR check
    @Bean
    public AuthTokenFilter authenticationJwtTokenFilter() {
        return new AuthTokenFilter();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
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
