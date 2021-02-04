package org.springframework.samples.petclinic.configuration;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.samples.petclinic.service.UserDetailsServiceImpl;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * @author japarejo
 */
@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	DataSource dataSource;
	
	@Autowired
	UserDetailsServiceImpl userDetailsService;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
		.csrf().disable()  
		.authorizeRequests()
		.antMatchers("/resources/**","/webjars/**","/h2-console/**").permitAll()
		.antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
		.antMatchers("/basicauth").authenticated()
		.antMatchers("/requests/pending", "/requests/decline/**", "/requests/accept/**").hasAuthority("profesor")
		.antMatchers("/premiados/{fechaWall}", "/premiados/ultimaSemana").hasAnyAuthority("profesor", "alumno")
		.antMatchers("/premiados/anadirPremiado/{fechaWall}", "/premiados/editarPremiado","/premiados/borrarPremiado/{id}").hasAuthority("profesor")
		.antMatchers("/pagos/notPaidByStudent/{nickUsuario}").hasAnyAuthority("profesor", "alumno")
		.antMatchers("/pagos/paidByStudent/{nickUsuario}").hasAuthority("alumno")
		.antMatchers("/pagos", "/pagos/{concepto}", "/pagos/notPaid/{concepto}", "/pagos/studentsNotPaid", "/pagos/new").hasAuthority("profesor")
		.antMatchers("/alumnos/**").hasAuthority("profesor")
		.antMatchers("/alumnos/editStudent", "/alumnos/getStudentInfo/{nickUsuario}").hasAnyAuthority("profesor", "alumno")
		.antMatchers("/alumnos/{nickTutor}/allMyStudents").hasAuthority("tutor")
		.anyRequest().denyAll()
		.and()
		.httpBasic();
        http.headers().frameOptions().sameOrigin();
	}

	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
//		auth.jdbcAuthentication()
//	      .dataSource(dataSource)
//	      .usersByUsernameQuery(
//	       "select username,password,enabled "
//	        + "from users "
//	        + "where username = ?")
//	      .authoritiesByUsernameQuery(
//	       "select username, authority "
//	        + "from authorities "
//	        + "where username = ?")	      	      
//	      .passwordEncoder(passwordEncoder());
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {	    
		PasswordEncoder encoder =  NoOpPasswordEncoder.getInstance();
	    return encoder;
	}
	
}


