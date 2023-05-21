package iessfranciscodelosrios.proyecto3.GestorAutocares;



import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.Predicate;

import iessfranciscodelosrios.proyecto3.GestorAutocares.model.DAO.ConsumerDAO;
import iessfranciscodelosrios.proyecto3.GestorAutocares.model.domain.Consumer;


import javafx.beans.property.ReadOnlyObjectWrapper;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;


import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class ConsumerAdminController implements Initializable{
	ConsumerDAO cdao = new ConsumerDAO();
	private List<Consumer> myconsumers =(List<Consumer>) cdao.findAll();
	private final ObservableList<Consumer> listaActualizable = FXCollections.observableArrayList(myconsumers);

	
	
	
	@FXML private TableView<Consumer> myTable;
	@FXML private TableColumn<Consumer, Integer> cod_c;
	@FXML private TableColumn<Consumer, String> name;
	@FXML private TableColumn<Consumer, String> dni;
	@FXML private TableColumn<Consumer, String> tlf;
	@FXML private TableColumn<Consumer, String> user;
	
	@FXML private TextField buscar;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		 try {
		        getAll();
		    } catch (SQLException e) {
		        
		        e.printStackTrace();
		    }
		//METODO BUSCAR	
			FilteredList<Consumer> filteredData = new FilteredList<>(listaActualizable, e -> true);
			buscar.setOnKeyReleased(e -> {
				buscar.textProperty().addListener((observableValue, oldValue, newValue) -> {
					filteredData.setPredicate((Predicate<? super Consumer>) consumer-> {
						if(newValue == null || newValue.isEmpty()) {
							return true;
						}
						if (consumer.getDni().contains(newValue)) {
							return true;
						}
						if (consumer.getName().contains(newValue)) {
							return true;
						}
						if (consumer.getTlf().contains(newValue)) {
							return true;
						}
						if (consumer.getUser().contains(newValue)) {
							return true;
						}
						if (String.valueOf(consumer.getCod_c()).contains(newValue)) {
							return true;
						}
						return false;
					});
				});
				SortedList<Consumer> sortedData = new SortedList<>(filteredData);
				sortedData.comparatorProperty().bind(myTable.comparatorProperty());
				myTable.setItems(sortedData);	
				
			});
		
	}
	
	
	/**
	 * Controlador que muestra los clientes en la tableView
	 * @throws SQLException 
	 */
	@FXML
	private void getAll() throws SQLException {
		ConsumerDAO cdao = new ConsumerDAO();

		List<Consumer> consumers = cdao.findAll();
		if(myTable.getItems().isEmpty()) {
			cod_c.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper(cellData.getValue().getCod_c()));
			name.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
			dni.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDni()));
			tlf.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTlf()));
			user.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getUser()));
			myTable.getItems().addAll(consumers);
			
		}
	}
	
}
