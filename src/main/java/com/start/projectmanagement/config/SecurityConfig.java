package com.start.projectmanagement.config;

import com.start.projectmanagement.security.JwtFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class SecurityConfig {

    @Autowired private JwtFilter jwtFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .cors(withDefaults())
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth

                        // ✅ Frontend static files
                        .requestMatchers(
                                new AntPathRequestMatcher("/"),
                                new AntPathRequestMatcher("/index.html"),
                                new AntPathRequestMatcher("/health"),
                                new AntPathRequestMatcher("/favicon.ico"),
                                new AntPathRequestMatcher("/*.css"),
                                new AntPathRequestMatcher("/*.js"),
                                new AntPathRequestMatcher("/*.png"),
                                new AntPathRequestMatcher("/*.jpg"),
                                new AntPathRequestMatcher("/static/**")
                        ).permitAll()

                        // Public auth routes
                        .requestMatchers("/auth/**").permitAll()

                        // ADMIN ONLY — user management
                        .requestMatchers("/users/**").hasRole("ADMIN")

                        // ADMIN ONLY — create & delete projects
                        .requestMatchers(HttpMethod.POST, "/projects/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/projects/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/projects/**").hasRole("ADMIN")

                        // ADMIN ONLY — create & delete tasks
                        .requestMatchers(HttpMethod.POST, "/tasks/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/tasks/**").hasRole("ADMIN")

                        // EMPLOYEE can view projects and tasks, update task status
                        .requestMatchers(HttpMethod.GET, "/projects/**").hasAnyRole("ADMIN", "EMPLOYEE")
                        .requestMatchers(HttpMethod.GET, "/tasks/**").hasAnyRole("ADMIN", "EMPLOYEE")
                        .requestMatchers(HttpMethod.PUT, "/tasks/**").hasAnyRole("ADMIN", "EMPLOYEE")
                        .requestMatchers(HttpMethod.PATCH, "/tasks/**").hasAnyRole("ADMIN", "EMPLOYEE")

                        .anyRequest().authenticated()
                )
                .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}