package it.unibo.briscola.model;

import java.util.List;
import java.util.Scanner;

/**
 * Rappresenta un giocatore umano controllato tramite input da terminale.
 * Estende GiocatoreBase per ereditare la gestione del nome e della mano.
 */
public class GiocatoreUmano extends GiocatoreBase {
    
    /**
     * Costruisce un giocatore umano con il nome specificato.
     * 
     * @param nome il nome del giocatore umano
     */
    public GiocatoreUmano(final String nome) {
        super(nome);
    }

    /**
     * Permette al giocatore umano di scegliere una carta tramite input da tastiera.
     * Include un ciclo di validazione per gestire input non validi senza mandare in crash il programma.
     * 
     * @param briscola il seme di briscola corrente
     * @param cartaAvversaria la carta giocata dall'avversario (può essere null se gioca per primo)
     * @return la carta scelta dal giocatore
     */
    @Override 
    public Carta scegliCarta(final Seme briscola, final Carta cartaAvversaria) {
       final Scanner scanner = new Scanner(System.in);
       final List<Carta> mano = this.getMano();
       
       // Mostra le carte attualmente in mano al giocatore
       System.out.println("Le tue carte in mano:");
       for (int i = 0; i < mano.size(); i++) {
           System.out.println("[" + i + "] " + mano.get(i));
       }
       
       // Se l'avversario ha già giocato, mostra la sua carta
       if (cartaAvversaria != null) {
           System.out.println("L'avversario ha giocato: " + cartaAvversaria);
       }

       // Ciclo infinito che si interrompe solo quando viene fornito un input valido
       while (true) {
           System.out.print("Scegli l'indice della carta da giocare: ");
           try {
               final int indice = scanner.nextInt();
               
               // Verifica che l'indice inserito sia compreso tra quelli disponibili nella mano
               if (indice >= 0 && indice < mano.size()) {
                   return mano.get(indice);
               } else {
                   System.out.println("Errore: indice non valido. Riprova.");
               }
           } catch (Exception e) {
               // Gestisce l'eccezione se l'utente inserisce caratteri non numerici
               System.out.println("Errore: devi inserire un numero valido. Riprova.");
               scanner.nextLine(); // Pulisce il buffer dello scanner per evitare loop infiniti
           }
       }
    }
}