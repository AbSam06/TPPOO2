/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tpvo;

/**
 *
 * @author 2417011
 */
public class Membre implements Observer{
    private int idMembre;
    private String nom;
    private String email;

    public Membre(int idMembre, String nom, String email) {
        this.idMembre = idMembre;
        this.nom = nom;
        this.email = email;
    }
    
     // Getters
    public int getIdMembre() {
        return idMembre;
    }

    public String getNom() {
        return nom;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public void update(String message) {
        // Afficher une notification pour le membre
        System.out.println("Notification pour " + nom + " (" + email + "): " + message);
    }
}
