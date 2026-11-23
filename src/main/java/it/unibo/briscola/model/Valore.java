package it.unibo.briscola.model;
/**
 *questa rappresenta i valori delle carte con i relativi punti e forza
 * e mostra i getter per ottenere il punto e la forza di ogni valore(DUE,QUATTRO,CINQUE...) delle carte
 * 
 * 
 */
public enum Valore {
    // Qui elenco tutti i valori  con i loro parametri 
    DUE(0, 1), QUATTRO(0,2), CINQUE(0,3), SEI(0,4), 
    SETTE(0,5), FANTE(2,6), CAVALLO(3,7), RE(4,8), TRE(10, 9), ASSO(11, 10);
    // poi metto i campi (final e private)
    private final int punti;
    private final int forza;
    //il costruttore (private) e preferisco mettere private lo userò solo dentro questa classe

    private Valore(int punti, int forza){
        this.punti = punti;
        this.forza= forza;
    }    

    // poi scrivo i getter per permetterà di ottenere i punti e la forza
    public int getPunti(){
        return this.punti;
    }

    public int getForza(){
        return this.forza;
    }
}
