package gestioneData.test;

import static org.junit.Assert.*;

import java.time.LocalDate;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import Controller.GestioneData;
import Entity.Cliente;
import Entity.Sconto;
import Entity.Spesa;
import exception.IDSpesaException;
import exception.SpesaException;

public class TesterGestioneData {

	LocalDate date ;
	Sconto coupon_1 ;
	Sconto coupon_2 ;
	Sconto coupon_3 ;
	Cliente savio ;
	Cliente giacomo ;
	Cliente francesco ;
	Spesa ordine_1 ;
	Spesa ordine_2 ;
	Spesa ordine_3 ;
	GestioneData data ;

	@Before
	public void setUp() throws Exception {
		date = LocalDate.now() ;
		coupon_1= new Sconto(date, 20);
		coupon_2 = new Sconto(date,10);
		coupon_3 = new Sconto(date,50);
		savio = new Cliente("savio.g","elefante123","Salvatore","Guerrisi");
		giacomo = new Cliente("jack.p", "polipo99","Giacomo", "Sanmontana");
		francesco = new Cliente("desaparetito", "sorrento123","Francesco", "Tito");
		ordine_1 = new Spesa(200.00, 180.00, date);
		ordine_2 = new Spesa(300.00, 150.00, date);
		ordine_3 = new Spesa(100.00, 80.00, date);
		data = new GestioneData();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	// n > Nspese  (n corrisponde alla soglia minima di acquisti da fare per usufruire di sconti)
	public void testCreateReport0() {


		try {
			data.addSpesa(savio, ordine_1);
		}catch (IDSpesaException e) {
		}
		try {
			data.addSpesa(savio, ordine_2);
		}catch(IDSpesaException e) {

		}


		assertEquals(0,data.createReport(3).size());

	}

	@Test
	// n = Nspese (n corrisponde alla soglia minima di acquisti da fare per usufruire di sconti)
	public void testCreateReport1() {


		try {
			data.addSpesa(savio, ordine_1);
		}catch (IDSpesaException e) {

		}
		try {
			data.addSpesa(francesco, ordine_2);
		}catch(IDSpesaException e) {

		}


		assertEquals(2,data.createReport(1).size());


	}

	@Test
	// n < Nspese (n corrisponde alla soglia minima di acquisti da fare per usufruire di sconti)
	public void testCreateReport2() {



		try {
			data.addSpesa(giacomo, ordine_1);
		}catch (IDSpesaException e) {
		}
		try {
			data.addSpesa(giacomo, ordine_2);
		}catch(IDSpesaException e) {

		}
		try {
			data.addSpesa(giacomo, ordine_3);
		}catch(IDSpesaException e) {

		}

		assertEquals(1,data.createReport(2).size());

	}




	@Test
	// verifico che il cliente francesco abbia speso 180 euro pur non essendo l'unico a far acquisti
	public void testCreateReport3() {

		data.addCodiceAttivo(coupon_1);
		data.addCodiceAttivo(coupon_2);
		data.addCodiceAttivo(coupon_3);

		try {
			data.addSpesa(coupon_1, francesco, ordine_1);
		}catch (SpesaException e) {
			System.out.println("Risulta che il tuo codice Sconto: "+
					coupon_1.codiceSconto	+" non sia valido, procedi senza inserire alcun sconto se vuoi terminare l'acquisto");
		}
		try {
			data.addSpesa(coupon_2, giacomo, ordine_2);
		}catch(SpesaException e) {
			System.out.println("Risulta che il tuo codice Sconto: "+
					coupon_2.codiceSconto	+ " non sia valido, procedi senza inserire alcun sconto se vuoi terminare l'acquisto");
		}
		try {
			data.addSpesa(coupon_3, giacomo, ordine_3);
		}catch(SpesaException e) {
			System.out.println("Risulta che il tuo codice Sconto: "+
					coupon_3.codiceSconto	+ " non sia valido, procedi senza inserire alcun sconto se vuoi terminare l'acquisto");
		}

		assertTrue(180.00 == data.createReport(0).get(francesco)[0]);
	}
	@Test
	// verifico che il cliente francesco abbia speso 180 euro essendo l'unico cliente
	public void testCreateReport4() {

		data.addCodiceAttivo(coupon_1);


		try {
			data.addSpesa(francesco, ordine_1);
		}catch (IDSpesaException e) {
		}


		assertTrue(180.00 == data.createReport(0).get(francesco)[0]);
	}

	@Test
	// verifico il totale speso da ogni cliente che usa lo stesso coupon
	public void testCreateReport8() {

		data.addCodiceAttivo(coupon_1);


		try {
			data.addSpesa(coupon_1, francesco, ordine_1);
		}catch (SpesaException e) {
			System.out.println("Risulta che il tuo codice Sconto: "+
					coupon_1.codiceSconto	+" non sia valido, procedi senza inserire alcun sconto se vuoi terminare l'acquisto");
		}
		try {
			data.addSpesa(coupon_1, savio, ordine_2);
		}catch (SpesaException e) {
			System.out.println("Risulta che il tuo codice Sconto: "+
					coupon_1.codiceSconto	+" non sia valido, procedi senza inserire alcun sconto se vuoi terminare l'acquisto");
		}


		assertTrue(180.00 == data.createReport(0).get(francesco)[0]);
		assertTrue(150.00 == data.createReport(0).get(savio)[0]);
	}


	@Test
	//verifico che l'inserimento di una spesa con uno stesso sconto applicato dallo stesso cliente non è possibile
	public void testCreateReport5() {
		data.addCodiceAttivo(coupon_1);
		try {
			data.addSpesa(coupon_1, giacomo, ordine_1);
		}catch (SpesaException e) {
			System.out.println("Risulta che il tuo codice Sconto: "+
					coupon_1.codiceSconto	+" non sia valido, procedi senza inserire alcun sconto se vuoi terminare l'acquisto");
		}
		try {
			data.addSpesa(coupon_1, giacomo, ordine_2);
		}catch (SpesaException e) {
			System.out.println("Risulta che il tuo codice Sconto: "+
					coupon_1.codiceSconto	+" non sia valido, procedi senza inserire alcun sconto se vuoi terminare l'acquisto");
		}
		assertTrue(1 == data.createReport(0).get(giacomo)[1]); // ho verificato che Nspese sia rimasto a 1
	}

	@Test
	//verifico che non è possibile effettuare 2 volte la stessa spesa (coupon diversi)

	public void testCreateReport6() {
		data.addCodiceAttivo(coupon_1);
		try {
			data.addSpesa(coupon_1, giacomo, ordine_1);
		}catch (SpesaException e) {
			System.out.println("Risulta che il tuo codice Sconto: "+
					coupon_1.codiceSconto	+" non sia valido, procedi senza inserire alcun sconto se vuoi terminare l'acquisto");
		}
		try {
			data.addSpesa(coupon_2, giacomo, ordine_1);
		}catch (SpesaException e) {
			System.out.println("Risulta che il tuo codice Sconto: "+
					coupon_2.codiceSconto	+" non sia valido, procedi senza inserire alcun sconto se vuoi terminare l'acquisto");
		}
		assertTrue(1 == data.createReport(0).get(giacomo)[1]); // ho verificato che Nspese sia rimasto a 1

	}

	@Test
	//verifico che non è possibile effetuare una spesa con codici non attivi
	public void testCreateReport7() {
		data.addCodiceAttivo(coupon_2);
		try {
			data.addSpesa(coupon_1, savio, ordine_1);
		}catch (SpesaException e) {
			System.out.println("Risulta che il tuo codice Sconto: "+
					coupon_1.codiceSconto	+" non sia valido, procedi senza inserire alcun sconto se vuoi terminare l'acquisto");
		}
		try {
			data.addSpesa(coupon_2, savio, ordine_1);
		}catch (SpesaException e) {
			System.out.println("Risulta che il tuo codice Sconto: "+
					coupon_2.codiceSconto	+" non sia valido, procedi senza inserire alcun sconto se vuoi terminare l'acquisto");
		}
		assertTrue(1 == data.createReport(0).get(savio)[1]); // ho verificato che Nspese sia rimasto a 0

	}



	@Test
	// n > Nspese  (n corrisponde alla soglia minima di acquisti da fare per usufruire di sconti)
	public void testCreateReport9() {


		try {
			data.addSpesa(savio, ordine_1);
		}catch (IDSpesaException e) {
		}
		try {
			data.addSpesa(savio, ordine_1);
		}catch(IDSpesaException e) {

		}


		assertEquals(1,data.createReport(0).size());

	}



}





