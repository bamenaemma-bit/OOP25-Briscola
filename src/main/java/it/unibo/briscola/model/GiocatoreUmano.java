package it.unibo.briscola.model;
import java.util.List;
import java.util.Scanner;
/**
 * 
 * rappresenta un giocatore controllato da un umano.
 * estende GiocatoreBase e implementa la scelta della carta
 * chiedendo un input da tastiera.
 */

public class GiocatoreUmano extends GiocatoreBase {
    /**
     * construisce un giocatore umano con il nome specificato .
     * @param nome il nome del giocatore umano
     */
    public GiocatoreUmano(final String nome){
        super(nome);
    }
    /**
     * scegli una carta da giocare chiedendo all'utente di inserire un indice
     * @param briscola il seme di briscola corrente
     * @param cartaAvversaria la carta giocata dall'avversario (può essere null)
     */
     @Override 
     public Carta scegliCarta(final Seme briscola, final Carta cartaAvversaria){
       final Scanner scanner = new Scanner(System.in);
       final List<Carta> mano = this.getMano();
       //mostra le carte in mano
       System.out.println("Le tue carte:");
       for (int i = 0; i < mano.size(); i++){
        System.out.println(i + ": " + mano.get(i));

       }
        System.out.println("Briscola:" + briscola);

        // chiede all'utente di scegliere
        System.out.print("Scegli l'indice della carta da giocare:");
        final int indice = scanner.nextInt();
        return mano.get(indice);  

     }


}
