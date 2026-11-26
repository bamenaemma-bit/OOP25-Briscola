package it.unibo.briscola.model;

/**
 * Rappresenta una partita di Briscola a 2 giocatori.
 * Gestisce il mazzo, i turni, i punteggi e le regole di presa.
 */
public class Partita {

    private final Mazzo mazzo;
    private final Giocatore giocatore1;
    private final Giocatore giocatore2;
    private Seme briscola;
    private Carta cartaBriscola;
    private int puntiGiocatore1;
    private int puntiGiocatore2;
    private boolean turnoGiocatore1;
    private boolean partitaFinita;

    /**
     * Costruisce una nuova partita tra un giocatore umano e un computer.
     * 
     * @param nomeGiocatore1 il nome del giocatore umano
     * @param nomeGiocatore2 il nome del giocatore computer
     */
    public Partita(final String nomeGiocatore1, final String nomeGiocatore2) {
        this.mazzo = new Mazzo();
        this.giocatore1 = new GiocatoreUmano(nomeGiocatore1);
        this.giocatore2 = new GiocatoreComputer(nomeGiocatore2);
        this.puntiGiocatore1 = 0;
        this.puntiGiocatore2 = 0;
        this.turnoGiocatore1 = true; 
        this.partitaFinita = false;
    }

    /**
     * Avvia la partita: mescola il mazzo, distribuisce 3 carte a testa e scopre la briscola.
     */
    public void avvia() {
        this.mazzo.mescola();
        
        // Distribuzione iniziale delle 3 carte a testa
        for (int i = 0; i < 3; i++) {
            this.giocatore1.riceviCarta(this.mazzo.pesca());
            this.giocatore2.riceviCarta(this.mazzo.pesca());
        }
        
        // Pesca e scopre la carta di briscola
        this.cartaBriscola = this.mazzo.pesca();
        this.briscola = this.cartaBriscola.seme();
        
        System.out.println("==================================");
        System.out.println("La briscola è: " + this.cartaBriscola);
        System.out.println("==================================");
    }

    /**
     * Esegue un singolo turno di gioco. 
     * Gestisce l'ordine delle giocate, il calcolo del vincitore, l'assegnazione dei punti
     * e la fase di pesca dal mazzo. Include la logica per gestire l'ultima mano
     * quando uno dei due giocatori non ha più carte.
     */
    public void giocaTurno() {
        if (this.partitaFinita) {
            throw new IllegalStateException("La partita è già finita!");
        }

        Giocatore primoGiocatore = this.turnoGiocatore1 ? this.giocatore1 : this.giocatore2;
        Giocatore secondoGiocatore = this.turnoGiocatore1 ? this.giocatore2 : this.giocatore1;

        Carta carta1 = null;
        Carta carta2 = null;

        // Il primo giocatore gioca solo se ha ancora carte in mano
        if (!primoGiocatore.getMano().isEmpty()) {
            System.out.println("\n--- Turno di " + primoGiocatore.getNome() + " ---");
            carta1 = primoGiocatore.scegliCarta(this.briscola, null);
            System.out.println(primoGiocatore.getNome() + " ha giocato: " + carta1);
        }

        // Il secondo giocatore gioca solo se ha ancora carte in mano
        if (!secondoGiocatore.getMano().isEmpty()) {
            System.out.println("--- Turno di " + secondoGiocatore.getNome() + " ---");
            carta2 = secondoGiocatore.scegliCarta(this.briscola, carta1);
            System.out.println(secondoGiocatore.getNome() + " ha giocato: " + carta2);
        }

        // Se entrambe le giocate sono nulle, non ci sono più carte sul tavolo e la partita termina
        if (carta1 == null && carta2 == null) {
            this.partitaFinita = true;
            return;
        }

        // Rimozione delle carte giocate dalla mano dei rispettivi giocatori
        if (carta1 != null) {
            primoGiocatore.getMano().remove(carta1);
        }
        if (carta2 != null) {
            secondoGiocatore.getMano().remove(carta2);
        }

        int vincitore = 0;
        int puntiMano = 0;

        // Calcolo del vincitore e dei punti della mano
        if (carta1 != null && carta2 != null) {
            // Caso standard: entrambi i giocatori hanno giocato
            vincitore = calcolaVincitore(carta1, carta2);
            puntiMano = carta1.valore().getPunti() + carta2.valore().getPunti();
        } else if (carta1 != null) {
            // Caso di fine partita: solo il primo giocatore ha giocato (l'altro non aveva carte)
            vincitore = 1;
            puntiMano = carta1.valore().getPunti();
        } else if (carta2 != null) {
            // Caso di fine partita: solo il secondo giocatore ha giocato (il primo non aveva carte)
            vincitore = 2;
            puntiMano = carta2.valore().getPunti();
        }

        // Assegnazione dei punti e aggiornamento del turno successivo
        if (vincitore == 1) {
            this.puntiGiocatore1 += puntiMano;
            System.out.println(">> " + primoGiocatore.getNome() + " vince la mano e guadagna " + puntiMano + " punti!");
            this.turnoGiocatore1 = true;
        } else if (vincitore == 2) {
            this.puntiGiocatore2 += puntiMano;
            System.out.println(">> " + secondoGiocatore.getNome() + " vince la mano e guadagna " + puntiMano + " punti!");
            this.turnoGiocatore1 = false;
        }

        // Fase di pesca: i giocatori pescano dal mazzo se ci sono ancora carte
        if (!this.mazzo.isVuoto()) {
            Giocatore vincitoreGiocatore = (vincitore == 1) ? this.giocatore1 : this.giocatore2;
            Giocatore perdenteGiocatore = (vincitore == 1) ? this.giocatore2 : this.giocatore1;
            
            // Il vincitore della mano pesca per primo
            vincitoreGiocatore.riceviCarta(this.mazzo.pesca());
            
            // Il perdente pesca solo se c'è un'ulteriore carta disponibile nel mazzo
            if (!this.mazzo.isVuoto()) {
                perdenteGiocatore.riceviCarta(this.mazzo.pesca());
            }
        }

        // Condizione di fine partita: entrambe le mani sono vuote
        if (this.giocatore1.getMano().isEmpty() && this.giocatore2.getMano().isEmpty()) {
            this.partitaFinita = true;
        }
    }

    /**
     * Calcola chi vince la mano confrontando le due carte giocate.
     * Applica le regole ufficiali della Briscola (briscola batte tutto, poi seme e forza).
     * 
     * @param carta1 la carta giocata dal primo giocatore
     * @param carta2 la carta giocata dal secondo giocatore
     * @return 1 se vince il primo giocatore, 2 se vince il secondo
     */
    private int calcolaVincitore(final Carta carta1, final Carta carta2) {
        final boolean c1Briscola = carta1.seme() == this.briscola;
        final boolean c2Briscola = carta2.seme() == this.briscola;

        // Regola 1: se una è briscola e l'altra no, vince chi ha la briscola
        if (c1Briscola && !c2Briscola) return 1;
        if (c2Briscola && !c1Briscola) return 2;
        
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

    /**
     * @return true se la partita è finita, false altrimenti.
     */
    public boolean isPartitaFinita() {
        return this.partitaFinita;
    }

    /**
     * @return il punteggio attuale del giocatore 1.
     */
    public int getPuntiGiocatore1() { 
        return this.puntiGiocatore1; 
    }

    /**
     * @return il punteggio attuale del giocatore 2.
     */
    public int getPuntiGiocatore2() { 
        return this.puntiGiocatore2; 
    }

    /**
     * Determina il nome del vincitore in base ai punteggi finali.
     * 
     * @return il nome del giocatore vincitore, o "PAREGGIO" se i punteggi sono uguali.
     */
    public String getVincitore() {
        if (this.puntiGiocatore1 > this.puntiGiocatore2) {
            return this.giocatore1.getNome();
        } else if (this.puntiGiocatore2 > this.puntiGiocatore1) {
            return this.giocatore2.getNome();
        } else {
            return "PAREGGIO";
        }
    }
}