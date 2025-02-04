import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import tpvo.Livre;
import tpvo.Observer;

class LivreTest {

    private Livre livre;
    private StringBuilder notification1;
    private StringBuilder notification2;

    @BeforeEach
    void setUp() {
        // Initialiser le livre et les "observateurs" (sous forme de StringBuilder pour capturer les notifications)
        livre = new Livre(1, "Les Misérables", false);
        notification1 = new StringBuilder();
        notification2 = new StringBuilder();
    }

    @Test
    void testInitialState() {
        // Vérifier l'état initial du livre
        assertEquals(1, livre.getIdLivre(), "L'ID du livre doit être 1.");
        assertEquals("Les Misérables", livre.getTitre(), "Le titre du livre doit être 'Les Misérables'.");
        assertFalse(livre.isDisponible(), "Le livre ne doit pas être disponible initialement.");
    }

    @Test
    void testAttachObserver() {
        // Ajouter un observateur
        livre.attach(message -> notification1.append(message));

        // Modifier l'état du livre
        livre.setDisponible(true);

        // Vérifier que l'observateur a reçu la notification
        assertEquals("Le livre 'Les Misérables' est maintenant disponible !", notification1.toString());
    }

    @Test
    void testDetachObserver() {
        // Ajouter puis détacher un observateur
        Observer observer = message -> notification1.append(message);
        livre.attach(observer);
        livre.detach(observer);

        // Modifier l'état du livre
        livre.setDisponible(true);

        // Vérifier qu'aucune notification n'a été reçue après le détachement
        assertTrue(notification1.isEmpty(), "L'observateur détaché ne doit pas recevoir de notification.");
    }

    @Test
    void testNotifyObserversWhenAvailable() {
        // Ajouter plusieurs observateurs
        livre.attach(message -> notification1.append(message));
        livre.attach(message -> notification2.append(message));

        // Modifier l'état du livre
        livre.setDisponible(true);

        // Vérifier que tous les observateurs ont été notifiés
        assertEquals("Le livre 'Les Misérables' est maintenant disponible !", notification1.toString());
        assertEquals("Le livre 'Les Misérables' est maintenant disponible !", notification2.toString());
    }

    @Test
    void testNoNotificationWhenUnavailable() {
        // Ajouter plusieurs observateurs
        livre.attach(message -> notification1.append(message));
        livre.attach(message -> notification2.append(message));

        // Modifier l'état du livre à indisponible
        livre.setDisponible(false);

        // Vérifier qu'aucune notification n'a été envoyée
        assertTrue(notification1.isEmpty(), "Les observateurs ne doivent pas recevoir de notification lorsque le livre est indisponible.");
        assertTrue(notification2.isEmpty(), "Les observateurs ne doivent pas recevoir de notification lorsque le livre est indisponible.");
    }
}
