package Entity;

import java.util.ArrayList;


public class Cliente {
	public String username;
	private String password;
	private String nome;
	private String cognome;
	public Integer nSpese;

	private  ArrayList<Sconto> codiciUsati= new ArrayList<Sconto>();
	private  ArrayList<Spesa> ordiniEffettuati = new ArrayList<Spesa>();
	private double totales;



	// Ometto attributi presenti nella specifica in quanto non utili alla realizzazione della funzionalità di generazione di un report 

	public Cliente () {
		this.username = null;
		this.password = null;
		this.cognome = null;
		this.cognome = null;
		this.nSpese = 0;
		this.totales = 0.0;


	}

	public Cliente (String user, String pass, String surname, String name) {
		this.username = user;
		this.password = pass;
		this.nome = name;
		this.cognome = surname;
		this.nSpese = 0;
		this.totales = 0.0;

	}

	//-------------------------------------- Metodi get & set ----------------------------------------------------//

	public String getUsername () {
		return this.username;
	}

	public String getPassword () {
		return this.password;
	} 

	public String getNome () {
		return this.nome;	
	}

	public String getCognome () {
		return this.cognome;
	}

	public Integer getNspese() {
		return this.nSpese;
	}

	public ArrayList<Sconto> getCodiciUsati() {
		return this.codiciUsati;
	}

	public ArrayList<Spesa> getOrdiniEffettuati() {
		return this.ordiniEffettuati;
	}
	
	public double getTot(Cliente c) {
		double totale = 0;
		
		for(Spesa s : c.getOrdiniEffettuati()) {
			
			totale += s.getTotaleScontato();
			
		}
		return totale;
	}
	


	public double getTotaleSpeso() {
		if (totales !=0.00) {
			this.totales = 0.00;
		}

		for (Spesa s :this.ordiniEffettuati) {
			totales += s.getTotaleScontato();
		}
		return totales;
	}

		public void setCodiciUsati(Sconto s){
			this.codiciUsati.add(s);

		}

		public void setOrdiniEffetuati(Spesa s) {
			this.ordiniEffettuati.add(s);
			this.nSpese++;
		}
		//------------------------------------------------------------------------------------------------------------//

		public void removeOrdine(Spesa s) {
			int index = this.ordiniEffettuati.indexOf(s);
			this.ordiniEffettuati.remove(index);				// Funzione di rimozione ordine, utile per gestire problemi di ordine
			this.codiciUsati.remove(index);

		}





	}
