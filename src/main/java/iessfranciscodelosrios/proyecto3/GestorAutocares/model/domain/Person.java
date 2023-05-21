package iessfranciscodelosrios.proyecto3.GestorAutocares.model.domain;

import java.util.Objects;

public class Person {
	private String dni;
	private String name;
	private String tlf;
	
	public Person(String dni, String name, String tlf) {
		
		this.dni = dni;
		this.name = name;
		this.tlf = tlf;
	}

	public Person() {
		this.dni = "";
		this.name = "";
		this.tlf = "";
		
	}

	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTlf() {
		return tlf;
	}

	public void setTlf(String tlf) {
		this.tlf = tlf;
	}

	
	
	
	
	
	@Override
	public int hashCode() {
		return Objects.hash(dni);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Person other = (Person) obj;
		return Objects.equals(dni, other.dni);
	}

	@Override
	public String toString() {
		return "dni: " + dni + ", nombre: " + name + ", tlf: " + tlf ;
	}
	
	
	
	
	
	
}

