package it.unibo.briscola.model;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
/**
 * 
 * questa classe rappresenta un mazzo di 40 carte, e mostrare come va fatta la gestione di questi 40 carte attraverso i metodi
 * 
 */
public class Mazzo {

    
    private final List<Carta> carte;

    /**
     * crea un mazzo ordinato con le 40 combinazioni
     * 
     */
public Mazzo(){
this.carte = new ArrayList<>();
for (final Seme seme:Seme.values()){
    for (final Valore valore: Valore.values()){
        this.carte.add(new Carta(seme, valore));

    }
}

}


/**
 * Mescola casualmente le carte nel mazzo
 */
public void mescola(){
    Collections.shuffle(this.carte);

}
/**
 * toglie dal mazzo e restituisce a carta in cima
 * 
 */
public Carta pesca(){
    if (this.carte.isEmpty()){
        throw new IllegalStateException("il mazzo è vuoto");

    }
    return this.carte.remove(0);
}
}
