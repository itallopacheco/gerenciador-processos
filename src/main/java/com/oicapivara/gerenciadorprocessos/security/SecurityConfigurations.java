package com.oicapivara.gerenciadorprocessos.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
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
public class SecurityConfigurations {

    @Autowired
    SecurityFilter securityFilter;

    @Bean
   public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity)throws Exception{
       httpSecurity
               .csrf(csrf -> csrf.disable())
               .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
               .authorizeHttpRequests(authorize -> authorize
                       .requestMatchers(HttpMethod.POST,"/pessoa/login").permitAll()
                       .requestMatchers(HttpMethod.POST,"/pessoa").permitAll()
                       .requestMatchers(HttpMethod.POST,"/processo").hasRole("ADVOGADO")
                       .requestMatchers(HttpMethod.PATCH,"/processo").hasRole("ADVOGADO")
                       .requestMatchers(HttpMethod.DELETE,"/processo").hasRole("ADVOGADO")
                       .anyRequest().authenticated()
               )
               .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class);
       return httpSecurity.build();
   }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception{
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){return new BCryptPasswordEncoder();
    }

}
