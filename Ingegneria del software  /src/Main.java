import Entity.Spesa;
import exception.IDSpesaException;
import exception.SpesaException;
import exception.ZeroSconti;
import Entity.Cliente;
import Entity.Sconto;

import java.time.LocalDate;

import Controller.GestioneData;;
public class Main {

	public static void main(String[] args){
		LocalDate date = LocalDate.now() ;
		Sconto coupon_1 = new Sconto(date, 20.5);
		Sconto coupon_2 = new Sconto(date, 20.5);
		Cliente savio = new Cliente("savio.g","ciao","Salvatore","Guerrisi");
		Spesa ordine_1 = new Spesa(200.00, 180.00, date);
		Spesa ordine_2 = new Spesa(200.00, 190.00, date);
		GestioneData data = new GestioneData();
		data.addCodiceAttivo(coupon_1);
		data.addCodiceAttivo(coupon_2);
		try {
		data.addSpesa(coupon_1, savio, ordine_1);
		}catch (SpesaException e) {
			System.out.println("Risulta che il tuo codice Sconto: "+
				coupon_1.codiceSconto	+" non sia valido, procedi senza inserire alcun sconto se vuoi terminare l'acquisto");
		}
		try {
			data.addSpesa(coupon_2, savio, ordine_2);
		}catch(SpesaException e) {
			System.out.println("Risulta che il tuo codice Sconto: "+
					coupon_2.codiceSconto	+ " non sia valido, procedi senza inserire alcun sconto se vuoi terminare l'acquisto");
		}
		
		try{
			data.getCodiciAttivi();
		}catch(ZeroSconti e) {
			System.out.println("\nAttualmente non sono disponibili codici sconto fruibili");
		}
		data.StampaReport(1);
		
		
		
		
		
	}

}