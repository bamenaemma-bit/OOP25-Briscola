package it.unibo.briscola.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Classe di test per verificare l'esattezza delle regole della Briscola.
 * Testare questa classe isolata è semplice e non richiede input da tastiera.
 */
public class RegoleBriscolaTest {

    private final RegoleBriscola regole = new RegoleBriscola();

    @Test
    public void testBriscolaBattononBriscola() {
        // Setup: creiamo una briscola di DENARI
        final Seme briscola = Seme.DENARI;
        
        // Carta 1: Due di Bastoni (non briscola, forza 1)
        final Carta carta1 = new Carta(Seme.BASTONI, Valore.DUE);
        
        // Carta 2: Due di Denari (briscola, forza 1)
        final Carta carta2 = new Carta(Seme.DENARI, Valore.DUE);

        // Azione: la briscola è giocata come seconda carta
        final int vincitore = regole.determinaVincitoreMano(carta1, carta2, briscola);
        
        // Verifica: vince il giocatore 2 perché ha la briscola
        assertEquals(2, vincitore, "La briscola deve battere una carta non di briscola.");
    }

    @Test
    public void testStessoSemeVinceIlPiuForte() {
        final Seme briscola = Seme.DENARI;
        
        // Carta 1: Re di Coppe (forza 8)
        final Carta carta1 = new Carta(Seme.COPPE, Valore.RE);
        // Carta 2: Asso di Coppe (forza 10)
        final Carta carta2 = new Carta(Seme.COPPE, Valore.ASSO);

        final int vincitore = regole.determinaVincitoreMano(carta1, carta2, briscola);
        
        assertEquals(2, vincitore, "A parità di seme, deve vincere la carta con forza maggiore.");
    }

    @Test
    public void testSemiDiversiVinceLaPrima() {
        final Seme briscola = Seme.DENARI;
        
        // Carta 1: Asso di Spade (forza 10, non briscola)
        final Carta carta1 = new Carta(Seme.SPADE, Valore.ASSO);
        // Carta 2: Due di Coppe (forza 1, non briscola)
        final Carta carta2 = new Carta(Seme.COPPE, Valore.DUE);

        final int vincitore = regole.determinaVincitoreMano(carta1, carta2, briscola);
        
        assertEquals(1, vincitore, "Se i semi sono diversi e non c'è briscola, vince la prima carta giocata.");
    }
}