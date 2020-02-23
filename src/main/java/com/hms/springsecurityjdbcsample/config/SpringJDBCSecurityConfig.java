package com.hms.springsecurityjdbcsample.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@PropertySource("classpath:application.properties")
@EnableWebSecurity
public class SpringJDBCSecurityConfig extends WebSecurityConfigurerAdapter {
	
	
	@Autowired
	Environment environment;
	@Autowired
	DataSource datasource;

	private final String URL = "url";
	private final String USER = "dbuser";
	private final String DRIVER = "driver";
	private final String PASSWORD = "dbpassword";

	@Bean
	DataSource dataSource() {
		DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource();
		driverManagerDataSource.setUrl(environment.getProperty(URL));
		driverManagerDataSource.setUsername(environment.getProperty(USER));
		driverManagerDataSource.setPassword(environment.getProperty(PASSWORD));
		driverManagerDataSource.setDriverClassName(environment.getProperty(DRIVER));
		return driverManagerDataSource;
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.jdbcAuthentication()
			.dataSource(datasource);
			
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		/**
		 * method Chaining pattern
		 * http sec with AuthorizeRequests.
		 * Matches any Url and underneath that with **
		 * With any Roles.
		 * Allow.
		 * 
		 */
		http.authorizeRequests()
			.antMatchers("/admin").hasRole("ADMIN")  // hasAnyRole opr has multi Role passing capability.
			.antMatchers("/user").hasAnyRole("USER","ADMIN")
			.antMatchers("/user").permitAll()
			.and().formLogin();
	}
	
	/**
	 * Spring Sec Need Password Encoding
	 * 
	 * Using Dummy Encoder to adhere to Spring Security framework
	 */
	@Bean
	public PasswordEncoder getPasswordEncoder() {
		return NoOpPasswordEncoder.getInstance();
	}
	
	

}
