package Entity;

import java.time.LocalDate;
import java.util.UUID;

public class Sconto {
	public String codiceSconto;
	public LocalDate dataScadenza;
	public double percentuale;
	
	
	public Sconto() {
		this.codiceSconto = null;
		this.dataScadenza = LocalDate.of(2020, 12, 31);
		this.percentuale = 0.00;
	}
	
	public Sconto (LocalDate scadenza, double p) {
		this.codiceSconto = UUID.nameUUIDFromBytes((scadenza.toString()).getBytes()).toString();
		this.dataScadenza = scadenza;
		this.percentuale = p;
	}
	
//-------------------------------------- Metodi get & set ----------------------------------------------------//	
	
	
	public String getCodiceSconto () {
		return this.codiceSconto;
	}
	
	public LocalDate getDataScadenza () {
		return this.dataScadenza;
	}
	
	public double getpercentuale() {
		return this.percentuale;
	}

	public void setDataScadenza(LocalDate value) {
		this.dataScadenza = value;
	}
	
	public void setPercentuale(double value) {
		this.percentuale = value;
	}
}
