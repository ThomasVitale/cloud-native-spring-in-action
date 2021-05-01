package com.polarbookshop.edgeservice.security;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class User {
	String username;
	String firstName;
	String lastName;
	List<String> roles;
}
