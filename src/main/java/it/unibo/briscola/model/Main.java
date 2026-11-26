package it.unibo.briscola.model;

/**
 * Classe principale per eseguire e testare una partita di Briscola da terminale.
 */
public class Main {

    /**
     * Punto di ingresso del programma.
     * 
     * @param args argomenti da linea di comando (non utilizzati)
     */
    public static void main(String[] args) {
        // 1. Inizializzazione della partita specificando i nomi dei giocatori
        final Partita partita = new Partita("Emmanuel", "Computer");
        
        // 2. Avvio della partita (mescolamento, distribuzione e briscola)
        partita.avvia();
        
        // 3. Ciclo di gioco principale: continua finché la partita non è finita
        while (!partita.isPartitaFinita()) {
            partita.giocaTurno();
        }
        
        // 4. Stampa del riepilogo finale con i punteggi
        System.out.println("\n==================================");
        System.out.println("       FINE DELLA PARTITA         ");
        System.out.println("==================================");
        System.out.println("Punteggio di Emmanuel: " + partita.getPuntiGiocatore1());
        System.out.println("Punteggio di Computer: " + partita.getPuntiGiocatore2());
        
        // 5. Determinazione e stampa del vincitore
        final String vincitore = partita.getVincitore();
        if (vincitore.equals("PAREGGIO")) {
            System.out.println("RISULTATO: È un pareggio!");
        } else {
            System.out.println("HA VINTO: " + vincitore);
        }
    }
}