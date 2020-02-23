package com.hms.springsecurityjdbcsample.resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmployeeResource {
	
	@GetMapping("/")
	public String welcomeEmp() { 
		
		return ("<h1>Welcome</h1>");
	}
	
	@GetMapping("/user")
	public String welcomeUser() { 
		
		return ("<h1>Welcome User</h1>");
	}

	
	@GetMapping("/admin")
	public String welcomeAdmin() { 
		
		return ("<h1>Welcome Admin</h1>");
	}

}
