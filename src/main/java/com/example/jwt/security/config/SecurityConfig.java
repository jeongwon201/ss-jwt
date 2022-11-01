package com.example.jwt.security.config;

import com.example.jwt.security.filter.JWTAuthenticationFilter;
import com.example.jwt.security.filter.LoginAuthenticationFilter;
import com.example.jwt.security.handler.JWTAccessDeniedHandler;
import com.example.jwt.security.handler.LoginAuthenticationFailureHandler;
import com.example.jwt.security.handler.LoginAuthenticationSuccessHandler;
import com.example.jwt.security.jwt.FilterSkipMatcher;
import com.example.jwt.security.jwt.HeaderTokenExtractor;
import com.example.jwt.security.provider.JWTAuthenticationProvider;
import com.example.jwt.security.provider.LoginAuthenticationProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private LoginAuthenticationProvider loginAuthenticationProvider;
    private JWTAuthenticationProvider jwtAuthenticationProvider;
    private LoginAuthenticationSuccessHandler loginAuthenticationSuccessHandler;
    private LoginAuthenticationFailureHandler loginAuthenticationFailureHandler;
    private JWTAccessDeniedHandler jwtAccessDeniedHandler;
    private HeaderTokenExtractor headerTokenExtractor;

    public SecurityConfig(LoginAuthenticationProvider loginAuthenticationProvider, LoginAuthenticationSuccessHandler loginAuthenticationSuccessHandler, LoginAuthenticationFailureHandler loginAuthenticationFailureHandler, JWTAuthenticationProvider jwtAuthenticationProvider, HeaderTokenExtractor headerTokenExtractor, JWTAccessDeniedHandler jwtAccessDeniedHandler) {
        this.loginAuthenticationProvider = loginAuthenticationProvider;
        this.loginAuthenticationSuccessHandler = loginAuthenticationSuccessHandler;
        this.loginAuthenticationFailureHandler = loginAuthenticationFailureHandler;
        this.jwtAuthenticationProvider = jwtAuthenticationProvider;
        this.headerTokenExtractor = headerTokenExtractor;
        this.jwtAccessDeniedHandler = jwtAccessDeniedHandler;
    }

    @Bean
    public AuthenticationManager getAuthenticationManager() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .authenticationProvider(loginAuthenticationProvider)
                .authenticationProvider(this.jwtAuthenticationProvider);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                .csrf()
                .disable();

        http
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http
                .httpBasic()
                .disable();

        http
                .addFilterBefore(loginAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

        http
                .exceptionHandling()
                .accessDeniedHandler(jwtAccessDeniedHandler);

        http
                .authorizeRequests()
                .antMatchers("/test").hasRole("USER");

        http
                .cors()
                .configurationSource(corsConfigurationSource());

    }

    protected LoginAuthenticationFilter loginAuthenticationFilter() throws Exception {
        LoginAuthenticationFilter filter = new LoginAuthenticationFilter(
                new AntPathRequestMatcher("/login", HttpMethod.POST.name()),
                loginAuthenticationSuccessHandler,
                loginAuthenticationFailureHandler);

        filter.setAuthenticationManager(super.authenticationManagerBean());

        return filter;
    }

    private JWTAuthenticationFilter jwtAuthenticationFilter() throws Exception {
        List<AntPathRequestMatcher> skipPath = new ArrayList<>();

        skipPath.add(new AntPathRequestMatcher("/error"));
        skipPath.add(new AntPathRequestMatcher("/static"));
        skipPath.add(new AntPathRequestMatcher("/static/**"));

        skipPath.add(new AntPathRequestMatcher("/h2-console"));
        skipPath.add(new AntPathRequestMatcher("/h2-console/**"));
        skipPath.add(new AntPathRequestMatcher("/account", HttpMethod.POST.name()));
        skipPath.add(new AntPathRequestMatcher("/login", HttpMethod.POST.name()));

        FilterSkipMatcher matcher = new FilterSkipMatcher(skipPath, "/**");

        JWTAuthenticationFilter filter = new JWTAuthenticationFilter(matcher, headerTokenExtractor);

        filter.setAuthenticationManager(super.authenticationManagerBean());

        return filter;
    }

    // CORS Config
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        configuration.setAllowedOriginPatterns(Arrays.asList("https://localhost:3000"));
        configuration.setAllowedMethods(Arrays.asList("OPTIONS", "GET", "POST", "PUT", "DELETE", "HEAD"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.setAllowedOrigins(Arrays.asList("https://localhost:3000"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}