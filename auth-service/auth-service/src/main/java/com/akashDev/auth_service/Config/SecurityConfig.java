package com.akashDev.auth_service.Config;

import com.akashDev.auth_service.auth2.CustomAuthenticationSuccessHandler;
import com.akashDev.auth_service.auth2.CustomOAuth2UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final CustomOAuth2UserService customOAuth2UserService;       // inject service
    private final CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;  // inject handler


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth

                        .requestMatchers("/api/otp/send", "/api/otp/verify", "/api/otp/register","/api/otp/login").permitAll()
                        .requestMatchers("/oauth2/**").permitAll()
                        .anyRequest().authenticated()
                )
                .oauth2Login(oauth -> oauth
                        .userInfoEndpoint(user -> user.userService(customOAuth2UserService))
                        .successHandler(customAuthenticationSuccessHandler)
                )

                .formLogin(form -> form.disable());


        return http.build();
    }
}