package iessfranciscodelosrios.proyecto3.GestorAutocares;

import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;


import iessfranciscodelosrios.proyecto3.GestorAutocares.model.DAO.TicketDAO;
import iessfranciscodelosrios.proyecto3.GestorAutocares.model.domain.Consumer;
import iessfranciscodelosrios.proyecto3.GestorAutocares.model.domain.Ticket;
import iessfranciscodelosrios.proyecto3.GestorAutocares.singleton.UserSession;
import iessfranciscodelosrios.proyecto3.GestorAutocares.utils.Validates;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;


public class ConsumerTicketsController implements Initializable{
	
	@FXML
	private TableView<Ticket> tableViewTickets;
	@FXML
	private TableColumn<Ticket, String> origin;
	@FXML
	private TableColumn<Ticket, String> destination;
	@FXML
	private TableColumn<Ticket, Date> date;
	@FXML
	private TableColumn<Ticket, String> dni;


	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
	    origin.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getJourney().getOrigin()));
	    destination.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getJourney().getDestination()));
	    date.setCellValueFactory(cellData -> {
    	    Date fecha = (Date) cellData.getValue().getDate();
    	    return new SimpleObjectProperty<Date>(fecha);
    	});
	    dni.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getConsumer().getDni()));

	    try {
	        TicketDAO ticketDAO = new TicketDAO();
	        List<Ticket> tickets = ticketDAO.ticketByConsumer(UserSession.getUser());
	        tableViewTickets.getItems().addAll(tickets);
	    } catch (SQLException e) {
	        e.printStackTrace();
	        // Manejar el error de la base de datos
	        // ...
	    }
	}
	/**
	 * Metodo que elimina los tickets comprados por un usuario
	 */
	@FXML
	private void deleteJourney() {
	    if (tableViewTickets.getSelectionModel().isEmpty()) {
	    	 Validates.alertError("ERROR", "ERROR EN MIS VIAJES", "Debes seleccionar un ticket.");
	        // utils.alerta("Error", "", "Debes seleccionar un cliente");
	    } else {
	        Ticket selectedTicket = tableViewTickets.getSelectionModel().getSelectedItem();
	        Consumer selectedConsumer = selectedTicket.getConsumer();

	        TicketDAO ticketDAO = new TicketDAO();
	        try {
	            ticketDAO.deleteTicketByConsumers(selectedTicket);
	        } catch (SQLException e) {
	            // Manejar el error de SQLException según tus necesidades
	        }

	        // Eliminar el ticket de la tabla y actualizar la vista
	        tableViewTickets.getItems().remove(selectedTicket);
	    }
	}


	




	


	
	
}
