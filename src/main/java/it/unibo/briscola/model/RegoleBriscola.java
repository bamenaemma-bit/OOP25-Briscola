package it.unibo.briscola.model;

/**
 * Classe che incapsula la logica di calcolo delle regole della Briscola.
 * Separare le regole dal resto della partita rispetta il principio di 
 * Singola Responsabilità (SRP), rendendo il codice più pulito e facilmente testabile.
 */
public class RegoleBriscola {

    /**
     * Determina quale dei due giocatori vince la mano confrontando le due carte giocate.
     * Applica le regole ufficiali della Briscola.
     * 
     * @param carta1 la carta giocata dal primo giocatore
     * @param carta2 la carta giocata dal secondo giocatore
     * @param briscola il seme di briscola corrente
     * @return 1 se vince il primo giocatore, 2 se vince il secondo
     */
    public int determinaVincitoreMano(final Carta carta1, final Carta carta2, final Seme briscola) {
        final boolean c1Briscola = carta1.seme() == briscola;
        final boolean c2Briscola = carta2.seme() == briscola;

        // Regola 1: se una carta è briscola e l'altra no, vince chi ha la briscola
        if (c1Briscola && !c2Briscola) {
            return 1;
        }
        if (c2Briscola && !c1Briscola) {
            return 2;
        }
        
        // Regola 2: se entrambe sono briscola, vince quella con forza maggiore
        if (c1Briscola && c2Briscola) {
            return (carta1.valore().getForza() > carta2.valore().getForza()) ? 1 : 2;
        }
        
        // Regola 3: se nessuna è briscola ma hanno lo stesso seme, vince quella con forza maggiore
        if (carta1.seme() == carta2.seme()) {
            return (carta1.valore().getForza() > carta2.valore().getForza()) ? 1 : 2;
        }
        
        // Regola 4: se nessuna è briscola e i semi sono diversi, vince la prima carta giocata
        return 1;
    }
}