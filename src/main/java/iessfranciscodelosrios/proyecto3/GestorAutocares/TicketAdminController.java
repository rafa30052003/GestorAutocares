package iessfranciscodelosrios.proyecto3.GestorAutocares;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.Predicate;

import iessfranciscodelosrios.proyecto3.GestorAutocares.model.DAO.TicketDAO;
import iessfranciscodelosrios.proyecto3.GestorAutocares.model.domain.Consumer;
import iessfranciscodelosrios.proyecto3.GestorAutocares.model.domain.Journey;
import iessfranciscodelosrios.proyecto3.GestorAutocares.model.domain.Ticket;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;





public class TicketAdminController implements Initializable{
	TicketDAO tdao = new TicketDAO();
	private List<Ticket> mytickets =(List<Ticket>) tdao.findAll();
	private final ObservableList<Ticket> listaActualizable = FXCollections.observableArrayList(mytickets);
	
	
	@FXML private TableView<Ticket> myTable;
	@FXML private TableColumn<Ticket, Consumer> consumer;
	@FXML private TableColumn<Ticket, Journey> journey;
	@FXML private TableColumn<Ticket, Date> date;

	@FXML private TextField buscar;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		try {
			allTicket();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//METODO BUSCAR	
		FilteredList<Ticket> filteredData = new FilteredList<>(listaActualizable, e -> true);
		buscar.setOnKeyReleased(e -> {
			buscar.textProperty().addListener((observableValue, oldValue, newValue) -> {
				filteredData.setPredicate((Predicate<? super Ticket>) ticket-> {
					if(newValue == null || newValue.isEmpty()) {
						return true;
					}
					if (ticket.getConsumer().getUser().contains(newValue)) {
						return true;
					}
					if (String.valueOf(ticket.getJourney().getCod_v()).contains(newValue)) {
						return true;
					}
					
					
					return false;
				});
			});
			SortedList<Ticket> sortedData = new SortedList<>(filteredData);
			sortedData.comparatorProperty().bind(myTable.comparatorProperty());
			myTable.setItems(sortedData);	
			
		});
		
	}
	
   /**
    * Metodo para mostrar en la tabla todos los campos de ticket
    * @throws SQLException
    */
	 public void allTicket() throws SQLException {
		 TicketDAO tdao = new TicketDAO();
		 List<Ticket> tickets = tdao.findAll();
		 if(myTable.getItems().isEmpty()) {
			 consumer.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper(cellData.getValue().getConsumer().getUser()));
			 journey.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper(cellData.getValue().getJourney().getCod_v()));
			 date.setCellValueFactory(cellData -> {
		    	    Date fecha = (Date) cellData.getValue().getDate();
		    	    return new SimpleObjectProperty<Date>(fecha);
		    	});
			 
			 myTable.getItems().addAll(tickets);
		 }
	 }
	
}
