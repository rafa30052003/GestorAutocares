package iessfranciscodelosrios.proyecto3.GestorAutocares.model.DAO;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import iessfranciscodelosrios.proyecto3.GestorAutocares.model.connection.Connect;
import iessfranciscodelosrios.proyecto3.GestorAutocares.model.domain.Consumer;
import iessfranciscodelosrios.proyecto3.GestorAutocares.model.domain.Journey;
import iessfranciscodelosrios.proyecto3.GestorAutocares.model.domain.Ticket;

public class TicketDAO  {
	/*
	 * QUERY
	 */
	private final static String FINDALL ="SELECT * from compra";
	private final static String GETTICKETBYCONSUMER ="SELECT viaje.origen, viaje.destino, compra.fecha, cliente.dni FROM compra JOIN viaje ON compra.cod_v = viaje.cod_v JOIN cliente ON compra.cod_c = cliente.cod_c WHERE cliente.cod_c = ?";
	private final static String DELETEBYVCONSUMER = "DELETE FROM compra WHERE cod_c IN (SELECT cod_c FROM cliente WHERE dni = ?) LIMIT 1";
	private final static String INSERTTICKETBYCONSUMER = "INSERT INTO compra (cod_c, cod_v, fecha) VALUES (?, (SELECT cod_v FROM viaje WHERE origen = ? AND destino = ?), ?)";
	
	/*
	 * CONNECTION
	 */
	private Connection conn;
	public TicketDAO(Connection conn) {
		this.conn = conn;
	}
	public TicketDAO() {
		this.conn=Connect.getConnect();
	}
	
	
	
	/**
	 * dao que muestra todos los tickes 
	 * @return
	 */
	public List <Ticket> findAll() {
		List <Ticket> result = new ArrayList<>();
		try(PreparedStatement pst=this.conn.prepareStatement(FINDALL)){
			try(ResultSet res = pst.executeQuery()){
				while(res.next()) {
					Ticket t = new Ticket(); 
					t.setDate(res.getDate("fecha"));
					ConsumerDAO cdao = new ConsumerDAO(this.conn);
					Consumer c = cdao.findById(res.getInt("cod_c"));
					t.setConsumer(c);
					JourneyDAO jdao = new JourneyDAO(this.conn);
					Journey j = jdao.findById(res.getInt("cod_v"));
					
					t.setJourney(j);
					result.add(t);
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
	
	/**
	 * dao que muestra los tickets de un cliente
	 * @param entity : el cliente
	 * @return la lista de tickets del propio cliente
	 * @throws SQLException
	 */
	public List<Ticket> ticketByConsumer(Consumer  entity) throws SQLException {
		  List<Ticket> result = new ArrayList<>();
		 
		  try (PreparedStatement pstmt = conn.prepareStatement(GETTICKETBYCONSUMER)) {
		    pstmt.setInt(1, entity.getCod_c());
		    try (ResultSet rs = pstmt.executeQuery()) {
		      while (rs.next()) {
		    	  Journey journey = new Journey();
	                journey.setOrigin(rs.getString("origen"));
	                journey.setDestination(rs.getString("destino"));
	                
	                Consumer consumer = new Consumer();
	                consumer.setDni(rs.getString("dni"));
	                
	                Ticket ticket = new Ticket();
	                ticket.setDate(rs.getDate("fecha"));
	                ticket.setJourney(journey);
	                ticket.setConsumer(consumer);
	                
	                result.add(ticket);
		      }
		    }
		  }
		  return result;
		}
	
	
	
	/**
	 * Dao que elimina los tickets de un cliente
	 * @param entity los tickets
	 * @throws SQLException
	 */
	public void deleteTicketByConsumers(Ticket entity) throws SQLException {
		try(PreparedStatement pst=this.conn.prepareStatement(DELETEBYVCONSUMER)){
			pst.setString(1, entity.getConsumer().getDni());
			pst.executeUpdate();
		}
	}
	
	
	/**
	 * dao que inserta un viaje para un cliente
	 * @param entity
	 * @return
	 * @throws SQLException
	 */
	public Ticket insertByConsumer(Ticket entity) throws SQLException {
			Ticket result = new Ticket();
			if(entity!=null) {
				
				 try (PreparedStatement pstmt = conn.prepareStatement(INSERTTICKETBYCONSUMER)) {
				        pstmt.setInt(1, entity.getConsumer().getCod_c());
				        pstmt.setString(2, entity.getJourney().getOrigin());
				        pstmt.setString(3, entity.getJourney().getDestination());
				        pstmt.setDate(4, (Date) entity.getDate());
				        pstmt.executeUpdate();
				    }
				result=entity;
			}
			
	   return result;
	  
	}
	
	/*
	 * CONSULTAS PARA LOS COMBOBOX
	 */
	
	
	
	public List<String> obtenerOrigenesDesdeBD() throws SQLException {
	    List<String> origenes = new ArrayList<>();
	    String query = "SELECT DISTINCT origen FROM viaje";

	    try (PreparedStatement pstmt = conn.prepareStatement(query);
	         ResultSet rs = pstmt.executeQuery()) {
	        while (rs.next()) {
	            String origen = rs.getString("origen");
	            origenes.add(origen);
	        }
	    }

	    return origenes;
	}

	public List<String> obtenerDestinosDesdeBD() throws SQLException {
	    List<String> destinos = new ArrayList<>();
	    String query = "SELECT DISTINCT destino FROM viaje";

	    try (PreparedStatement pstmt = conn.prepareStatement(query);
	         ResultSet rs = pstmt.executeQuery()) {
	        while (rs.next()) {
	            String destino = rs.getString("destino");
	            destinos.add(destino);
	        }
	    }

	    return destinos;
	}


	public boolean existeViajeEnBD(String origen, String destino) throws SQLException {
	    String query = "SELECT COUNT(*) FROM viaje WHERE origen = ? AND destino = ?";

	    try (PreparedStatement pstmt = conn.prepareStatement(query)) {
	        pstmt.setString(1, origen);
	        pstmt.setString(2, destino);

	        try (ResultSet rs = pstmt.executeQuery()) {
	            if (rs.next()) {
	                int count = rs.getInt(1);
	                return count > 0;
	            }
	        }
	    }

	    return false;
	}
	
	
	
	public boolean existeTicketEnBD(String origen, String destino, Date fecha) throws SQLException {
	    String sql = "SELECT COUNT(*) FROM compra c INNER JOIN viaje v ON c.cod_v = v.cod_v WHERE v.origen = ? AND v.destino = ? AND c.fecha = ?";
	    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
	        stmt.setString(1, origen);
	        stmt.setString(2, destino);
	        stmt.setDate(3, fecha);

	        try (ResultSet rs = stmt.executeQuery()) {
	            if (rs.next()) {
	                int count = rs.getInt(1);
	                return count > 0;
	            }
	        }
	    }

	    return false;
	}



}
