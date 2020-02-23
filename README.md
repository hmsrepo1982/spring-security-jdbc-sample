# spring-security-jdbc-sample

https://docs.spring.io/spring-security/site/docs/current/reference/htmlsingle/#user-schema - read this.

# MySql DB Script 

CREATE TABLE `users` (
  `username` varchar(50) NOT NULL,
  `password` varchar(50) NOT NULL,
  `enabled` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`username`)
) 

create table authorities (
    username varchar(50) not null,
    authority varchar(50) not null,
    constraint fk_authorities_users foreign key(username) references users(username)
);

create unique index ix_auth_username on authorities (username,authority);

# Changes for Version and you can test that 

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.jdbcAuthentication()
			.dataSource(datasource) // default queries that needs.
			.usersByUsernameQuery("select username,password,enabled" + " from users" + " where username = ?")
			.authoritiesByUsernameQuery("select username,authority" + " from authorities" + "where username = ?");
		
		/**
		 * what if Tables are different. Say Glogowner. Here is the spot to change/override to load user
		 * user enabled/active or not.
		 * What permission he has got.
		 * Based on that it would apply for # API.
		 * 
		 */
			
	}
