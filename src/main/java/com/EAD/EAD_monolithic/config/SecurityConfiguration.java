package com.EAD.EAD_monolithic.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


import static com.EAD.EAD_monolithic.entity.Permission.*;
import static com.EAD.EAD_monolithic.entity.Role.*;
import static org.springframework.http.HttpMethod.*;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {
    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf()
                .disable()
                .authorizeHttpRequests()
                .requestMatchers("/api/v1/auth/**")
                    .permitAll()



                .requestMatchers(GET,"/api/v1/order/**").hasAnyRole(ADMIN.name(), CUSTOMER.name(), DELIVERY_PERSON.name())
                .requestMatchers(POST,"/api/v1/order/**").hasAnyAuthority(ADMIN_CREATE.name(), CUSTOMER_CREATE.name())
                .requestMatchers(PUT,"/api/v1/order/**").hasAnyAuthority(ADMIN.name())
                .requestMatchers(DELETE,"/api/v1/order/**").hasAnyAuthority(ADMIN_DELETE.name())

                .requestMatchers("api/v1/product/**").hasAnyRole(ADMIN.name(), CUSTOMER.name(), DELIVERY_PERSON.name(), INVENTORY_KEEPER.name())
                .requestMatchers(GET,"/api/v1/product/**").hasAnyAuthority(ADMIN.name(), CUSTOMER.name(), DELIVERY_PERSON.name(), INVENTORY_KEEPER.name())
                .requestMatchers(POST,"/api/v1/product/**").hasAnyAuthority(ADMIN.name(), INVENTORY_KEEPER.name())
                .requestMatchers(PUT,"/api/v1/product/**").hasAnyAuthority(ADMIN.name())
                .requestMatchers(DELETE,"/api/v1/product/**").hasAnyAuthority(ADMIN_DELETE.name())

                .anyRequest()
                .authenticated()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}