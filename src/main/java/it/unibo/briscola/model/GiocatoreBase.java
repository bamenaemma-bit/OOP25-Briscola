package it.unibo.briscola.model;
import java.util.List;
import java.util.ArrayList;

public abstract class GiocatoreBase implements Giocatore {
    private final String nome;
    private final List<Carta> mano;

    public GiocatoreBase(final String nome) {
      this.nome = nome;
      this.mano = new ArrayList<>();
    }

    @Override 
    public String getNome() {
        return this.nome;
    }

    @Override 
    public List<Carta> getMano() {
        return this.mano;
    }

    @Override 
    public void riceviCarta(final Carta carta) {
        this.mano.add(carta);
    }

    @Override 
    public abstract Carta scegliCarta(Seme briscola, Carta cartaAvversaria);
}