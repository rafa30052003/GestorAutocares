package iessfranciscodelosrios.proyecto3.GestorAutocares;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import iessfranciscodelosrios.proyecto3.GestorAutocares.model.DAO.ConsumerDAO;
import iessfranciscodelosrios.proyecto3.GestorAutocares.model.domain.Consumer;
import iessfranciscodelosrios.proyecto3.GestorAutocares.utils.Validates;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class ConsumerRegistrerController implements Initializable{
	@FXML private TextField txtName;
	@FXML private TextField txtDni;
	@FXML private TextField txtTlf;
	@FXML private TextField txtUser;
	@FXML private PasswordField txtPassword;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		
	}
	
	/**
	 * Controlador que añade los clientes a la base de datos
	 */
	@FXML
	public void addCosumer() {
		String name =txtName.getText();
		String dni =txtDni.getText();
		Validates.validarDNI(dni);
		String tlf =txtTlf.getText();
		Validates.validarTelefono(tlf);
		String User = txtUser.getText();
		String password = txtPassword.getText();
		password= Validates.encryptSHA256(password);
		Consumer c = new Consumer(dni, name, tlf, User, password);
		ConsumerDAO cdao = new ConsumerDAO();

		try {
			 cdao.save(c);
			 App.setRoot("login");
		} catch (SQLException e) {
			Validates.alertError("ERROR", "DUPLICIDAD DE DATOS", "El nombre de usuario o contraseña ya están tomados");
			e.printStackTrace();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
	}
	

}
