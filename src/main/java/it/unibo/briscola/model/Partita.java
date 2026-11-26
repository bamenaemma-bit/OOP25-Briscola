package it.unibo.briscola.model;

/**
 * Rappresenta una partita di Briscola a 2 giocatori.
 * Gestisce il mazzo, i turni e i punteggi, delegando il calcolo delle regole 
 * alla classe specializzata RegoleBriscola.
 */
public class Partita {

    private final Mazzo mazzo;
    private final Giocatore giocatore1;
    private final Giocatore giocatore2;
    private final RegoleBriscola regole; // Delega delle regole
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
        this.regole = new RegoleBriscola();
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
        
        for (int i = 0; i < 3; i++) {
            this.giocatore1.riceviCarta(this.mazzo.pesca());
            this.giocatore2.riceviCarta(this.mazzo.pesca());
        }
        
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

        if (!primoGiocatore.getMano().isEmpty()) {
            System.out.println("\n--- Turno di " + primoGiocatore.getNome() + " ---");
            carta1 = primoGiocatore.scegliCarta(this.briscola, null);
            System.out.println(primoGiocatore.getNome() + " ha giocato: " + carta1);
        }

        if (!secondoGiocatore.getMano().isEmpty()) {
            System.out.println("--- Turno di " + secondoGiocatore.getNome() + " ---");
            carta2 = secondoGiocatore.scegliCarta(this.briscola, carta1);
            System.out.println(secondoGiocatore.getNome() + " ha giocato: " + carta2);
        }

        if (carta1 == null && carta2 == null) {
            this.partitaFinita = true;
            return;
        }

        if (carta1 != null) {
            primoGiocatore.getMano().remove(carta1);
        }
        if (carta2 != null) {
            secondoGiocatore.getMano().remove(carta2);
        }

        int vincitore = 0;
        int puntiMano = 0;

        if (carta1 != null && carta2 != null) {
            // Uso della classe delegata per il calcolo
            vincitore = this.regole.determinaVincitoreMano(carta1, carta2, this.briscola);
            puntiMano = carta1.valore().getPunti() + carta2.valore().getPunti();
        } else if (carta1 != null) {
            vincitore = 1;
            puntiMano = carta1.valore().getPunti();
        } else if (carta2 != null) {
            vincitore = 2;
            puntiMano = carta2.valore().getPunti();
        }

        if (vincitore == 1) {
            this.puntiGiocatore1 += puntiMano;
            System.out.println(">> " + primoGiocatore.getNome() + " vince la mano e guadagna " + puntiMano + " punti!");
            this.turnoGiocatore1 = true;
        } else if (vincitore == 2) {
            this.puntiGiocatore2 += puntiMano;
            System.out.println(">> " + secondoGiocatore.getNome() + " vince la mano e guadagna " + puntiMano + " punti!");
            this.turnoGiocatore1 = false;
        }

        if (!this.mazzo.isVuoto()) {
            Giocatore vincitoreGiocatore = (vincitore == 1) ? this.giocatore1 : this.giocatore2;
            Giocatore perdenteGiocatore = (vincitore == 1) ? this.giocatore2 : this.giocatore1;
            
            vincitoreGiocatore.riceviCarta(this.mazzo.pesca());
            
            if (!this.mazzo.isVuoto()) {
                perdenteGiocatore.riceviCarta(this.mazzo.pesca());
            }
        }

        if (this.giocatore1.getMano().isEmpty() && this.giocatore2.getMano().isEmpty()) {
            this.partitaFinita = true;
        }
    }

    public boolean isPartitaFinita() {
        return this.partitaFinita;
    }

    public int getPuntiGiocatore1() { 
        return this.puntiGiocatore1; 
    }

    public int getPuntiGiocatore2() { 
        return this.puntiGiocatore2; 
    }

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