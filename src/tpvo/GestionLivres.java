/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tpvo;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author 2417011
 */
public class GestionLivres {
    
    
    private Connection connection;

    public GestionLivres() {
        // Utilisation de DatabaseManager pour obtenir la connexion
        this.connection = DatabaseManager.getInstance().getConnection();
    }

    // Ajouter un livre
    public void ajouterLivre(int ID_Livre,String titre,int annee, String isbn,int ID_Editeur,int ID_Categorie) {
         String query = "INSERT INTO Livre (ID_Livre, Titre, Annee_Publication, ISBN, ID_Editeur, ID_Categorie) VALUES (?, ?, ?, ?,?,?)";
         try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, ID_Livre);
            stmt.setString(2, titre);
            stmt.setInt(3, annee);
            stmt.setString(4, isbn);
            stmt.setInt(5, ID_Editeur);
            stmt.setInt(6, ID_Categorie);
            stmt.executeUpdate();
            System.out.println("Livre ajouté avec succès !");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
     public List<Object[]> lireLivres() {
        List<Object[]> livres = new ArrayList<>();
        String query = "SELECT * FROM Livre";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                // Ajouter les données du livre sous forme de tableau d'objets
                livres.add(new Object[]{
                    rs.getInt("ID_Livre"),
                    rs.getString("Titre"),
                    rs.getInt("Annee_Publication"),
                    rs.getString("ISBN"),
                    rs.getInt("ID_Editeur"),
                    rs.getInt("ID_Categorie")
                });
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return livres;
    }
     
     
    public void modifierLivre(int ID_Livre, String titre, int annee, String isbn, int ID_Editeur, int ID_Categorie) {
    String query = "UPDATE Livre SET Titre = ?, Annee_Publication = ?, ISBN = ?, ID_Editeur = ?, ID_Categorie = ? WHERE ID_Livre = ?";
    try (PreparedStatement stmt = connection.prepareStatement(query)) {
        // Définir les paramètres pour la requête
        stmt.setString(1, titre);       // Nouveau titre
        stmt.setInt(2, annee);          // Nouvelle année de publication
        stmt.setString(3, isbn);        // Nouveau ISBN
        stmt.setInt(4, ID_Editeur);     // Nouvel ID éditeur
        stmt.setInt(5, ID_Categorie);   // Nouvel ID catégorie
        stmt.setInt(6, ID_Livre);       // ID du livre à modifier

        // Exécuter la requête
        int rowsUpdated = stmt.executeUpdate();
        if (rowsUpdated > 0) {
            System.out.println("Livre modifié avec succès !");
        } else {
            System.out.println("Aucun livre trouvé avec cet ID.");
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
}
    
    
public void supprimerLivre(int ID_Livre) {
    String query = "DELETE FROM Livre WHERE ID_Livre = ?";
    try (PreparedStatement stmt = connection.prepareStatement(query)) {
        // Définir le paramètre de la requête
        stmt.setInt(1, ID_Livre);

        // Exécuter la requête
        int rowsDeleted = stmt.executeUpdate();
        if (rowsDeleted > 0) {
            System.out.println("Livre supprimé avec succès !");
        } else {
            System.out.println("Aucun livre trouvé avec cet ID.");
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
}


public List<Object[]> rechercherLivre(String titreRecherche) {
    List<Object[]> livres = new ArrayList<>();
    String query = "SELECT * FROM Livre WHERE Titre LIKE ?";
    try (PreparedStatement stmt = connection.prepareStatement(query)) {
        // Ajouter des caractères génériques pour rechercher des titres partiels
        stmt.setString(1, "%" + titreRecherche + "%");

        try (ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                // Ajouter les données des livres trouvés dans la liste
                livres.add(new Object[]{
                    rs.getInt("ID_Livre"),
                    rs.getString("Titre"),
                    rs.getInt("Annee_Publication"),
                    rs.getString("ISBN"),
                    rs.getInt("ID_Editeur"),
                    rs.getInt("ID_Categorie")
                });
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return livres;
}
    
 
}
