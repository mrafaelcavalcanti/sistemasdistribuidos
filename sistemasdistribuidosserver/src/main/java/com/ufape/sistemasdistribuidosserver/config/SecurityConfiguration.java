package com.ufape.sistemasdistribuidosserver.config;

import com.ufape.sistemasdistribuidosserver.service.MyUserDetailService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    @Autowired
    MyUserDetailService userDetailsService;

    @Override
    protected void configure (AuthenticationManagerBuilder auth) throws Exception {
        //auth.inMemoryAuthentication().withUser("user").password(getPasswordEncoder().encode("password"));
        //System.out.println("passou 2");
        System.out.println(auth.toString());
        auth.userDetailsService(userDetailsService);

    }

    @Override
    protected void configure (HttpSecurity http) throws Exception {
        //System.out.println("passou");
        http.csrf().disable();        
		
		
		http.authorizeRequests()
        .antMatchers("/usuarios/novo");

        http.authorizeRequests().antMatchers("/api/usuario/**").permitAll();
        http.authorizeRequests().antMatchers("/api/arquivo/**").permitAll();
        http.authorizeRequests().antMatchers("/api/requisicoes/**").permitAll();
        /*http.authorizeRequests().antMatchers("/api/produtos/getByCategoria/**").hasAnyAuthority("LOGADO");
        http.authorizeRequests().antMatchers("/api/produtos/getByName/**").hasAnyAuthority("LOGADO");
        http.authorizeRequests().antMatchers("/api/produtos/getById/**").hasAnyAuthority("LOGADO");
        http.authorizeRequests().antMatchers("/api/produtos/get/all/**").hasAnyAuthority("LOGADO");
        http.authorizeRequests().antMatchers("/api/produtos/getByUserProducts/**").hasAnyAuthority("LOGADO");
        http.authorizeRequests().antMatchers("/api/carrinho/**").hasAnyAuthority("LOGADO");
        */
        
        http.authorizeRequests().antMatchers("/home").hasAnyAuthority("LOGADO");
        http.authorizeRequests().antMatchers("/usuario").hasAnyAuthority("LOGADO");

        http.formLogin().loginPage("/login").defaultSuccessUrl("/home");
        http.logout().logoutSuccessUrl("/login?logout").permitAll();
    }

    @Bean
	public PasswordEncoder getPasswordEncoder() {
		
		return NoOpPasswordEncoder.getInstance();
	}
    
}
