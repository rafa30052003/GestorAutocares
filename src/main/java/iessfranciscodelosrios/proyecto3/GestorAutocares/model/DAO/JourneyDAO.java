package iessfranciscodelosrios.proyecto3.GestorAutocares.model.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import iessfranciscodelosrios.proyecto3.GestorAutocares.interfaces.DAO;
import iessfranciscodelosrios.proyecto3.GestorAutocares.model.connection.Connect;

import iessfranciscodelosrios.proyecto3.GestorAutocares.model.domain.Journey;



public class JourneyDAO implements DAO<Journey,Integer>{
	/*
	 * QUERY
	 */
	private final static String FINDALL ="SELECT * from viaje";
	private final static String FINBYID ="SELECT * from viaje WHERE cod_v=?";
	private final static String INSERT ="INSERT INTO viaje (cod_v,origen,destino) VALUES (null,?,?)";
	private final static String UPDATE ="UPDATE viaje SET origen=?, destino =?  WHERE cod_v=?";
	private final static String DELETE ="DELETE FROM viaje WHERE cod_v=?";
	/*
	 * CONNECTION
	 */
	private Connection conn;
	public JourneyDAO(Connection conn) {
		this.conn = conn;
	}
	public JourneyDAO() {
		this.conn=Connect.getConnect();
	}
	
	
	
	
	
	/**
	 * Dao que muestra todos los viajes
	 */
	@Override
	public List<Journey> findAll()  {
		List<Journey> result = new ArrayList<>();
		try(PreparedStatement pst=this.conn.prepareStatement(FINDALL)){
			try(ResultSet res = pst.executeQuery()){
				while(res.next()) {
					Journey j = new Journey();
					j.setCod_v(res.getInt("cod_v"));
					j.setOrigin(res.getString("origen"));  
					j.setDestination(res.getString("destino"));
					result.add(j);
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	/**
	 * Dao que busca por id el viaje
	 */
	@Override
	public Journey findById(Integer id) throws SQLException {
		Journey result = null;
		try(PreparedStatement pst=this.conn.prepareStatement(FINBYID)){
			pst.setInt(1, id);
			try(ResultSet res = pst.executeQuery()){
				if(res.next()) {
					result = new Journey();
					result.setCod_v(res.getInt("cod_v"));
					result.setOrigin(res.getString("origen"));
					result.setDestination(res.getString("destino"));
				}
			}
		}
		return result;
	}
	/**
	 * Dao que inserta el viaje y si este existe lo edita 
	 */
	@Override
	public Journey save(Journey entity) throws SQLException {
		Journey result = new Journey();
			if(entity!=null) {
				Journey j = findById(entity.getCod_v());
				if(j == null) {

					//INSERT 
					try(PreparedStatement pst=this.conn.prepareStatement(INSERT)){
						pst.setString(1, entity.getOrigin());
						pst.setString(2, entity.getDestination());
						pst.executeUpdate();
						/**Tickets**/
						//MIRAR PROXIMAMENTE
					}
				}else {
					//UPDATE
					
					try(PreparedStatement pst=this.conn.prepareStatement(UPDATE)){
						pst.setString(1, entity.getOrigin());
						pst.setString(2, entity.getDestination());
						pst.setInt(3, entity.getCod_v());
						pst.executeUpdate();
						/**Tickets**/
						//MIRAR PROXIMAMENTE
					}
				}
				result=entity;
			}
		return result;
	}
	/**
	 * Dao que elimina el viaje
	 */
	@Override
	public void delete(Journey entity) throws SQLException {
		try(PreparedStatement pst=this.conn.prepareStatement(DELETE)){
			 pst.setInt(1, entity.getCod_v());
			 pst.executeUpdate();
		}
		
	}
	
	
	
	
}
