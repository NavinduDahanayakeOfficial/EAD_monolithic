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

                .requestMatchers(GET,"/api/v1/order/**").hasAnyAuthority(ADMIN_READ.getPermission(), CUSTOMER_READ.getPermission(), DELIVERY_PERSON_READ.getPermission(),INVENTORY_KEEPER_READ.getPermission())
                .requestMatchers(POST,"/api/v1/order/**").hasAnyAuthority(ADMIN_CREATE.getPermission(), CUSTOMER_CREATE.getPermission())
                .requestMatchers(PUT,"/api/v1/order/**").hasAnyAuthority(ADMIN_UPDATE.getPermission(), CUSTOMER_UPDATE.getPermission())
                .requestMatchers(DELETE,"/api/v1/order/**").hasAnyAuthority(ADMIN_DELETE.getPermission(), CUSTOMER_DELETE.getPermission())
                .requestMatchers(PATCH,"/api/v1/order/**").hasAnyAuthority(ADMIN_UPDATE.getPermission(), INVENTORY_KEEPER_READ.getPermission())

                .requestMatchers(GET,"/api/v1/product/**").hasAnyAuthority(ADMIN_READ.getPermission(), CUSTOMER_READ.getPermission(), INVENTORY_KEEPER_READ.getPermission())
                .requestMatchers(POST,"/api/v1/product/**").hasAnyAuthority(ADMIN_CREATE.getPermission(), INVENTORY_KEEPER_CREATE.getPermission())
                .requestMatchers(PUT,"/api/v1/product/**").hasAnyAuthority(ADMIN_UPDATE.getPermission(), INVENTORY_KEEPER_UPDATE.getPermission())
                .requestMatchers(DELETE,"/api/v1/product/**").hasAnyAuthority(ADMIN_UPDATE.getPermission(), INVENTORY_KEEPER_UPDATE.getPermission())

                .requestMatchers(GET,"/api/v1/user/**").hasAuthority(ADMIN_READ.getPermission())
                .requestMatchers(PUT,"/api/v1/user/**").hasAuthority(ADMIN_UPDATE.getPermission())
                .requestMatchers(DELETE,"/api/v1/user/**").hasAuthority(ADMIN_UPDATE.getPermission())

                .requestMatchers(GET,"/api/v1/delivery/**").hasAnyAuthority(ADMIN_READ.getPermission(), CUSTOMER_READ.getPermission(), INVENTORY_KEEPER_READ.getPermission(), DELIVERY_PERSON_READ.getPermission())
                .requestMatchers(POST,"/api/v1/delivery/**").hasAnyAuthority(ADMIN_CREATE.getPermission(), INVENTORY_KEEPER_CREATE.getPermission())
                .requestMatchers(PATCH,"/api/v1/delivery/**").hasAnyAuthority(ADMIN_UPDATE.getPermission(), INVENTORY_KEEPER_UPDATE.getPermission(), DELIVERY_PERSON_UPDATE.getPermission())
                .requestMatchers(DELETE,"/api/v1/delivery/**").hasAnyAuthority(ADMIN_DELETE.getPermission(), INVENTORY_KEEPER_DELETE.getPermission())

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