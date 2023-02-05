package com.monkeys.challenge.shared.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.core.DelegatingOAuth2TokenValidator;
import org.springframework.security.oauth2.core.OAuth2TokenValidator;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Configures our application with Spring Security to restrict access to our API endpoints.
 * @author Santi
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    //* The audience of our OAuth2 provider.
    @Value("${auth0.audience}")
    private String audience;

    //* The issuer of our OAuth2 provider.
    @Value("${spring.security.oauth2.resourceserver.jwt.issuer-uri}")
    private String issuer;

    /**
     * Configures the security filter chain.
     * @param http The HttpSecurity object to configure.
     * @return The configured security filter chain.
     * @throws Exception If an error occurs.
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        /*
        This is where we configure the security required for our endpoints and set up our app to serve as
        an OAuth2 Resource Server, using JWT validation.
        * Login and actuator endpoints are excluded from security.
        */
        http.csrf()
                .disable()
                .authorizeHttpRequests()
                .requestMatchers("/login", "/actuator/**", "/v3/**", "/error", "/swagger-ui/**", "/swagger-ui.html").permitAll()
                .anyRequest().authenticated()
                .and().cors()
                .and().oauth2ResourceServer().jwt()
                .decoder(jwtDecoder())
                .jwtAuthenticationConverter(jwtAuthenticationConverter());

        return http.build();
    }

    /**
     * Configures the JWT authentication converter.
     * @return The configured JWT authentication converter.
     */
    JwtAuthenticationConverter jwtAuthenticationConverter() {
        /*
        By default, Spring Security does not get the authorities from permissions claim of the token, to keep the authorization
        login simple. Adding our own authentication convert is easy to do:
        */
        JwtGrantedAuthoritiesConverter converter = new JwtGrantedAuthoritiesConverter();
        converter.setAuthoritiesClaimName("permissions");
        converter.setAuthorityPrefix("");

        JwtAuthenticationConverter jwtConverter = new JwtAuthenticationConverter();
        jwtConverter.setJwtGrantedAuthoritiesConverter(converter);
        return jwtConverter;
    }

    /**
     * Configures the JWT decoder.
     * @return The configured JWT decoder.
     */
    @Bean
    JwtDecoder jwtDecoder() {
        /*
        By default, Spring Security does not validate the "aud" claim of the token, to ensure that this token is
        indeed intended for our app. Adding our own validator is easy to do:
        */

        NimbusJwtDecoder jwtDecoder = (NimbusJwtDecoder)
                JwtDecoders.fromOidcIssuerLocation(issuer);

        OAuth2TokenValidator<Jwt> audienceValidator = new AudienceValidator(audience);
        OAuth2TokenValidator<Jwt> withIssuer = JwtValidators.createDefaultWithIssuer(issuer);
        OAuth2TokenValidator<Jwt> withAudience = new DelegatingOAuth2TokenValidator<>(withIssuer, audienceValidator);

        jwtDecoder.setJwtValidator(withAudience);

        return jwtDecoder;
    }
}