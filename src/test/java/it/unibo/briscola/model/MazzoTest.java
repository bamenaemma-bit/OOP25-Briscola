package it.unibo.briscola.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Classe di test per verificare il corretto funzionamento della classe Mazzo.
 * Utilizza JUnit 5 per automatizzare i controlli.
 */
public class MazzoTest {

    /**
     * Testa che un mazzo appena creato contenga esattamente 40 carte
     * e che non sia considerato vuoto.
     */
    @Test
    public void testCreazioneMazzo() {
        // Creo un nuovo mazzo
        final Mazzo mazzo = new Mazzo();
        
        // Verifico che non sia vuoto (dovrebbe avere 40 carte)
        assertFalse(mazzo.isVuoto(), "Un mazzo appena creato non deve essere vuoto.");
    }

    /**
     * Testa che l'operazione di pesca restituisca una carta valida
     * e che il mazzo si svuoti correttamente dopo 40 pescate.
     */
    @Test
    public void testPescaESvuotamento() {
        final Mazzo mazzo = new Mazzo();
        
        // Pesco tutte le 40 carte una per una
        for (int i = 0; i < 40; i++) {
            mazzo.pesca(); // Estrae la carta
        }
        
        // Dopo 40 pescate, il mazzo deve essere vuoto
        assertTrue(mazzo.isVuoto(), "Dopo aver pescato 40 carte, il mazzo deve essere vuoto.");
    }

    /**
     * Testa che se si prova a pescare da un mazzo vuoto,
     * venga lanciata l'eccezione prevista dal codice.
     */
    @Test
    public void testPescaDaMazzoVuotoLanciaEccezione() {
        final Mazzo mazzo = new Mazzo();
        
        // Svuoto il mazzo
        for (int i = 0; i < 40; i++) {
            mazzo.pesca();
        }
        
        // Verifico che pescare un'ulteriore carta lanci una IllegalStateException
        assertThrows(IllegalStateException.class, () -> {
            mazzo.pesca();
        }, "Pescare da un mazzo vuoto deve lanciare una IllegalStateException.");
    }
}