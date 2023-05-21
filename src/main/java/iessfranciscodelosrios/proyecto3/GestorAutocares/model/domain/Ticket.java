package iessfranciscodelosrios.proyecto3.GestorAutocares.model.domain;

import java.util.Date;
import java.util.Objects;

public class Ticket {
	 private Journey journey;
	 private Consumer consumer;
	 private Date date;
	 
	 
	public Ticket(Journey journey, Consumer consumer, Date date) {
		super();
		this.journey = journey;
		this.consumer = consumer;
		this.date = date;
	}


	public Ticket() {
		super();
		this.journey = null;
		this.consumer = null;
		this.date = null;
	}


	public Journey getJourney() {
		return journey;
	}


	public void setJourney(Journey journey) {
		this.journey = journey;
	}


	public Consumer getConsumer() {
		return consumer;
	}


	public void setConsumer(Consumer consumer) {
		this.consumer = consumer;
	}


	public Date getDate() {
		return date;
	}


	public void setDate(Date date) {
		this.date = date;
	}


	@Override
	public int hashCode() {
		return Objects.hash(consumer, date, journey);
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Ticket other = (Ticket) obj;
		return Objects.equals(consumer, other.consumer) && Objects.equals(date, other.date)
				&& Objects.equals(journey, other.journey);
	}


	@Override
	public String toString() {
		return "Ticket [journey=" + journey + ", consumer=" + consumer + ", date=" + date + "]";
	}
	 
	   
	 
}
