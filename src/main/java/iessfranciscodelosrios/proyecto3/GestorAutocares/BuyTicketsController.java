package iessfranciscodelosrios.proyecto3.GestorAutocares;

import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;


import java.util.List;
import java.util.ResourceBundle;

import iessfranciscodelosrios.proyecto3.GestorAutocares.model.DAO.TicketDAO;
import iessfranciscodelosrios.proyecto3.GestorAutocares.model.domain.Consumer;
import iessfranciscodelosrios.proyecto3.GestorAutocares.model.domain.Journey;
import iessfranciscodelosrios.proyecto3.GestorAutocares.model.domain.Ticket;
import iessfranciscodelosrios.proyecto3.GestorAutocares.singleton.UserSession;
import iessfranciscodelosrios.proyecto3.GestorAutocares.utils.Validates;
import javafx.collections.FXCollections;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;

public class BuyTicketsController implements Initializable {
    @FXML
    private ComboBox<String> comboBoxOrigen;
    @FXML
    private ComboBox<String> comboBoxDestino;
    @FXML
    private DatePicker datePickerFecha;
    @FXML
    private Button btnComprar;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Obtener origenes y destinos desde la base de datos
        List<String> origenes;
        List<String> destinos;
        try {
            TicketDAO ticketDAO = new TicketDAO();
            origenes = ticketDAO.obtenerOrigenesDesdeBD();
            destinos = ticketDAO.obtenerDestinosDesdeBD();
        } catch (SQLException e) {
            // Manejo de errores
            return;
        }

        // Configurar los ComboBox con los origenes y destinos obtenidos
        comboBoxOrigen.setItems(FXCollections.observableArrayList(origenes));
        comboBoxDestino.setItems(FXCollections.observableArrayList(destinos));

        // Deshabilitar el botón de compra
        btnComprar.setDisable(true);

        // Agregar el escucha al ComboBox de origen
        comboBoxOrigen.valueProperty().addListener((observable, oldValue, newValue) -> {
            actualizarEstadoBotonComprar();
        });

        // Agregar el escucha al ComboBox de destino
        comboBoxDestino.valueProperty().addListener((observable, oldValue, newValue) -> {
            actualizarEstadoBotonComprar();
        });
    }

    // Método para habilitar o deshabilitar el botón de compra según si se ha seleccionado un origen y un destino
    private void actualizarEstadoBotonComprar() {
        String origen = comboBoxOrigen.getValue();
        String destino = comboBoxDestino.getValue();
        btnComprar.setDisable(origen == null || destino == null);
    }
    /**
     * Metodo para comprar tickets
     */
    @FXML
    private void comprarTicket() {
        String origen = comboBoxOrigen.getValue();
        String destino = comboBoxDestino.getValue();
        Date fechaSeleccionada = java.sql.Date.valueOf(datePickerFecha.getValue());

        // Verificar si se ha seleccionado el origen y el destino
        if (origen != null && destino != null) {
            // Obtener el código del usuario que ha iniciado sesión
            Consumer codigoUsuario = UserSession.getUser();

            // Crear una instancia de TicketDAO
            TicketDAO ticketDAO = new TicketDAO();

            // Verificar si la combinación de origen y destino existe en la base de datos
            boolean viajeExiste;
            try {
                viajeExiste = ticketDAO.existeViajeEnBD(origen, destino);

                if (!viajeExiste) {
                	Validates.alertError("ERROR", "VIAJE NO ENCONTRADO", "La combinación de origen y destino seleccionada no coincide con ningún viaje en la base de datos.");
                    // Mostrar un mensaje de error al usuario
                   
                } else {
                    // Verificar si el ticket ya existe en la base de datos para el mismo viaje y fecha
                    boolean ticketExiste = ticketDAO.existeTicketEnBD(origen, destino, fechaSeleccionada);

                    if (ticketExiste) {
                        // Mostrar un mensaje de error al usuario
                    	Validates.alertError("ERROR", "TICKET DUPLICADO", "Ya has comprado un ticket para este viaje y fecha. No puedes comprar otro ticket duplicado.");

                       
                    } else {
                        // Crear una instancia de

	                    // Crear una instancia de Journey y asignar origen y destino
	                    Journey journey = new Journey();
	                    journey.setOrigin(origen);
	                    journey.setDestination(destino);

	                    // Crear una instancia de Ticket y asignar el Journey, fecha, etc.
	                    Ticket ticket = new Ticket();
	                    ticket.setJourney(journey);
	                    ticket.setDate(fechaSeleccionada);
	                    // Asignar otros datos necesarios al ticket
	                    ticket.setConsumer(codigoUsuario); // Asignar el código de usuario

	                    // Realizar la compra del ticket utilizando el método insertByConsumer(Ticket ticket) de TicketDAO
	                    Ticket ticketComprado = ticketDAO.insertByConsumer(ticket);

	                    if (ticketComprado != null) {
	                        // Mostrar un mensaje de éxito al usuario
	                    	Validates.alertCorrect("INFO", "TICKET COMPRADO", "Se ha realizado la compra del ticket exitosamente.");
	                        

	                        // Actualizar la interfaz de usuario o realizar otras acciones necesarias
	                        // ...
	                    } else {
	                        // Mostrar un mensaje de error al usuario
	                    	Validates.alertError("ERROR", "ERROR EN LA COMPRA", "No se pudo realizar la compra del ticket. Por favor, intenta nuevamente.");

	                    }
	                }
	            }
	        } catch (SQLException e) {
	            // Manejar la excepción de la base de datos
	            e.printStackTrace();
	        }
	    }
	}




	


}
