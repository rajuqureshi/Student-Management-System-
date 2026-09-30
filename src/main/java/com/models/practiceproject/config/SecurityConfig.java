package com.models.practiceproject.config;

import com.models.practiceproject.Security.JwtAuthenticationFilter;
import com.models.practiceproject.Security.RestAccessDeniHandler;
import com.models.practiceproject.Security.RestAuthenticationEntryPoint;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig{
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;
    private final RestAccessDeniHandler restAccessDeniHandler;
    private final RestAuthenticationEntryPoint restAuthenticationEntry;

//    Securtiry Configuration
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
//                Swagger/OpenApi in spring boot
                .authorizeHttpRequests(authorizeRequests ->
                         authorizeRequests
                                 .requestMatchers(
                                         "/v3/api-docs",
                                         "/v3/api-docs/**",
                                         "/swagger-resources",
                                         "/swagger-resources/**",
                                         "/configuration/ui",
                                         "/configuration/security",
                                         "/swagger-ui/**",
                                         "/webjars/**",
                                         "/swagger-ui.html"
                                 ).permitAll()

                                 .requestMatchers("/api/auth/login"
                                         ,"/api/auth/refresh","/api/auth/logout")
                                 .permitAll()
//                                 .requestMatchers("/api/auth/refresh")
//                                 .permitAll()
                                 .requestMatchers(HttpMethod.POST,"/api/students/**")
                                 .hasRole("ADMIN")
                                 .requestMatchers(HttpMethod.GET,"/api/students/**")
                                 .hasAnyRole("ADMIN","USER")
                                 .requestMatchers(HttpMethod.PUT,"/api/students/**")
                                 .hasRole("ADMIN")
                                 .requestMatchers(HttpMethod.PATCH,"/api/students/**")
                                 .hasRole("ADMIN")
                                 .requestMatchers(HttpMethod.DELETE,"/api/students/**")
                                 .hasRole("ADMIN")
                                 .anyRequest()
                                 .authenticated()
                )
                .exceptionHandling(exceptionHandler ->
                        exceptionHandler.authenticationEntryPoint(restAuthenticationEntry)
                                        .accessDeniedHandler(restAccessDeniHandler))


                .sessionManagement(session-> session.sessionCreationPolicy(
                        SessionCreationPolicy.STATELESS)
                )
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider(userDetailsService);
        provider.setPasswordEncoder(
                passwordEncoder
        );
        return provider;
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration config)throws Exception{
        return config.getAuthenticationManager();
    }
//
//
//
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http.csrf(csrf -> csrf.disable())
//                .authorizeHttpRequests(authorizeRequests ->
//                         authorizeRequests.requestMatchers(HttpMethod.GET, "/api/students/**")
//                                 .hasAnyRole("USER","ADMIN")
//                                 .requestMatchers(HttpMethod.POST,"/api/students/**")
//                                 .hasRole("ADMIN")
//                                 .requestMatchers(HttpMethod.PUT,"/api/students/**")
//                                 .hasRole("ADMIN")
//                                 .requestMatchers(HttpMethod.DELETE,"/api/students/**")
//                                 .hasRole("ADMIN")
//                                 .requestMatchers(HttpMethod.PATCH,"/api/students/**")
//                                 .hasRole("ADMIN")
//                                .anyRequest().authenticated()
//                )
//                .httpBasic(Customizer.withDefaults());
//        return http.build();
//    }

//    In-Memory user - password

//    @Bean
//    public UserDetailsService userDetailsService() {
////        FOR USER
//        UserDetails user = User.builder()
//                .username("user")
////                .password("{noop}password")
//                .password(passwordEncoder.encode("password"))
//                .roles("USER")
//                .build();
//
////        FOR ADMIN
//
//        UserDetails admin = User.builder()
//                .username("Admin")
////                .password("{noop}admin")
//                .password(passwordEncoder.encode("admin"))
//                .roles("ADMIN")
//                .build();
//        return new InMemoryUserDetailsManager(user, admin);
//    }
}
