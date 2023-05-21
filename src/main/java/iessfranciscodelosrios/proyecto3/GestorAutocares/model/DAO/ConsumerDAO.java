package iessfranciscodelosrios.proyecto3.GestorAutocares.model.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import iessfranciscodelosrios.proyecto3.GestorAutocares.interfaces.DAO;
import iessfranciscodelosrios.proyecto3.GestorAutocares.model.connection.Connect;
import iessfranciscodelosrios.proyecto3.GestorAutocares.model.domain.Consumer;
import iessfranciscodelosrios.proyecto3.GestorAutocares.singleton.UserSession;


public class ConsumerDAO implements DAO<Consumer,Integer>{
	/*
	 * QUERY
	 */
	private final static String FINDALL ="SELECT * from cliente";
	private final static String FINBYID ="SELECT * from cliente WHERE cod_c=?";
	private final static String INSERT ="INSERT INTO cliente (cod_c,dni,nombre,telefono,usuario,contrasena) VALUES (null,?,?,?,?,?)";
	private final static String UPDATE ="UPDATE cliente SET nombre=?, telefono =?, usuario=?, contrasena=? WHERE dni=?";
	private final static String DELETE ="DELETE FROM cliente WHERE dni=?";
	private final static String FINBYDNI ="SELECT * FROM cliente WHERE dni=?";
	private final static String LOGIN ="SELECT * FROM cliente WHERE usuario= ? and contrasena= ? ";
	/*
	 * CONNECTION
	 */
	private Connection conn;
	
	public ConsumerDAO(Connection conn) {
		this.conn = conn;
	}
	public ConsumerDAO() {
		this.conn=Connect.getConnect();
	}
	
	
	
	
	/**
	 * Dao que muestra todos los clientes
	 */
	@Override
	public List<Consumer> findAll()  {
		List <Consumer> result = new ArrayList<>();
		try(PreparedStatement pst=this.conn.prepareStatement(FINDALL)){
			try(ResultSet res = pst.executeQuery()){
				while(res.next()) {
					Consumer c = new Consumer();
					c.setCod_c(res.getInt("cod_c"));
					c.setDni(res.getString("dni"));
					c.setName(res.getString("nombre"));
					c.setTlf(res.getString("telefono"));
					c.setUser(res.getString("usuario"));
					c.setPassword(res.getString("contrasena"));
					result.add(c);
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	/**
	 * Dao que busca por id
	 */
	@Override
	public Consumer findById(Integer id) throws SQLException {
		Consumer result = null;
		try(PreparedStatement pst=this.conn.prepareStatement(FINBYID)){
			pst.setInt(1, id);
			try(ResultSet res = pst.executeQuery()){
				if(res.next()) {
					result = new Consumer();
					result.setDni(res.getString("dni"));
					result.setName(res.getString("nombre"));
					result.setTlf(res.getString("telefono"));
					result.setUser(res.getString("usuario"));
					result.setPassword(res.getString("contrasena"));
				}
			}
		}
		return result;
		
	}

	/**
	 * Dao que inserta el cliente y si este existe lo edita 
	 */
	@Override
	public Consumer save(Consumer entity) throws SQLException {
	    Consumer result = new Consumer();
	    if (entity != null) {
	        Consumer c = findByDni(entity.getDni());

	        if (c == null) {
	            // INSERT
	            try (PreparedStatement pst = this.conn.prepareStatement(INSERT)) {
	                pst.setString(1, entity.getDni());
	                pst.setString(2, entity.getName());
	                pst.setString(3, entity.getTlf());
	                pst.setString(4, entity.getUser());
	                pst.setString(5, entity.getPassword());
	                pst.executeUpdate();
	            }

	        } else {
	            // UPDATE
	            try (PreparedStatement pst = this.conn.prepareStatement(UPDATE)) {
	                pst.setString(1, entity.getName());
	                pst.setString(2, entity.getTlf());
	                pst.setString(3, entity.getUser());
	                
	                // Solo actualizar la contraseña si se proporcionó una nueva contraseña
	                if (entity.getPassword() != null && !entity.getPassword().isEmpty()) {
	                    pst.setString(4, entity.getPassword());
	                } else {
	                    pst.setString(4, c.getPassword());
	                }

	                pst.setString(5, entity.getDni());
	                pst.executeUpdate();
	            }
	        }
	        result = entity;
	    }
	    return result;
	}

	/**
	 * Dao que ellimina el cliente
	 */
	@Override
	public void delete(Consumer entity) throws SQLException {
		try(PreparedStatement pst=this.conn.prepareStatement(DELETE)){
			pst.setString(1, entity.getDni());
			pst.executeUpdate();
		}
		
	}
	
	
	
	/**
	 * Dao que busca el cliente por el dni
	 * @param dni
	 * @return
	 * @throws SQLException
	 */
	public Consumer findByDni(String dni) throws SQLException {
		Consumer result = null;
		try(PreparedStatement pst=this.conn.prepareStatement(FINBYDNI)){
			pst.setString(1, dni);
			try(ResultSet res = pst.executeQuery()){
				if(res.next()) {
					result = new Consumer();
					result.setCod_c(res.getInt("cod_c"));
					result.setDni(res.getString("dni"));
					result.setName(res.getString("nombre"));
					result.setTlf(res.getString("telefono"));
					result.setUser(res.getString("usuario"));
					result.setPassword(res.getString("contrasena"));
				}
			}
		}
		return result;
		
	}
	
	/*
	 * LOGIN 
	 */
	
	
	/**
	 * Funcion que comprueba si los datos introducidos en el login
	 * son correctos o no 
	 * @param user Usuario introducido en la ventana de login 	
	 * @param contrasena Contraseña introducida en la ventana de Login 
	 * @return Devuelve true si el usuario se encuentra en la base de datos
	 * o false si no.
	 */
	public boolean login(String user, String password) {
	    boolean logeado = false;
	    if (this.conn != null) {
	        try (PreparedStatement ps = this.conn.prepareStatement(LOGIN)){
	            ps.setString(1, user);
	            ps.setString(2, password);
	            ResultSet rs = ps.executeQuery();
	            if (rs.next()) {
	            	int cod_c = rs.getInt("cod_c");
	                String dni = rs.getString("dni");
	                String name = rs.getString("nombre");
	                String tlf = rs.getString("telefono");
	                String username = rs.getString("usuario");
	                // Establecer los datos del usuario en UserSession
	                UserSession.login(dni, name, tlf, cod_c, username);
	                logeado = true;
	            } else {
	                logeado = false;
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	            //JOptionPane.showMessageDialog(null,"Hubo un error en la ejecucion:\n"+e.getMessage());
	        }
	    }
	    return logeado;
	}

	
	/**
	 * Dao que me devuelve los datos del usuario(cliente) logueado 
	 * @param username
	 * @return
	 */
	public Consumer getUserByUser(String username) {
	    String query = "SELECT * FROM cliente WHERE usuario = ?";
	    try (PreparedStatement ps = conn.prepareStatement(query)) {
	        ps.setString(1, username);
	        ResultSet rs = ps.executeQuery();
	        if (rs.next()) {
	        	int cod_c = rs.getInt("cod_c");
	            String dni = rs.getString("dni");
	            String name = rs.getString("nombre");
	            String tlf = rs.getString("telefono");
	            String user = rs.getString("usuario");
	            String password = rs.getString("contrasena");
	            // Crea y devuelve un objeto Consumer con los datos obtenidos
	            return new Consumer(dni, name, tlf, cod_c, user, password);
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return null; // Si no se encontró el usuario, devuelve null
	}

}
