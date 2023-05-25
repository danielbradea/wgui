package com.bid90.wgui.securitys;

import com.bid90.wgui.models.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Component
public class Security {

    @Autowired
    CustomAccessDeniedHandler customAccessDeniedHandler;
    @Autowired
    CustomAuthenticationFailureHandler customAuthenticationFailureHandler;

    @Autowired
    CustomAuthenticationEntryPoint  customAuthenticationEntryPoint;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    CustomUserDetailService customUserDetailsService;

    @Bean
    public AuthenticationManager authenticationManager() {

        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(customUserDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder);

        List<AuthenticationProvider> providers = List.of(authProvider);

        return new ProviderManager(providers);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
       return http.sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED))
               .csrf(AbstractHttpConfigurer::disable).authorizeHttpRequests(ahr ->
                        ahr.requestMatchers("/h2-console/**","/images/**","/").permitAll()
                                .requestMatchers("/home").hasAnyAuthority(UserRole.ADMIN.getValue(), UserRole.USER.getValue())
                                .requestMatchers("/profile").hasAnyAuthority(UserRole.ADMIN.getValue(), UserRole.USER.getValue())
                                .requestMatchers("/settings").hasAnyAuthority(UserRole.ADMIN.getValue())
                                .requestMatchers("/server").hasAnyAuthority(UserRole.ADMIN.getValue())
                                .requestMatchers("/users").hasAnyAuthority(UserRole.ADMIN.getValue())
                                .requestMatchers("/users/add").hasAnyAuthority(UserRole.ADMIN.getValue())
                                .requestMatchers("/users/{userId}/edit").hasAnyAuthority(UserRole.ADMIN.getValue())
                                .requestMatchers("/users/{userId}/delete").hasAnyAuthority(UserRole.ADMIN.getValue())
                                .requestMatchers("/users/update").hasAnyAuthority(UserRole.ADMIN.getValue())
                                .requestMatchers("/clients").hasAnyAuthority(UserRole.ADMIN.getValue())
                                .requestMatchers("/clients/add").hasAnyAuthority(UserRole.ADMIN.getValue())
                                .requestMatchers("/clients/{clientId}/edit").hasAnyAuthority(UserRole.ADMIN.getValue())
                                .requestMatchers("/clients/{clientId}/delete").hasAnyAuthority(UserRole.ADMIN.getValue())
                                .requestMatchers("/clients/update").hasAnyAuthority(UserRole.ADMIN.getValue())
                                .anyRequest().authenticated())
                .formLogin(fl ->
                        fl.loginPage("/login")
                                .usernameParameter("email")
                                .successHandler((request, response, authentication) -> {
                                    for (GrantedAuthority authority : authentication.getAuthorities()) {
                                        if (authority.getAuthority().equals(UserRole.ADMIN.getValue())) {
                                            response.sendRedirect("/users");
                                        } else if (authority.getAuthority().equals(UserRole.USER.getValue())) {
                                            response.sendRedirect("/home");
                                        }
                                    }
                                })
                                .failureUrl("/login")
                                .failureHandler(customAuthenticationFailureHandler)

                                .permitAll())
                .logout(LogoutConfigurer::permitAll)
                .exceptionHandling(eh->eh.accessDeniedHandler(customAccessDeniedHandler)
                        .authenticationEntryPoint(customAuthenticationEntryPoint))
                .headers(h->h.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable))
                .build();
    }

}
