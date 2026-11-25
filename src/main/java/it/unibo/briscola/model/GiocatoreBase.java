package it.unibo.briscola.model;
import java.util.List;
import java.util.ArrayList;
/**
 * Classe astratta che rappresenta un giocatore generico di Briscola.
 * Fornisce l'implementazione di base per la gestione del nome e della mano
 * 
 * 
 */

public abstract class GiocatoreBase implements Giocatore{
    private final String nome;
    private final List<Carta> mano;
    /**
     * costruisce un nuovo giocatore con il nome specificato
     * @param nome il nome del giocatore 
     * 
     */
    public GiocatoreBase(final String nome ){
      this.nome = nome;
      this.mano = new ArrayList<>();
       
    }
    @Override 
    public String getNome(){
            return this.nome;
    }
      @Override 
      public List<Carta> getMano(){
       return this.mano;
          
      }
      @Override 
      public void riceviCarta(final Carta carta){
        this.mano.add(carta);
      }
/**
 * Metodo astratto che deve essere implementato dalle sottoclassi,
 * Decide quale carta giocare in base alla briscola e alla carta avversaria.
 * @param brsicola il seme di briscola corrente
 * @param cartaAvversaria la carta giocata dall'avversario (null se è il primo a giocare)
 * @return la carta scelta da giocare
 */
@Override 
public abstract Carta scegliCarta(Seme briscola, Carta cartaAvversariCarta);

}
