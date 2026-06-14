package com.ashu.security;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
// i have to add a welcome page with details and their login button to go to login by using permit all to welcome page
@Configuration
public class SecurityConfig {
	

    @Autowired
    private JwtFilter jwtFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
            .cors(cors -> cors.configurationSource(corsConfigurationSource()))  //enable cors
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth


            	    .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
            	    .requestMatchers("/api/auth/**").permitAll() //thats allow all user to go to login page

            	    // USER allow only this ONE endpoint
            	    .requestMatchers("/api/tasks/yourtasks/**").hasAnyRole("USER","MANAGER")
            	    .requestMatchers("/api/tasks/update/**").hasAnyRole("USER","MANAGER")
            	    .requestMatchers("/api/saves/change-password").hasRole("USER")

            	    // ADMIN  full access to tasks + users
            	    .requestMatchers("/api/tasks/**").hasRole("MANAGER")
            	    .requestMatchers("/api/saves/**").hasRole("MANAGER")
            	    .requestMatchers("/api/saves/manager/**").hasRole("MANAGER")
            	   

            	    // everything else
            	    .anyRequest().authenticated()
            )
            .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build(); 
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {

        CorsConfiguration configuration = new CorsConfiguration();

        configuration.setAllowedOriginPatterns(List.of(
                "http://localhost:3000",
                "https://*.vercel.app"
        ));
        configuration.setAllowedMethods(List.of("GET","POST","PUT","DELETE","OPTIONS"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source =
                new UrlBasedCorsConfigurationSource();

        source.registerCorsConfiguration("/**", configuration);

        return source;
    }
    /*@Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }*/
}