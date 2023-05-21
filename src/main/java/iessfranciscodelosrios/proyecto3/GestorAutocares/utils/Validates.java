package iessfranciscodelosrios.proyecto3.GestorAutocares.utils;

import java.security.MessageDigest;

import javafx.scene.control.Alert;

public class Validates {
	
	/**
	 * Metodo que te hashea una contrseña
	 * @param s
	 * @return
	 */
	public static String encryptSHA256 (String s){
        String result = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA256");
            md.update(s.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte aByte : md.digest()) {
                sb.append(Integer.toString((aByte & 0xff) + 0x100, 16).substring(1));
            }
            result = sb.toString();
        }catch(Exception e){
            e.printStackTrace();
        }
        return result;
    }
	
	
	
	/*
	 * Envia una alerta de cuando ha ocurrido un error
	 */
	 public static void alertError(String titulo, String header, String content) {
		 Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setHeaderText(header);
			alert.setTitle(titulo);
			alert.setContentText(content);
			alert.showAndWait();
	 }
	 
	 
		
		/*
		 * Envia una alerta de cuando este bien
		 */
		 public static void alertCorrect(String titulo, String header, String content) {
			 Alert alert = new Alert(Alert.AlertType.INFORMATION);
				alert.setHeaderText(header);
				alert.setTitle(titulo);
				alert.setContentText(content);
				alert.showAndWait();
		 }
		 
		 
		 
		 /**
		  * valida el dni
		  * @param dni
		  * @return
		  */
		 public static boolean validarDNI(String dni) {
			    // Eliminar espacios en blanco y convertir a mayúsculas
			    dni = dni.trim().toUpperCase();

			    // Verificar longitud del DNI
			    if (dni.length() != 9) {
			        // Mostrar alerta
			    	alertError("ERROR", "INTRODUCCION DEL DNI", "El DNI debe tener 9 caracteres");
			        
			        return false;
			    }

			    // Extraer número y letra del DNI
			    String numero = dni.substring(0, 8);
			    String letra = dni.substring(8);

			    // Verificar que el número sea válido (solo dígitos)
			    try {
			        Integer.parseInt(numero);
			    } catch (NumberFormatException e) {
			        // Mostrar alerta
			    	alertError("ERROR", "INTRODUCCION DEL DNI", "El número del DNI debe ser un valor numérico");
			      
			        return false;
			    }

			    // Verificar que la letra sea válida
			    String letrasValidas = "TRWAGMYFPDXBNJZSQVHLCKE";
			    int indice = Integer.parseInt(numero) % 23;
			    char letraValida = letrasValidas.charAt(indice);

			    if (letraValida != letra.charAt(0)) {
			        // Mostrar alerta
			    	alertError("ERROR", "INTRODUCCION DEL DNI", "La letra del DNI no es valida");
			        
			        return false;
			    }

			    // El DNI es válido
			    return true;
			}

		 /**
		  * valida telefono
		  * @param telefono
		  * @return
		  */
		 public static boolean validarTelefono(String telefono) {
			 String regex = "^[0-9]{9}$"; // Patrón que coincide con 9 dígitos consecutivos
			    boolean esValido = telefono.matches(regex);
			    
			    if (!esValido) {
			       alertError("ERROR", "INTRODUCCION DEL TELEFONO", "El numero debe tener 9 numeros");
			    }
			    
			    return esValido;
			}


}
