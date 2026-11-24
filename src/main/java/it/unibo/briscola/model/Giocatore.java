package it.unibo.briscola.model;
import java.util.List;
/**
 * 
 * rappresenta le caratteristiche e le azioni di un giocatore ,  di Briscola
 */
public interface Giocatore {
  String getNome();// per sapere il nome del giocatore
  List<Carta> getMano();// per vedere le carte che ha in mano
  void riceviCarta(Carta carta);//per aggiungere una carta alla mano quando pesca
  Carta scegliCarta(Seme briscola, Carta cartaAvversaria);//questo metodo decide quale carta giocare



}
