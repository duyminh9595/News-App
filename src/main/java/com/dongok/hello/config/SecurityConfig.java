package com.dongok.hello.config;

import com.dongok.hello.dao.AccountCustomerRepository;
import com.dongok.hello.dao.AuthorRepository;
import com.dongok.hello.jwt.JwtValidator;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.access.channel.ChannelProcessingFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private Environment environment;
    private AuthorRepository authorRepository;
    private AccountCustomerRepository accountCustomerRepository;
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.addFilterBefore(new JwtValidator(environment,authorRepository,accountCustomerRepository),UsernamePasswordAuthenticationFilter.class);

        http.addFilterBefore(new CorsFilter(), ChannelProcessingFilter.class);

        http.cors().disable()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers("/author/app/addnews").hasRole("AUTHOR")
                .antMatchers("/author/app/addvideo").hasRole("AUTHOR")
                .antMatchers("/user/app/getinfo").hasRole("USER")
                .antMatchers("/user/app/updateavatar").hasRole("USER")
                .antMatchers("/user/app/addlove").hasRole("USER")
                .anyRequest().permitAll();
    }

    @Bean
    @Override
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }


}
