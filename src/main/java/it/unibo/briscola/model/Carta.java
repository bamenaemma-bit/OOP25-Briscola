package it.unibo.briscola.model;
/**
 * rappresenta una carta da gioco, combinazione di un seme e un valore.
 * 
 * 
 */

    public record Carta( Seme seme, Valore valore){
     
    
    @Override 
    public String toString() {
        return  this.valore + " di " + this.seme;
    
    }
} 
/**
 * avrei potuto ottenere lo stesso risultato con una classe
 * normale, ma il record mi ha permesso di rispettare il principio DRY e di garantire l'immutabilità,
 * senza sforzo, evitando il rischio di dimenticare final o di scrivere equals in modo errato
 * 
 *
 */