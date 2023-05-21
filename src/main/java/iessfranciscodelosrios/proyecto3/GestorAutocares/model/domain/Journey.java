package iessfranciscodelosrios.proyecto3.GestorAutocares.model.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Journey {
	private int cod_v;
	private String origin;
	private String destination;
	private List<Ticket> tickets = null;
	public Journey(int cod_v, String origin, String destination) {
		super();
		this.cod_v = cod_v;
		this.origin = origin;
		this.destination = destination;
	}
	public Journey(String origin, String destination) {
		super();
		this.origin = origin;
		this.destination = destination;
	}
	public Journey() {
		super();
		this.cod_v = 0;
		this.origin = null;
		this.destination = null;
		
	}
	public int getCod_v() {
		return cod_v;
	}
	public void setCod_v(int cod_v) {
		this.cod_v = cod_v;
	}
	public String getOrigin() {
		return origin;
	}
	public void setOrigin(String origin) {
		this.origin = origin;
	}
	public String getDestination() {
		return destination;
	}
	public void setDestination(String destination) {
		this.destination = destination;
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
		return Objects.hash(cod_v, destination, origin);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Journey other = (Journey) obj;
		return cod_v == other.cod_v && Objects.equals(destination, other.destination)
				&& Objects.equals(origin, other.origin);
	}
	@Override
	public String toString() {
		return "Journey [cod_v=" + cod_v + ", origin=" + origin + ", destination=" + destination + "]";
	}
	
	
	
}
