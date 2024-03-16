package com.book_store.full.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import jakarta.servlet.http.HttpServletRequest;

import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.Collections;
import com.book_store.full.filter.CsrfCookieFilter;
import com.book_store.full.filter.JwtAuthFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Autowired
    private JwtAuthFilter authFilter;

    @Bean
    // authentication
    public UserDetailsService userDetailsService() {
        // UserDetails admin = User.withUsername("Basant")
        // .password(encoder.encode("Pwd1"))
        // .roles("ADMIN")
        // .build();
        // UserDetails user = User.withUsername("John")
        // .password(encoder.encode("Pwd2"))
        // .roles("USER","ADMIN","HR")
        // .build();
        // return new InMemoryUserDetailsManager(admin, user);
        return new UserInfoUserDetailsService();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        CsrfTokenRequestAttributeHandler requestHandler = new CsrfTokenRequestAttributeHandler();
        requestHandler.setCsrfRequestAttributeName("_csrf");

        return http
                .securityContext((context) -> context.requireExplicitSave(false))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.ALWAYS))

                // .cors(corsCustomizer -> corsCustomizer.configurationSource(new CorsConfigurationSource() {
                //     @Override
                //     public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
                //         CorsConfiguration config = new CorsConfiguration();
                //         config.setAllowedOrigins(Collections.singletonList("*"));
                //         config.setAllowedMethods(Collections.singletonList("*"));
                //         config.setAllowCredentials(true);
                //         config.setAllowedHeaders(Collections.singletonList("*"));
                //         config.setMaxAge(3600L);
                //         return config;
                //     }
                // }))

                .authorizeHttpRequests(requests -> requests
                        .requestMatchers("/home", "/home/**", "/actuator/**")
                        .permitAll())

                .authorizeHttpRequests(requests -> requests
                        .anyRequest()
                        .authenticated())

                .csrf((csrf) -> csrf.csrfTokenRequestHandler(requestHandler)
                        .ignoringRequestMatchers("/home/authenticate","/home/validateToken","/addstar","/removestar","/addcart","/removecart","/home/welcome")
                        .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()))
                .addFilterAfter(new CsrfCookieFilter(), UsernamePasswordAuthenticationFilter.class)

                // .sessionManagement(management -> management
                //         .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
        // .cors(Customizer.withDefaults())
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService());
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

}
// MvcRequestMatcher
// .requestMatchers(antMatcher("/home"), antMatcher("/home/addnewuser"),
// antMatcher("/home/authenticate"))
// .requestMatchers("/home", "/home/addnewuser", "/home/authenticate", "/home/topselling",
//                                 "/home/resentllyadded", "/home/validateToken", "/home/addnewuser", "/home/verifyemail",
//                                 "/home/search", "/home/refreshtoken", "/home/generatetext", "/actuator/**")