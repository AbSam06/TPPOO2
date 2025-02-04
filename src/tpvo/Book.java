/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tpvo;

/**
 *
 * @author 2417011
 */
public class Book implements CategoryComponent{
    private final String title;

    public Book(String title) {
        this.title = title;
    }

    @Override
    public void display() {
        System.out.println("Livre : " + title);
    }
}
