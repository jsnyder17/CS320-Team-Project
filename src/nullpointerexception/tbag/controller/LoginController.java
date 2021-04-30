package nullpointerexception.tbag.controller;

import nullpointerexception.tbag.model.Library;

public class LoginController {
	private Library model = null;
	
	public LoginController(Library model) {
		this.model = model;
	}
	
	public boolean checkUserName(String name) {
		return model.validateUserName(name);
	}
	
	public boolean validateCredentials(String name, String pw) {
		return model.validatePW(name, pw);
	}
}
