package it.unibo.briscola.model;


/**
 * rappresenta un giocatore controllata dal computer.
 * Estende GiocaotoreBase per ereditare la gestione
 * della mano e del nome,
 * e implements la logica per scegliere la carta
 */
public class GiocatoreComputer extends GiocatoreBase{// eredita tutti i metodi del padre
    /**
     * Costruisce un giocatore computer con il nome specificato
     * @param nome il nome computer ( esempio: Computer)
     * 
     */
public  GiocatoreComputer(final String nome){
    super(nome); //chiama il costruttore della classe padre 
}
  /**
   * sceglie una carta da giocare
   * per ora, la strategia è molto: gioca la prima carta disponibile
   * @param briscola il seme di briscola corrente
   * @param cartaAvversaria la carta giocata dall'avversaria (può essere null)
   * @return la carta scelta */  
  @Override 
  public Carta scegliCarta(final Seme briscola, final Carta cartaAvversaria){
    //strategia semplice : restituisce la prima carta in  mano
    return this.getMano().get(0);
  }
}
