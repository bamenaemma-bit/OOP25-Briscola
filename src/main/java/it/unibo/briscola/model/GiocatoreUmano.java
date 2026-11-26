package it.unibo.briscola.model;

import java.util.List;
import java.util.Scanner;

/**
 * Rappresenta un giocatore umano controllato tramite input da terminale.
 * Estende la classe astratta GiocatoreBase per ereditare la gestione del nome e della mano,
 * e implementa la logica per la scelta della carta tramite l'interazione con l'utente.
 */
public class GiocatoreUmano extends GiocatoreBase {
    
    /**
     * Lo scanner per la lettura dell'input da tastiera.
     * Viene dichiarato come attributo final della classe (e non istanziato nel metodo) 
     * per evitare il problema del "resource leak" e per non chiudere prematuramente System.in,
     * consentendo di riutilizzare lo stesso oggetto per tutte le giocate.
     */
    private final Scanner scanner;

    /**
     * Costruisce un giocatore umano con il nome specificato.
     * Inizializza anche lo scanner per l'input da terminale.
     * 
     * @param nome il nome del giocatore umano
     */
    public GiocatoreUmano(final String nome) {
        super(nome);
        this.scanner = new Scanner(System.in);
    }

    /**
     * Permette al giocatore umano di scegliere una carta da giocare.
     * Mostra a video le carte in mano e, se presente, la carta avversaria.
     * Include un ciclo di validazione per gestire input errati (es. lettere o indici inesistenti)
     * senza mandare in crash il programma.
     * 
     * @param briscola il seme di briscola corrente
     * @param cartaAvversaria la carta giocata dall'avversario (può essere null se gioca per primo)
     * @return la carta scelta dal giocatore
     */
    @Override 
    public Carta scegliCarta(final Seme briscola, final Carta cartaAvversaria) {
       final List<Carta> mano = this.getMano();
       
       // Mostra le carte attualmente in mano al giocatore
       System.out.println("Le tue carte in mano:");
       for (int i = 0; i < mano.size(); i++) {
           System.out.println("[" + i + "] " + mano.get(i));
       }
       
       // Se l'avversario ha già giocato, mostra la sua carta per permettere la strategia
       if (cartaAvversaria != null) {
           System.out.println("L'avversario ha giocato: " + cartaAvversaria);
       }

       // Ciclo infinito che si interrompe solo quando viene fornito un input valido
       while (true) {
           System.out.print("Scegli l'indice della carta da giocare: ");
           try {
               final int indice = this.scanner.nextInt();
               
               // Verifica che l'indice inserito corrisponda a una carta effettivamente in mano
               if (indice >= 0 && indice < mano.size()) {
                   return mano.get(indice);
               } else {
                   System.out.println("Errore: indice non valido. Riprova.");
               }
           } catch (Exception e) {
               // Gestisce l'eccezione se l'utente digita caratteri non numerici
               System.out.println("Errore: devi inserire un numero valido. Riprova.");
               this.scanner.nextLine(); // Pulisce il buffer dello scanner per evitare loop infiniti
           }
       }
    }
}