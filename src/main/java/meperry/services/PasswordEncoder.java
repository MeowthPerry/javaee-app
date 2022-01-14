package meperry.services;

import org.springframework.security.crypto.bcrypt.BCrypt;

public class PasswordEncoder {

	public String hashPassword(String password) {
		return BCrypt.hashpw(password, BCrypt.gensalt());
	}

	public boolean checkPassword(String password, String hashed) {
		return BCrypt.checkpw(password, hashed);
	}
}
