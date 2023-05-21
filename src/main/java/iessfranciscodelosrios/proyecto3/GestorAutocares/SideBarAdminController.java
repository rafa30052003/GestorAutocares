package iessfranciscodelosrios.proyecto3.GestorAutocares;

import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.Predicate;

import iessfranciscodelosrios.proyecto3.GestorAutocares.model.DAO.JourneyDAO;
import iessfranciscodelosrios.proyecto3.GestorAutocares.model.domain.Journey;
import iessfranciscodelosrios.proyecto3.GestorAutocares.utils.Validates;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

public class SideBarAdminController implements Initializable{
	
	JourneyDAO jdao = new JourneyDAO();
	private List<Journey> myjourneys =(List<Journey>) jdao.findAll();
	private final ObservableList<Journey> listaActualizable = FXCollections.observableArrayList(myjourneys);
	
	
	@FXML private Button btnInsert;
	
	@FXML private TableView<Journey> mytable;
	@FXML private TableColumn<Journey, Integer> cod_v; 
	@FXML private TableColumn<Journey, String> origin;
	@FXML private TableColumn<Journey, String> destination;
	@FXML private TextField buscar;
	
	
	
	 @FXML private BorderPane bp;
	 @FXML private AnchorPane ap;
	 @FXML Button btnLogin;
	
	 
	 
	 @FXML
		public void journeys(MouseEvent event) {
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
	public void  ticketsAdmin(MouseEvent event) {
		loadPage("TicketsAdmin");
	}
	
	@FXML
	public void consumersAdnmin (MouseEvent event){
		loadPage("ConsumersAdmin");
	}
	
	
	/**
	 * Controlador del SideBar
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
	 * JOURNEYS IN ADMIN
	 */
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		this.allJourney();
		mytable.setItems(listaActualizable);
		//METODO BUSCAR	
		FilteredList<Journey> filteredData = new FilteredList<>(listaActualizable, e -> true);
		buscar.setOnKeyReleased(e -> {
			buscar.textProperty().addListener((observableValue, oldValue, newValue) -> {
				filteredData.setPredicate((Predicate<? super Journey>) journey-> {
					if(newValue == null || newValue.isEmpty()) {
						return true;
					}
					if (journey.getOrigin().contains(newValue)) {
						return true;
					}
					if (journey.getDestination().contains(newValue)) {
						return true;
					}
					if (String.valueOf(journey.getCod_v()).contains(newValue)) {
						return true;
					}
					return false;
				});
			});
			SortedList<Journey> sortedData = new SortedList<>(filteredData);
			sortedData.comparatorProperty().bind(mytable.comparatorProperty());
			mytable.setItems(sortedData);	
			
		});
	}
	
	

	
	/**
	 * Controlador que muestra la tabla en el tableView
	 * Ademas edita la viaje
	 */
		
	private void allJourney() {
		//cod_v
		cod_v.setCellValueFactory(journey ->{
						ObservableValue<Integer> ov = new SimpleIntegerProperty().asObject();
						((ObjectProperty<Integer>) ov).setValue(journey.getValue().getCod_v());
						return ov;
		});
		//origin
		origin.setCellValueFactory(journey ->{
						SimpleStringProperty ssp = new SimpleStringProperty();
						ssp.setValue(journey.getValue().getOrigin());
						return ssp;
		});
		origin.setCellFactory(TextFieldTableCell.forTableColumn());
		origin.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Journey, String>>() {
			@Override
			public void handle(TableColumn.CellEditEvent<Journey, String> t) {
				Journey selected = (Journey) t.getTableView().getItems().get(t.getTablePosition().getRow());
				selected.setOrigin(t.getNewValue());
				try {
					jdao.save(selected);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		//destination
		destination.setCellValueFactory(journey ->{
			SimpleStringProperty ssp = new SimpleStringProperty();
			ssp.setValue(journey.getValue().getDestination());
			return ssp;
		});
		destination.setCellFactory(TextFieldTableCell.forTableColumn());
		destination.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Journey, String>>() {
		@Override
		public void handle(TableColumn.CellEditEvent<Journey, String> t) {
			Journey selected = (Journey) t.getTableView().getItems().get(t.getTablePosition().getRow());
			selected.setDestination(t.getNewValue());
			try {
				jdao.save(selected);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		});
		mytable.setEditable(true);
		mytable.getSelectionModel().cellSelectionEnabledProperty().set(true);
	}
	
	/**
	 * Metodo que elimina de la tabla 
	 */
	@FXML
	 private void deleteJourney() {
		 if(mytable.getSelectionModel().isEmpty()) {
			// utils.alerta("Error", "", "Debes seleccionar un cliente");
			 Validates.alertError("ERROR", "ERROR EN VIAJES", "Debes seleccionar un viaje.");
		 }else {
			 try {
				jdao.delete(mytable.getSelectionModel().getSelectedItem());
			} catch (SQLException e) {
				
				e.printStackTrace();
			}
			 	
				listaActualizable.remove(mytable.getSelectionModel().getSelectedItem());
		 }
	 }
	
	
	
	/**
	 * Metodo que cambia al fxml de nuevo viaje
	 * @param event
	 * @throws IOException
	 */
	 @FXML
	    private void switchToAddJourney(ActionEvent event) throws IOException {
		 
			    Object evt = event.getSource();
			    if (evt.equals(btnInsert)) {
			        App.setRoot("JourneyRegister");
			    }
	    }
}
