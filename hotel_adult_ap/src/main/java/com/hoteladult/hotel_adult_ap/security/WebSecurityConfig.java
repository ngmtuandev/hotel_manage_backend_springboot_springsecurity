package com.hoteladult.hotel_adult_ap.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class WebSecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/rooms/add/new-room",
                                "/rooms/room/types",
                                "/rooms/all-room",
                                "/rooms/delete/room/{roomID}",
                                "/rooms/update/{roomID}",
                                "/rooms/room/{roomId}",
                                "/auth/register",
                                "/role/create-new-role",
                                "role/delete/{roleId}",
                                "role/assign-role-for-user",
                                "role/get_all_role",
                                "role/remove-user-from-role",
                                "users/all",
                                "users/{email}"
                                ).permitAll()
                        )
                .build();
    }

}
