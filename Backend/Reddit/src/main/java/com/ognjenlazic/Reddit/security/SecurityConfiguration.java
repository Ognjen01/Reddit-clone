package com.ognjenlazic.Reddit.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    public void configureAuthentication(
            AuthenticationManagerBuilder authenticationManagerBuilder)
            throws Exception {

        authenticationManagerBuilder
                .userDetailsService(this.userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public AuthenticationTokenFilter authenticationTokenFilterBean()
            throws Exception {
        AuthenticationTokenFilter authenticationTokenFilter = new AuthenticationTokenFilter();
        authenticationTokenFilter
                .setAuthenticationManager(authenticationManagerBean());
        return authenticationTokenFilter;
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception{

        httpSecurity.headers().cacheControl().disable();

        httpSecurity.cors();
        httpSecurity
                .csrf()
                .disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                // Requests that anyone can access
                .antMatchers(HttpMethod.GET, "/api/user").permitAll()
                .antMatchers(HttpMethod.POST, "/api/user").permitAll()
                .antMatchers(HttpMethod.GET, "/api/user/username/{username}").permitAll()
                .antMatchers(HttpMethod.GET, "/api/user/{id}").permitAll()
                .antMatchers(HttpMethod.POST, "/api/user/login").permitAll()
                .antMatchers(HttpMethod.PUT, "/api/user/resetPassword").permitAll()
                .antMatchers(HttpMethod.GET, "/api/post").permitAll()
                .antMatchers(HttpMethod.POST, "/api/post").permitAll()
                .antMatchers(HttpMethod.GET, "/api/post/{id}").permitAll()
                .antMatchers(HttpMethod.GET, "/api/post/sorted-by-popular").permitAll()
                .antMatchers(HttpMethod.GET, "/api/post/sorted-by-unpopular").permitAll()
                .antMatchers(HttpMethod.GET, "/api/post/sorted-by-date-asc").permitAll()
                .antMatchers(HttpMethod.GET, "/api/post/sorted-by-date-desc").permitAll()
                .antMatchers(HttpMethod.GET, "/api/community").permitAll()
                .antMatchers(HttpMethod.GET, "/api/community/{id}").permitAll()
                .antMatchers(HttpMethod.GET, "/api/reaction").permitAll()
                .antMatchers(HttpMethod.GET, "/api/reaction/comment-karma/{commentId}").permitAll()
                .antMatchers(HttpMethod.GET, "/api/reaction/post-karma/{postId}").permitAll()
                .antMatchers(HttpMethod.GET, "/api/reaction/user-karma/{userId}").permitAll()
                .antMatchers(HttpMethod.GET, "/api/comment/sorted-by-popular").permitAll()
                .antMatchers(HttpMethod.GET, "/api/comment/sorted-by-unpopular").permitAll()
                .antMatchers(HttpMethod.GET, "/api/comment/sorted-by-date-asc").permitAll()
                .antMatchers(HttpMethod.GET, "/api/comment/sorted-by-date-desc").permitAll()
                .antMatchers(HttpMethod.GET, "/api/flair").permitAll()
                .antMatchers(HttpMethod.GET, "/api/flair/{id}").permitAll()
                .antMatchers(HttpMethod.GET, "/api/report").permitAll()
                .antMatchers(HttpMethod.GET, "/api/user/rese tPassword").permitAll()
                .anyRequest().authenticated();

        httpSecurity.addFilterBefore(authenticationTokenFilterBean(), UsernamePasswordAuthenticationFilter.class);

    }
}





