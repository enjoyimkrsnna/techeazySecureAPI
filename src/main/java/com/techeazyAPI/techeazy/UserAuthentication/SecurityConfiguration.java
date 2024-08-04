package com.techeazyAPI.techeazy.UserAuthentication;


import com.techeazyAPI.techeazy.Utilities.KeyProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;

import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;



@Configuration

public class SecurityConfiguration {

    private final KeyProperties key;

    public SecurityConfiguration(KeyProperties keyProperties) {
        super();
        this.key = keyProperties;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(UserDetailsService service) {
        DaoAuthenticationProvider provider= new DaoAuthenticationProvider();
        provider.setUserDetailsService(service);
        provider.setPasswordEncoder(passwordEncoder());
        return new ProviderManager(provider);
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf->csrf.disable())
                .authorizeHttpRequests(auth->{
                    auth.requestMatchers("/error").permitAll();
                    auth.requestMatchers("/api/v1/auth/**").permitAll();
                    auth.requestMatchers("/admin/**").hasRole("ADMIN");
                    auth.requestMatchers("/user/**").hasAnyRole("ADMIN","USER");
                    auth.requestMatchers("/api/v1/students/**").hasAnyRole("ADMIN","USER");
                    auth.requestMatchers("/api/v1/subjects/**").hasAnyRole("ADMIN","USER");
                    auth.anyRequest().authenticated();
                })
                .oauth2ResourceServer((oauth2) -> oauth2
                        .jwt(jwt->jwt.jwtAuthenticationConverter(jwtConverter()))
                )

                .sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .build();
    }

    @Bean
    public JwtDecoder decode() {
        return NimbusJwtDecoder.withPublicKey(key.getPublicKey()).build();
    }

    @Bean
    public JwtEncoder encode() {
        JWK jwk=new RSAKey.Builder(key.getPublicKey()).privateKey(key.getPrivateKey()).build();
        JWKSource<SecurityContext> source= new ImmutableJWKSet<>(new JWKSet(jwk));
        return new NimbusJwtEncoder(source);
    }

    @Bean
    public JwtAuthenticationConverter jwtConverter() {
        JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter=new JwtGrantedAuthoritiesConverter();
        jwtGrantedAuthoritiesConverter.setAuthoritiesClaimName("roles");
        jwtGrantedAuthoritiesConverter.setAuthorityPrefix("ROLE_");
        JwtAuthenticationConverter jwtAuthenticationConverter=new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(jwtGrantedAuthoritiesConverter);
        return jwtAuthenticationConverter;
    }

}
