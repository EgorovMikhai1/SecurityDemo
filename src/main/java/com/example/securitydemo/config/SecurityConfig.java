package com.example.securitydemo.config;

import com.example.securitydemo.security.AuthTokenFilter;
import com.example.securitydemo.security.UserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final AuthTokenFilter authTokenFilter;

    private final UserDetailsServiceImpl userDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .sessionManagement(man -> man.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth ->
                        auth
                                .requestMatchers("/page_for_admins").hasRole("ADMIN")
                                .requestMatchers("/page_for_users").hasRole("USER")
                                .requestMatchers("/read_secret").hasAuthority("READ_PRIVILEGE"))
                .formLogin(Customizer.withDefaults())
                .logout(logoutPage -> logoutPage.logoutSuccessUrl("/"))
                .addFilterBefore(authTokenFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    //Если создавать юзеров прям в памяти
//    @Bean
//    public UserDetailsService userDetailsService() {
//
//        UserDetails user = User.builder()
//                .passwordEncoder(new BCryptPasswordEncoder()::encode)
//                .username("user")
//                .password("user")//{bcrypt}
//                .roles("USER")
//                .build();
//        UserDetails admin = User.builder()
//                .passwordEncoder(new BCryptPasswordEncoder()::encode)
//                .username("admin")
//                .password("admin")//{bcrypt}
//                .roles("ADMIN", "USER")
//                .build();
//        return new InMemoryUserDetailsManager(user, admin);
//    }
}
