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
/**
 *
 * @author 2417011
 */
public class GestionMembres {
    private Connection connection;

    public GestionMembres() {
        // Utilisation de DatabaseManager pour obtenir la connexion
        this.connection = DatabaseManager.getInstance().getConnection();
    }

    // Ajouter un membre
    public void ajouterMembre(int ID_Membre, String nom, String prenom, String email, String dateInscription) {
        String query = "INSERT INTO Membre (ID_Membre, Nom, Prenom, Email, Date_Inscription) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, ID_Membre);
            stmt.setString(2, nom);
            stmt.setString(3, prenom);
            stmt.setString(4, email);
            stmt.setString(5, dateInscription);
            stmt.executeUpdate();
            System.out.println("Membre ajouté avec succès !");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Lire tous les membres
    public List<Object[]> lireMembres() {
        List<Object[]> membres = new ArrayList<>();
        String query = "SELECT * FROM Membre";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                // Ajouter les données des membres sous forme de tableau d'objets
                membres.add(new Object[]{
                    rs.getInt("ID_Membre"),
                    rs.getString("Nom"),
                    rs.getString("Prenom"),
                    rs.getString("Email"),
                    rs.getString("Date_Inscription")
                });
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return membres;
    }

    // Modifier un membre
    public void modifierMembre(int ID_Membre, String nom, String prenom, String email, String dateInscription) {
        String query = "UPDATE Membre SET Nom = ?, Prenom = ?, Email = ?, Date_Inscription = ? WHERE ID_Membre = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, nom);
            stmt.setString(2, prenom);
            stmt.setString(3, email);
            stmt.setString(4, dateInscription);
            stmt.setInt(5, ID_Membre);

            int rowsUpdated = stmt.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Membre modifié avec succès !");
            } else {
                System.out.println("Aucun membre trouvé avec cet ID.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Supprimer un membre
    public void supprimerMembre(int ID_Membre) {
        String query = "DELETE FROM Membre WHERE ID_Membre = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, ID_Membre);

            int rowsDeleted = stmt.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Membre supprimé avec succès !");
            } else {
                System.out.println("Aucun membre trouvé avec cet ID.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Rechercher un membre par nom
    public List<Object[]> rechercherMembre(String nomRecherche) {
        List<Object[]> membres = new ArrayList<>();
        String query = "SELECT * FROM Membre WHERE Nom LIKE ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, "%" + nomRecherche + "%");

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    // Ajouter les données des membres trouvés dans la liste
                    membres.add(new Object[]{
                        rs.getInt("ID_Membre"),
                        rs.getString("Nom"),
                        rs.getString("Prenom"),
                        rs.getString("Email"),
                        rs.getString("Date_Inscription")
                    });
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return membres;
    }
}
