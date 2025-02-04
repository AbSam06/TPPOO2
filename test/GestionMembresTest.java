/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author 2417011
 */
import org.junit.jupiter.api.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import tpvo.GestionMembres;

class GestionMembresTest {

    private GestionMembres gestionMembres;
    private Connection connection;

    @BeforeEach
    void setUp() throws Exception {
        // Créer une base de données en mémoire pour les tests
        connection = DriverManager.getConnection("jdbc:sqlite::memory:");
        gestionMembres = new GestionMembres();

        // Créer une table pour les membres
        try (Statement stmt = connection.createStatement()) {
            stmt.executeUpdate("CREATE TABLE Membre (" +
                    "ID_Membre INTEGER PRIMARY KEY, " +
                    "Nom TEXT, " +
                    "Prenom TEXT, " +
                    "Email TEXT, " +
                    "Date_Inscription TEXT)");
        }
    }

    @AfterEach
    void tearDown() throws Exception {
        if (connection != null) {
            connection.close();
        }
    }

    @Test
    void testAjouterMembre() {
        gestionMembres.ajouterMembre(1, "Dupont", "Jean", "jean.dupont@example.com", "2025-01-01");
        List<Object[]> membres = gestionMembres.lireMembres();

        assertEquals(1, membres.size(), "Il doit y avoir un membre.");
        assertEquals("Dupont", membres.get(0)[1], "Le nom du membre doit être 'Dupont'.");
    }

    @Test
    void testLireMembres() {
        gestionMembres.ajouterMembre(1, "Dupont", "Jean", "jean.dupont@example.com", "2025-01-01");
        gestionMembres.ajouterMembre(2, "Durand", "Sophie", "sophie.durand@example.com", "2025-01-02");

        List<Object[]> membres = gestionMembres.lireMembres();
        assertEquals(2, membres.size(), "Il doit y avoir deux membres.");
    }

    @Test
    void testModifierMembre() {
        gestionMembres.ajouterMembre(1, "Dupont", "Jean", "jean.dupont@example.com", "2025-01-01");
        gestionMembres.modifierMembre(1, "Durand", "Sophie", "sophie.durand@example.com", "2025-01-02");

        List<Object[]> membres = gestionMembres.lireMembres();
        assertEquals("Durand", membres.get(0)[1], "Le nom du membre doit être modifié.");
        assertEquals("Sophie", membres.get(0)[2], "Le prénom du membre doit être modifié.");
    }

    @Test
    void testSupprimerMembre() {
        gestionMembres.ajouterMembre(1, "Dupont", "Jean", "jean.dupont@example.com", "2025-01-01");
        gestionMembres.supprimerMembre(1);

        List<Object[]> membres = gestionMembres.lireMembres();
        assertEquals(0, membres.size(), "Il ne doit plus y avoir de membres.");
    }

    @Test
    void testRechercherMembre() {
        gestionMembres.ajouterMembre(1, "Dupont", "Jean", "jean.dupont@example.com", "2025-01-01");
        gestionMembres.ajouterMembre(2, "Durand", "Sophie", "sophie.durand@example.com", "2025-01-02");

        List<Object[]> resultats = gestionMembres.rechercherMembre("Durand");
        assertEquals(1, resultats.size(), "Il doit y avoir un membre correspondant à la recherche.");
        assertEquals("Durand", resultats.get(0)[1], "Le nom doit correspondre à 'Durand'.");
    }
}
