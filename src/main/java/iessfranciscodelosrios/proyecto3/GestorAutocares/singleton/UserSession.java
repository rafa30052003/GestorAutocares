package iessfranciscodelosrios.proyecto3.GestorAutocares.singleton;

import iessfranciscodelosrios.proyecto3.GestorAutocares.model.domain.Consumer;

public class UserSession {
	private static Consumer user1 = null;
	
	public static void login(String dni, String name, String tlf, int cod_c,  String user) {
		user1 = new Consumer(dni, name, tlf, cod_c, user);
	}
	
	public static void logout() {
		user1 = null;
	}
	
	public static boolean isLogged() {
		return user1==null?false:true;
	}
	
	public static Consumer getUser() {
		return user1;
	}
}
