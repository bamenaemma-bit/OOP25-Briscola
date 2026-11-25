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
     * Avvia la partita: mescola il mazzo, distribuisce le carte e scopre la briscola.
     */
    public void avvia() {
        this.mazzo.mescola();
        
        // Distribuisce 3 carte a testa
        for (int i = 0; i < 3; i++) {
            this.giocatore1.riceviCarta(this.mazzo.pesca());
            this.giocatore2.riceviCarta(this.mazzo.pesca());
        }
        
        // Scopre la briscola
        this.cartaBriscola = this.mazzo.pesca();
        this.briscola = this.cartaBriscola.seme();
        
        System.out.println("La briscola è: " + this.briscola);
    }

    /**
     * Esegue un turno di gioco: entrambi i giocatori giocano una carta,
     * si calcola il vincitore, si assegnano i punti e si pesca.
     */
    public void giocaTurno() {
        if (this.partitaFinita) {
            throw new IllegalStateException("La partita è già finita!");
        }

        // Gestisce l'ordine dei turni
        Giocatore primoGiocatore = this.turnoGiocatore1 ? this.giocatore1 : this.giocatore2;
        Giocatore secondoGiocatore = this.turnoGiocatore1 ? this.giocatore2 : this.giocatore1;

        // I due giocatori scelgono la carta
        System.out.println("\n--- Turno di " + primoGiocatore.getNome() + " ---");
        Carta carta1 = primoGiocatore.scegliCarta(this.briscola, null);
        
        System.out.println("--- Turno di " + secondoGiocatore.getNome() + " ---");
        Carta carta2 = secondoGiocatore.scegliCarta(this.briscola, carta1);

        // Rimuove le carte dalla mano
        primoGiocatore.getMano().remove(carta1);
        secondoGiocatore.getMano().remove(carta2);

        // Calcola il vincitore della mano
        int vincitore = calcolaVincitore(carta1, carta2);

        // Assegna i punti
        int puntiMano = carta1.valore().getPunti() + carta2.valore().getPunti();
        
        if (vincitore == 1) {
            this.puntiGiocatore1 += puntiMano;
            System.out.println(primoGiocatore.getNome() + " vince la mano e guadagna " + puntiMano + " punti!");
            this.turnoGiocatore1 = true; // Il vincitore gioca per primo nel prossimo turno
        } else {
            this.puntiGiocatore2 += puntiMano;
            System.out.println(secondoGiocatore.getNome() + " vince la mano e guadagna " + puntiMano + " punti!");
            this.turnoGiocatore1 = false;
        }

        // Pesca nuove carte se il mazzo non è vuoto
        if (!this.mazzo.isVuoto()) {
            Giocatore vincitoreGiocatore = (vincitore == 1) ? this.giocatore1 : this.giocatore2;
            Giocatore perdenteGiocatore = (vincitore == 1) ? this.giocatore2 : this.giocatore1;
            
            vincitoreGiocatore.riceviCarta(this.mazzo.pesca());
            perdenteGiocatore.riceviCarta(this.mazzo.pesca());
        }

        // Controlla se la partita è finita
        if (this.giocatore1.getMano().isEmpty() && this.giocatore2.getMano().isEmpty()) {
            this.partitaFinita = true;
            System.out.println("\n--- PARTITA FINITA ---");
            System.out.println("Punteggio finale:");
            System.out.println(this.giocatore1.getNome() + ": " + this.puntiGiocatore1);
            System.out.println(this.giocatore2.getNome() + ": " + this.puntiGiocatore2);
        }
    }

    /**
     * Calcola chi vince la mano in base alle regole della Briscola.
     * 
     * @param carta1 la carta giocata dal primo giocatore
     * @param carta2 la carta giocata dal secondo giocatore
     * @return 1 se vince il primo giocatore, 2 se vince il secondo
     */
    private int calcolaVincitore(Carta carta1, Carta carta2) {
        boolean c1Briscola = carta1.seme() == this.briscola;
        boolean c2Briscola = carta2.seme() == this.briscola;

        // 1. Se una è briscola e l'altra no, vince la briscola
        if (c1Briscola && !c2Briscola) {
            return 1;
        }
        if (c2Briscola && !c1Briscola) {
            return 2;
        }
        
        // 2. Se entrambe sono briscola, vince la più forte
        if (c1Briscola && c2Briscola) {
            return (carta1.valore().getForza() > carta2.valore().getForza()) ? 1 : 2;
        }

        // 3. Se nessuna è briscola ma sono dello stesso seme, vince la più forte
        if (carta1.seme() == carta2.seme()) {
            return (carta1.valore().getForza() > carta2.valore().getForza()) ? 1 : 2;
        }

        // 4. Se nessuna è briscola e sono di semi diversi, vince la prima carta giocata
        return 1;
    }

    /**
     * @return true se la partita è finita
     */
    public boolean isPartitaFinita() {
        return this.partitaFinita;
    }

    /**
     * @return il punteggio del giocatore 1
     */
    public int getPuntiGiocatore1() {
        return this.puntiGiocatore1;
    }

    /**
     * @return il punteggio del giocatore 2
     */
    public int getPuntiGiocatore2() {
        return this.puntiGiocatore2;
    }
}