/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tpvo;

/**
 *
 * @author 2417011
 */

import java.sql.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
public class GestionEmprunts {
    
    private Connection connection;
    private Map<Integer, List<Observer>> reservations;
    private Map<Integer, Boolean> disponibilites;
    public GestionEmprunts() {
        // Utilisation de DatabaseManager pour obtenir la connexion
        this.connection = DatabaseManager.getInstance().getConnection();
        this.reservations = new HashMap<>();
        this.disponibilites = new HashMap<>();
    }

    // Ajouter un emprunt
    public void ajouterEmprunt(int ID_Emprunt, int ID_Livre, int ID_Membre, String Date_Emprunt, String Date_Retour_Prevue, String Date_Retour_Effective) {
        String query = "INSERT INTO Emprunt (ID_Emprunt, ID_Livre, ID_Membre, Date_Emprunt, Date_Retour_Prevue, Date_Retour_Effective) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, ID_Emprunt);
            stmt.setInt(2, ID_Livre);
            stmt.setInt(3, ID_Membre);
            stmt.setString(4, Date_Emprunt);
            stmt.setString(5, Date_Retour_Prevue);

            if (Date_Retour_Effective == null || Date_Retour_Effective.trim().isEmpty()) {
                stmt.setNull(6, java.sql.Types.DATE);
            } else {
                stmt.setString(6, Date_Retour_Effective);
            }

            stmt.executeUpdate();
            System.out.println("Emprunt ajouté avec succès !");
            disponibilites.put(ID_Livre, false); // Livre non disponible une fois emprunté
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Lire tous les emprunts
    public List<Object[]> lireEmprunts() {
        List<Object[]> emprunts = new ArrayList<>();
        String query = "SELECT * FROM Emprunt";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                emprunts.add(new Object[]{
                    rs.getInt("ID_Emprunt"),
                    rs.getInt("ID_Livre"),
                    rs.getInt("ID_Membre"),
                    rs.getString("Date_Emprunt"),
                    rs.getString("Date_Retour_Prevue"),
                    rs.getString("Date_Retour_Effective")
                });
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return emprunts;
    }

    // Modifier un emprunt
    public void modifierEmprunt(int ID_Emprunt, int ID_Livre, int ID_Membre, String Date_Emprunt, String Date_Retour_Prevue, String Date_Retour_Effective) {
        String query = "UPDATE Emprunt SET ID_Livre = ?, ID_Membre = ?, Date_Emprunt = ?, Date_Retour_Prevue = ?, Date_Retour_Effective = ? WHERE ID_Emprunt = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, ID_Livre);
            stmt.setInt(2, ID_Membre);
            stmt.setString(3, Date_Emprunt);
            stmt.setString(4, Date_Retour_Prevue);
            stmt.setString(5, Date_Retour_Effective);
            stmt.setInt(6, ID_Emprunt);
            int rowsUpdated = stmt.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Emprunt modifié avec succès !");
            } else {
                System.out.println("Aucun emprunt trouvé avec cet ID.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Supprimer un emprunt
    public void supprimerEmprunt(int ID_Emprunt) {
        String query = "DELETE FROM Emprunt WHERE ID_Emprunt = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, ID_Emprunt);
            int rowsDeleted = stmt.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Emprunt supprimé avec succès !");
            } else {
                System.out.println("Aucun emprunt trouvé avec cet ID.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Rechercher un emprunt par ID_Livre ou ID_Membre
    public List<Object[]> rechercherEmprunt(int ID_Livre, int ID_Membre) {
        List<Object[]> emprunts = new ArrayList<>();
        String query = "SELECT * FROM Emprunt WHERE ID_Livre = ? OR ID_Membre = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, ID_Livre);
            stmt.setInt(2, ID_Membre);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    emprunts.add(new Object[]{
                        rs.getInt("ID_Emprunt"),
                        rs.getInt("ID_Livre"),
                        rs.getInt("ID_Membre"),
                        rs.getString("Date_Emprunt"),
                        rs.getString("Date_Retour_Prevue"),
                        rs.getString("Date_Retour_Effective")
                    });
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return emprunts;
    }
    
    
    // Réserver un livre
    public void reserverLivre(int ID_Livre, Observer membre) {
        if (!reservations.containsKey(ID_Livre)) {
            reservations.put(ID_Livre, new ArrayList<>());
        }
        reservations.get(ID_Livre).add(membre);
        System.out.println("Réservation enregistrée pour le livre ID : " + ID_Livre);
    }

    // Retourner un livre
    public void retournerLivre(int ID_Livre) {
        disponibilites.put(ID_Livre, true); // Livre marqué comme disponible
        notifierMembres(ID_Livre);
    }

    // Notifier les membres
    private void notifierMembres(int ID_Livre) {
        List<Observer> observers = reservations.get(ID_Livre);
        if (observers != null) {
            for (Observer membre : observers) {
                membre.update("Le livre ID " + ID_Livre + " est maintenant disponible !");
            }
            reservations.remove(ID_Livre); // Réinitialiser les réservations après notification
        }
    }
    
}
