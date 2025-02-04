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
public class Category implements CategoryComponent{
    private final String name;
    private final List<CategoryComponent> components = new ArrayList<>();

    public Category(String name) {
        this.name = name;
    }

    public void add(CategoryComponent component) {
        components.add(component);
    }

    public void remove(CategoryComponent component) {
        components.remove(component);
    }

    @Override
    public void display() {
        System.out.println("Catégorie : " + name);
        for (CategoryComponent component : components) {
            component.display(); // Appel récursif
        }
    }
    
}
