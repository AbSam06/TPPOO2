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
import tpvo.GestionLivres;

class GestionLivresTest {

    private GestionLivres gestionLivres;
    private Connection connection;

    @BeforeEach
    void setUp() throws Exception {
        // Créer une base de données en mémoire pour les tests
        connection = DriverManager.getConnection("jdbc:sqlite::memory:");
        gestionLivres = new GestionLivres();

        // Créer une table pour les livres
        try (Statement stmt = connection.createStatement()) {
            stmt.executeUpdate("CREATE TABLE Livre (" +
                    "ID_Livre INTEGER PRIMARY KEY, " +
                    "Titre TEXT, " +
                    "Annee_Publication INTEGER, " +
                    "ISBN TEXT, " +
                    "ID_Editeur INTEGER, " +
                    "ID_Categorie INTEGER)");
        }
    }

    @AfterEach
    void tearDown() throws Exception {
        if (connection != null) {
            connection.close();
        }
    }

    @Test
    void testAjouterLivre() {
        gestionLivres.ajouterLivre(1, "Les Misérables", 1862, "123456789", 101, 201);
        List<Object[]> livres = gestionLivres.lireLivres();

        assertEquals(1, livres.size(), "Il doit y avoir un livre.");
        assertEquals("Les Misérables", livres.get(0)[1], "Le titre du livre doit être 'Les Misérables'.");
    }

    @Test
    void testLireLivres() {
        gestionLivres.ajouterLivre(1, "Les Misérables", 1862, "123456789", 101, 201);
        gestionLivres.ajouterLivre(2, "Le Comte de Monte-Cristo", 1844, "987654321", 102, 202);

        List<Object[]> livres = gestionLivres.lireLivres();
        assertEquals(2, livres.size(), "Il doit y avoir deux livres.");
    }

    @Test
    void testModifierLivre() {
        gestionLivres.ajouterLivre(1, "Les Misérables", 1862, "123456789", 101, 201);
        gestionLivres.modifierLivre(1, "Les Misérables - Edition Modifiée", 1863, "987654321", 102, 202);

        List<Object[]> livres = gestionLivres.lireLivres();
        assertEquals("Les Misérables - Edition Modifiée", livres.get(0)[1], "Le titre du livre doit être modifié.");
        assertEquals(1863, livres.get(0)[2], "L'année de publication doit être modifiée.");
    }

    @Test
    void testSupprimerLivre() {
        gestionLivres.ajouterLivre(1, "Les Misérables", 1862, "123456789", 101, 201);
        gestionLivres.supprimerLivre(1);

        List<Object[]> livres = gestionLivres.lireLivres();
        assertEquals(0, livres.size(), "Il ne doit plus y avoir de livres.");
    }

    @Test
    void testRechercherLivre() {
        gestionLivres.ajouterLivre(1, "Les Misérables", 1862, "123456789", 101, 201);
        gestionLivres.ajouterLivre(2, "Le Comte de Monte-Cristo", 1844, "987654321", 102, 202);

        List<Object[]> resultats = gestionLivres.rechercherLivre("Comte");
        assertEquals(1, resultats.size(), "Il doit y avoir un livre correspondant à la recherche.");
        assertEquals("Le Comte de Monte-Cristo", resultats.get(0)[1], "Le titre doit correspondre à 'Le Comte de Monte-Cristo'.");
    }
}

