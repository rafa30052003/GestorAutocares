package iessfranciscodelosrios.proyecto3.GestorAutocares.model.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Consumer extends Person{
	private int cod_c;
	private String user;
	private String password;
	private List<Ticket> tickets = null;
	
	
	
	
	public Consumer(String dni, String name, String tlf, int cod_c, String user, String password) {
		super(dni, name, tlf);
		this.cod_c = cod_c;
		this.user = user;
		this.password = password;
		
	}




	public Consumer(String dni, String name, String tlf, String user, String password) {
		super(dni, name, tlf);
		this.user = user;
		this.password = password;
	
	}
	
	
	


	public Consumer(String dni, String name, String tlf, String user) {
		super(dni, name, tlf);
		this.user = user;
	}




	public Consumer(String dni, String name, String tlf, int cod_c, String user) {
		super(dni, name, tlf);
		this.cod_c = cod_c;
		this.user = user;
	}




	public Consumer() {
		super("", "", "");
		this.cod_c = 0;
		this.user = "";
		this.password = "";
		
	}




	public int getCod_c() {
		return cod_c;
	}




	public void setCod_c(int cod_c) {
		this.cod_c = cod_c;
	}




	public String getUser() {
		return user;
	}




	public void setUser(String user) {
		this.user = user;
	}




	public String getPassword() {
		return password;
	}




	public void setPassword(String password) {
		this.password = password;
	}




	public List<Ticket> getTickets() {
		return tickets;
	}




	public void setTickets(List<Ticket> tickets) {
		this.tickets = tickets;
	}
	
	
	public void addJourney(Ticket ticket) {
		if(this.tickets==null) {
			this.tickets=new ArrayList<>();
			
		}
		this.tickets.add(ticket);
	}




	




	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(cod_c);
		return result;
	}




	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Consumer other = (Consumer) obj;
		return cod_c == other.cod_c;
	}




	@Override
	public String toString() {
		return super.toString()+ "cod_c: " + cod_c + ", user: " + user + ", password: " + password ;
	}
	
	
	
	
	
	
}
