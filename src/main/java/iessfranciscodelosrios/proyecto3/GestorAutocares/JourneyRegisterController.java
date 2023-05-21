package iessfranciscodelosrios.proyecto3.GestorAutocares;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import iessfranciscodelosrios.proyecto3.GestorAutocares.model.DAO.JourneyDAO;
import iessfranciscodelosrios.proyecto3.GestorAutocares.model.domain.Journey;
import iessfranciscodelosrios.proyecto3.GestorAutocares.utils.Validates;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

public class JourneyRegisterController implements Initializable{
	
	@FXML private TextField txtOrigin;
	@FXML private TextField txtDestination;
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * Metodo para registrar un viaje
	 */
	public void addJourney() {
		if(!txtOrigin.getText().isEmpty() && !txtDestination.getText().isEmpty()) {
			String origin = txtOrigin.getText();
			String destination = txtDestination.getText();
			Journey j = new Journey(origin, destination);
			JourneyDAO jdao = new JourneyDAO();
			try {
				jdao.save(j);
				App.setRoot("SideBarAdmin");
			} catch (SQLException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else {
			Validates.alertError("ERROR", "REGISTRAR VIAJE", "Algun campo se encuenta vacio.");
		}
		
	}

}
