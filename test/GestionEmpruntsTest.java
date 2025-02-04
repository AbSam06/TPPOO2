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
import tpvo.GestionEmprunts;

class GestionEmpruntsTest {

    private GestionEmprunts gestionEmprunts;
    private Connection connection;

    @BeforeEach
    void setUp() throws Exception {
        // Créer une base de données en mémoire pour les tests
        connection = DriverManager.getConnection("jdbc:sqlite::memory:");
        gestionEmprunts = new GestionEmprunts();

        // Créer une table pour les emprunts
        try (Statement stmt = connection.createStatement()) {
            stmt.executeUpdate("CREATE TABLE Emprunt (ID_Emprunt INTEGER PRIMARY KEY, ID_Livre INTEGER, ID_Membre INTEGER, Date_Emprunt TEXT, Date_Retour_Prevue TEXT, Date_Retour_Effective TEXT)");
        }
    }

    @AfterEach
    void tearDown() throws Exception {
        if (connection != null) {
            connection.close();
        }
    }

    @Test
    void testAjouterEmprunt() {
        gestionEmprunts.ajouterEmprunt(1, 101, 201, "2025-01-01", "2025-01-15", null);
        List<Object[]> emprunts = gestionEmprunts.lireEmprunts();

        assertEquals(1, emprunts.size(), "Il doit y avoir un emprunt.");
        assertEquals(101, emprunts.get(0)[1], "L'ID du livre doit être 101.");
    }

    @Test
    void testLireEmprunts() {
        gestionEmprunts.ajouterEmprunt(1, 101, 201, "2025-01-01", "2025-01-15", null);
        gestionEmprunts.ajouterEmprunt(2, 102, 202, "2025-01-02", "2025-01-16", null);

        List<Object[]> emprunts = gestionEmprunts.lireEmprunts();
        assertEquals(2, emprunts.size(), "Il doit y avoir deux emprunts.");
    }

    @Test
    void testModifierEmprunt() {
        gestionEmprunts.ajouterEmprunt(1, 101, 201, "2025-01-01", "2025-01-15", null);
        gestionEmprunts.modifierEmprunt(1, 102, 202, "2025-01-02", "2025-01-16", null);

        List<Object[]> emprunts = gestionEmprunts.lireEmprunts();
        assertEquals(102, emprunts.get(0)[1], "L'ID du livre doit avoir été modifié à 102.");
        assertEquals(202, emprunts.get(0)[2], "L'ID du membre doit avoir été modifié à 202.");
    }

    @Test
    void testSupprimerEmprunt() {
        gestionEmprunts.ajouterEmprunt(1, 101, 201, "2025-01-01", "2025-01-15", null);
        gestionEmprunts.supprimerEmprunt(1);

        List<Object[]> emprunts = gestionEmprunts.lireEmprunts();
        assertEquals(0, emprunts.size(), "Il ne doit plus y avoir d'emprunt.");
    }

    @Test
    void testRechercherEmprunt() {
        gestionEmprunts.ajouterEmprunt(1, 101, 201, "2025-01-01", "2025-01-15", null);
        gestionEmprunts.ajouterEmprunt(2, 102, 202, "2025-01-02", "2025-01-16", null);

        List<Object[]> emprunts = gestionEmprunts.rechercherEmprunt(101, 0);
        assertEquals(1, emprunts.size(), "Il doit y avoir un emprunt pour le livre 101.");
    }

  

    @Test
    void testRetournerLivre() {
        gestionEmprunts.ajouterEmprunt(1, 101, 201, "2025-01-01", "2025-01-15", null);
        gestionEmprunts.retournerLivre(101);

        // Vérifier que le livre est marqué comme disponible
        assertTrue(gestionEmprunts.rechercherEmprunt(101, 0).isEmpty(), "Le livre doit être disponible après avoir été retourné.");
    }
}
