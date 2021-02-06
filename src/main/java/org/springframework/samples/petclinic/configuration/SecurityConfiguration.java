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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
		.antMatchers("/premiados/**").hasAuthority("profesor")
		.antMatchers("/pagos/notPaidByStudent/{nickUsuario}").hasAnyAuthority("profesor", "alumno")
		.antMatchers("/pagos/paidByStudent/{nickUsuario}").hasAuthority("alumno")
		.antMatchers("/pagos/**").hasAuthority("profesor")
		.antMatchers("/alumnos/editStudent", "/alumnos/getStudentInfo/{nickUsuario}").hasAnyAuthority("profesor", "alumno")
		.antMatchers("/alumnos/{nickTutor}/allMyStudents").hasAuthority("tutor")
		.antMatchers("/alumnos/**").hasAuthority("profesor") //student vista alumno
		.antMatchers("/materiales/getMaterialByAlumno/{nickAlumno}").hasAnyAuthority("alumno", "profesor")
		.antMatchers("/materiales/**").hasAuthority("profesor")
		.antMatchers("/inscriptions/**").hasAuthority("alumno")
		.antMatchers("/grupos/**").hasAuthority("profesor")
		.antMatchers("/feedback/{nickUser}/{idMaterial}", "/feedback/update").hasAnyAuthority("alumno", "profesor")
		.antMatchers("/feedback/**").hasAuthority("profesor")
		.antMatchers("/events/getByCourse/{nick}", "/events/descriptionAlumno/{id}/{nickUser}").hasAuthority("alumno")
		.antMatchers("/events/**").hasAuthority("profesor")
		.antMatchers("/asignaciones/**").hasAuthority("profesor")
		.antMatchers("/tutores/**").hasAuthority("tutor")
		.anyRequest().permitAll()
		.and()
		.httpBasic();
        http.headers().frameOptions().sameOrigin();
	}
  
	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
	}
	
	@Bean
    public PasswordEncoder passwordEncoder(){
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder;
    }
	
}


