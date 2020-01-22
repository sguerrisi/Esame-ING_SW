package exception;

public class SpesaException extends Exception{
	public SpesaException(){
		System.out.println("Risulta che il tuo codice Sconto non sia valido, procedi senza inserire alcun sconto se vuoi terminare l'acquisto");
	}
}
