package it.unibo.briscola.model;
/**
 * Questa classe rappresenta i quattro semi del mazzo di carte italiane
 * ho scelto enum perchè DENARI, COPPE, SPADE , BASTONI
 * sono dei volari che non possono mai cambiare in futuro, anche se avevo pensato usarli in una classe 
 * innestata in carta.java ma poi ho notato che diventerà un disastro in avanti
 * 
 */
public enum Seme {
   DENARI, COPPE, SPADE, BASTONI;
    
}
