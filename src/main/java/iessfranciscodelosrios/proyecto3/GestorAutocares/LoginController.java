package iessfranciscodelosrios.proyecto3.GestorAutocares;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


import iessfranciscodelosrios.proyecto3.GestorAutocares.model.DAO.ConsumerDAO;
import iessfranciscodelosrios.proyecto3.GestorAutocares.model.domain.Consumer;
import iessfranciscodelosrios.proyecto3.GestorAutocares.singleton.UserSession;
import iessfranciscodelosrios.proyecto3.GestorAutocares.utils.Validates;
import javafx.event.ActionEvent;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;



public class LoginController implements Initializable{
	
	private ConsumerDAO cdao = new ConsumerDAO();
	
	
	@FXML TextField txtUser;  
	@FXML PasswordField txtPassword;
	@FXML Button btnLogin;
	@FXML Button btnRegister;
	@FXML BorderPane borderPane;
	
	
	/**
	 * Metodo que hace que no se puedan poner espacios en los TextField
	 * @param event : evento
	 */
	@FXML
	private void eventKey(KeyEvent event) {
		Object evt = event.getSource();
		if(evt.equals(txtUser)) {
			//hacemos que no se pueda insertar espacios en blanco
			if(event.getCharacter().equals(" ")) {
				event.consume();
			}
		}else if(evt.equals(txtPassword)){
			//hacemos que no se pueda insertar espacios en blanco
			if(event.getCharacter().equals(" ")) {
				event.consume();
			}
		}
	}
	/**
	 * Conntrolador de login: hace que se puefan loguear tanto usuario como administrador
	 * @param event : evento de ActionEvent --> boton.
	 * @throws IOException:Esta clase es la clase general de excepciones producidas por operaciones de E/S fallidas o interrumpidas.

	 */
	@FXML
	private void eventAtion(ActionEvent event) throws IOException {
	    Object evt = event.getSource();
	    if (evt.equals(btnLogin)) {
	        if (!txtUser.getText().isEmpty() && !txtPassword.getText().isEmpty()) {
	            if (txtUser.getText().equals("admin") && txtPassword.getText().equals("admin")) {
	                App.setRoot("SideBarAdmin");
	            } else {
	                String user = txtUser.getText();
	                String password = txtPassword.getText();
	                password = Validates.encryptSHA256(password);
	                boolean state = cdao.login(user, password);
	                if (state) {
	                    Consumer loggedUser = cdao.getUserByUser(user); // Obtener los datos completos del usuario
	                    UserSession.login(loggedUser.getDni(), loggedUser.getName(), loggedUser.getTlf(), loggedUser.getCod_c(), loggedUser.getUser());
	                    App.setRoot("SideBarConsumer");
	                } else {
	                	Validates.alertError("ERROR", "ERROR AL INICIAR SESION", "Datos incorrectos.");
	                }
	            }
	        } else {
	           Validates.alertError("ERROR", "ERROR AL INICIAR SESION", "Algun campo se encuentra vacio.");
	        }
	    } else if (evt.equals(btnRegister)) {
	        App.setRoot("ConsumerRegister");
	    }
	}

	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		
	}
	
   
	 
	
}
