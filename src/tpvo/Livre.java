/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tpvo;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author 2417011
 */
public class Livre implements Subject{
    private int idLivre;
    private String titre;
    private boolean disponible;
    private List<Observer> observers;

    public Livre(int idLivre, String titre, boolean disponible) {
        this.idLivre = idLivre;
        this.titre = titre;
        this.disponible = disponible;
        this.observers = new ArrayList<>();
    }

    public int getIdLivre() {
        return idLivre;
    }

    public String getTitre() {
        return titre;
    }

    public boolean isDisponible() {
        return disponible;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;

        // Notifier les membres si le livre devient disponible
        if (disponible) {
            notifyObservers();
        }
    }

    @Override
    public void attach(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void detach(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers() {
        for (Observer observer : observers) {
            observer.update("Le livre '" + titre + "' est maintenant disponible !");
        }
    }
}
