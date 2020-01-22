
package Controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import Entity.Cliente;
import Entity.Sconto;
import Entity.Spesa;
import exception.IDSpesaException;
import exception.SpesaException;
import exception.ZeroSconti;

public class GestioneData {
	private ArrayList<Sconto> codiciScontoAttivi;
	private ArrayList<Cliente> clienti;
	private ArrayList<Spesa> ordini;
	private ArrayList<Sconto> codici;

	public GestioneData() {

		this.codiciScontoAttivi = new ArrayList<Sconto>();
		this.clienti = new ArrayList<Cliente>();
		this.ordini = new ArrayList<Spesa>();
		this.codici = new ArrayList<Sconto>();
	}

	public void addCodiceAttivo(Sconto s) {
		this.codiciScontoAttivi.add(s);
	}
	public void addClienti(Cliente c) {
		clienti.add(c);
	}




	public void getCodiciAttivi() throws ZeroSconti {
		if(this.codiciScontoAttivi.size() == 0 )throw new ZeroSconti(); // gestisco l'evenienza in cui non siano presenti sconti
		for(Sconto s : this.codiciScontoAttivi) {
			System.out.println("\nI codici sconto attivi sono : \n" + s.getCodiceSconto() + " ");
		}

	}
	//-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------//
	// Ho 2 versioni di addspesa, una con sconto e l'altra senza alcun sconto

	public void addSpesa(Sconto cod, Cliente c, Spesa ordine) throws SpesaException {

		if (codiciScontoAttivi.contains(cod) && !c.getCodiciUsati().contains(cod) && ordine.getTotaleScontato()!=ordine.getTotaleSpeso()) {
			clienti.add(c);
			ordine.setIsScontato(true);
			ordini.add(ordine);
			codici.add(cod);
			// verifico che il codice sconto sia attivo (non scaduto) e che il cliente non lo abbia usato già e che nel carrello risulti che il totale scontato sia diverso da 0

			c.setOrdiniEffetuati(ordine);
			c.setCodiciUsati(cod);
			System.out.print("\nLa spesa è stata aggiunta con successo!\nIl totale speso è: " + ordine.getTotaleSpeso() + "€ ed il prezzo scontato è : " + ordine.getTotaleScontato()+"€");

		}else {
			throw new SpesaException(); // gestisco sovrapposizioni ordini con codice sconto e dunque se inserissi 2 volte lo stesso carrello avrei che il mio codice sconto risulterebbe usato
		}

	}


	public void addSpesa(Cliente c, Spesa ordine) throws IDSpesaException {
		if (ordini.contains(ordine)) {
			throw new IDSpesaException();
			} else // gestisco sovrapposizioni ordini verificando che l'articolo non sia già nella mia lista ordini
		clienti.add(c);
		ordine.setIsScontato(false);

		ordini.add(ordine);
		c.setOrdiniEffetuati(ordine);
		System.out.print("\nLa spesa è stata aggiunta con successo!\nIl totale speso è: " + ordine.getTotaleSpeso() + "€");


	}

	//-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------//

	
	
	public void StampaReport(int n) {
		
		HashMap<Cliente, double[]> tmp = new HashMap<>();
		tmp.putAll(createReport(n));
		for (Entry<Cliente, double[]> entry : tmp.entrySet()) {
			System.out.println("\nI clienti che possono usifruire di sconti sono :\n"+ "- " + entry.getKey().getCognome() + " " + entry.getKey().getNome() + " user: " + entry.getKey().getUsername() + "\n");
			System.out.println("Il totale speso da: "+ entry.getKey().getNome() + " " + entry.getKey().getCognome() +" è " + entry.getKey().getTot(entry.getKey()) + "€");
			System.out.println("Il totale speso da ogni cliente corrisponde a: " + entry.getKey().getTotaleSpeso()+"€");
			
		
		}
		

	}
	public double[] getNspeseTot(Cliente c) {
		double[] data = new double[2];
			data[0] = c.getTot(c);
			data[1] = c.getNspese();
			
		return data;
	}

	public HashMap<Cliente, double[]> createReport(int n)  {
		HashMap <Cliente, double[]> reportMap = new HashMap <>();
		for(Cliente c : this.clienti) {
			if(c.getNspese() >= n) {
				reportMap.put(c, this.getNspeseTot(c));
			}
		}
		
		return reportMap;
	}





}
