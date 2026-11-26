package it.unibo.briscola.model;
import java.util.List;

public interface Giocatore {
  String getNome();
  List<Carta> getMano();
  void riceviCarta(Carta carta);
  Carta scegliCarta(Seme briscola, Carta cartaAvversaria);
}