package iessfranciscodelosrios.proyecto3.GestorAutocares;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import iessfranciscodelosrios.proyecto3.GestorAutocares.model.DAO.ConsumerDAO;
import iessfranciscodelosrios.proyecto3.GestorAutocares.model.domain.Consumer;
import iessfranciscodelosrios.proyecto3.GestorAutocares.singleton.UserSession;
import iessfranciscodelosrios.proyecto3.GestorAutocares.utils.Validates;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

public class SideBarConsumerController implements Initializable{
	
	 @FXML private BorderPane bp;
	 @FXML private AnchorPane ap;
	 @FXML Button btnLogin;
	
	 
	 
	 @FXML
		public void perfil(MouseEvent event) {
			bp.setCenter(ap);
			
		}	
	@FXML
	public void login(ActionEvent event) throws IOException {
		Object evt = event.getSource();
		if(evt.equals(btnLogin)) {
			App.setRoot("login");
		}
	}
	
	@FXML
	public void  buyTickets(MouseEvent event) {
		loadPage("BuyTickets");
	}
	
	@FXML
	public void consumerTickets (MouseEvent event){
		loadPage("ConsumerTickets");
	}
	
	
	

	/**
	 * Controlador del sidebar
	 * @param page
	 */
	public void loadPage(String page) {
	    try {
	    	
		        Parent root = FXMLLoader.load(getClass().getResource(page + ".fxml"));
	    	

	        bp.setCenter(root);
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}

	/*
	 * CARGA DE DATOS DEL CONSUMER QUE SE HA LOGUEADO
	 */
	

	@FXML private TextField txtName;
	@FXML private TextField txtDni;
	@FXML private TextField txtTlf;
	@FXML private TextField txtUser;
	@FXML private PasswordField txtPassword; // Nuevo campo para la nueva contraseña

	@Override
	public void initialize(URL location, ResourceBundle resources) {
	     if (UserSession.isLogged()) {
	        Consumer user = UserSession.getUser();
	        
	        txtDni.setText(user.getDni());
	        txtDni.setEditable(false);
	        txtName.setText(user.getName());
	        txtTlf.setText(user.getTlf());
	        txtUser.setText(user.getUser());
	        // No establecer la contraseña en el TextField
	    }
	}
	/**
	 * metodo que me permite editar el perfil del usuario
	 * @param event
	 */
	
	@FXML
	private void handleTextFieldKeyPress(KeyEvent event) {
	    if (event.getCode() == KeyCode.ENTER) {
	        if (UserSession.isLogged()) {
	            Consumer user = UserSession.getUser();

	            // Obtener los nuevos valores de los campos de texto
	            String newName = txtName.getText();
	            String newTlf = txtTlf.getText();
	            String newUser = txtUser.getText();
	            String newPassword = txtPassword.getText();

	            // Guardar los valores anteriores
	            String previousName = user.getName();
	            String previousTlf = user.getTlf();
	            String previousUser = user.getUser();

	            if (!newName.isEmpty() && !newTlf.isEmpty() && !newUser.isEmpty()) {
	                if (!newPassword.isEmpty()) {
	                    newPassword = Validates.encryptSHA256(newPassword);
	                    user.setPassword(newPassword);
	                }

	                // Actualizar los valores del objeto UserSession
	                user.setName(newName);
	                user.setTlf(newTlf);
	                user.setUser(newUser);

	                try {
	                    // Crear una instancia de ConsumerDAO
	                    ConsumerDAO consumerDAO = new ConsumerDAO();

	                    // Guardar los cambios en la base de datos
	                    consumerDAO.save(user);
	                    Validates.alertCorrect("INFORMACION", "PERFIL DE USUARIO", "Has actualizado el usuario");

	                    // Actualizar los campos de texto con los nuevos valores
	                    txtName.setText(user.getName());
	                    txtTlf.setText(user.getTlf());
	                    txtUser.setText(user.getUser());
	                    txtPassword.clear();
	                } catch (SQLException e) {
	                    e.printStackTrace();
	                    Validates.alertError("ERROR", "EDITAR PERFIL", "El usuario o la contraseña introducida ya existen.");

	                    // Restablecer los campos con los valores anteriores
	                    txtName.setText(previousName);
	                    txtTlf.setText(previousTlf);
	                    txtUser.setText(previousUser);
	                    txtPassword.setText(newPassword);
	                }
	            } else {
	                Validates.alertError("ERROR", "EDITAR PERFIL", "No se permiten campos vacíos.");

	                // Restablecer los campos con los valores anteriores
	                txtName.setText(previousName);
	                txtTlf.setText(previousTlf);
	                txtUser.setText(previousUser);
	                txtPassword.setText(newPassword);
	            }
	        }
	    }
	}









}
