package Entity;

import java.time.LocalDate;
import java.util.UUID;


public class Spesa {		

	public String id;
	private Double totaleSpeso;
	private LocalDate data;
	private Boolean isScontato;
	private Double totaleScontato;

	public Spesa() {
		this.id += UUID.nameUUIDFromBytes((Math.random()+LocalDate.now().toString()).getBytes()).toString();;
		this.totaleSpeso = 0.00;
		this.totaleScontato = this.totaleSpeso;
		this.data = LocalDate.now();
		this.isScontato = false;
	}
	// setto ID spesa in base alla data e l'ora per evitare ci siano sovrapposizioni.

	public Spesa(Double tot, Double totScontato, LocalDate date) {

		this.isScontato = true;
		this.totaleSpeso = tot;
		if (totScontato == 0 ) {
			this.totaleScontato = this.totaleSpeso;
		}else {
			this.totaleScontato = totScontato;}
		this.data = date;
		this.id=UUID.nameUUIDFromBytes((tot + LocalDate.now().toString()).getBytes()).toString();	}
	// la generazione di ID spesa viene creata basandosi su tot e la data attuale

	//-------------------------------------- Metodi get & set ----------------------------------------------------//

	public String getId() {
		return this.getId();

	}
	

	public Double getTotaleSpeso() {
		return this.totaleSpeso;
	}

	public LocalDate getData() {
		return this.data;
	}

	public Boolean getIsScontato() {
		return this.isScontato;
	}

	public Double getTotaleScontato() {
		return this.totaleScontato;
	}

	public void setIsScontato(Boolean value) {
		this.isScontato = value;

	}
	public void setData (LocalDate value) {
		this.data = value;
	}

	public void setTotaleSpeso (Double value) {
		this.totaleSpeso = value;
	}

	public void setTotaleScontato (Double value) {
		this.totaleScontato = value;
	}

}
