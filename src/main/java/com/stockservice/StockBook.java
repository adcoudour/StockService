/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.stockservice;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * Classe qui permet de réaliser toutes les requêtes du stockBook.
 * @author adcoudour
 */
public class StockBook {
    public StockBook() {
    }
    
    /**
     * Récupère la quantité en fonction d'un livre
     * Si le livre n'existe pas dans la base renvoie une erreur où si il y a eu une erreur sur la requête
     * @param isbn identifiant du livre
     * @return ResponseEntity contenant si la fonction à été une réussite où non.
     */
    public ResponseEntity<Object> getStockDataBase(String isbn){
        int stock = 0;
         try {
            Connection conn = new ConnectionManager().getConnection();
            createDataBase();
            PreparedStatement stmt = conn.prepareStatement("SELECT quantite FROM StockBook WHERE isbn=?");
            stmt.setString(1, isbn);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                stock = rs.getInt(1);
            }else{      
                System.out.println("Book not found "+isbn);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Book not found "+isbn);
            }
            conn.close();
             System.out.println("Quantity for the isbn : "+isbn +" is : "+ stock);
            return ResponseEntity.ok(stock);
        } catch (SQLException ex) {
             System.out.println("Request error for book :"+isbn);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Request error for book :"+isbn);
        }
    }
    
    public boolean IsbnExiste(String isbn){
                int stock = 0;
         try {
            Connection conn = new ConnectionManager().getConnection();
            createDataBase();
            PreparedStatement stmt = conn.prepareStatement("SELECT quantite FROM StockBook WHERE isbn=?");
            stmt.setString(1, isbn);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                stock = rs.getInt(1);
            }else{      
                System.out.println("Book not found "+isbn);
                return false;
            }
            conn.close();
             System.out.println("Quantity for the isbn : "+isbn +" is : "+ stock);
            return true;
        } catch (SQLException ex) {
             System.out.println("Request error for book :"+isbn);
             return false;
        }
    }
    
    /**
     * Modification de la base de donnéee permettant de rajouter du stock
     * @param isbn identifiant du livre
     * @param quantity quantité que l'on souhaite rajouté
     * @return état de la fonction
     */
    public ResponseEntity<Object> addDataBase(String isbn, int quantity){
        try {
            if (!IsbnExiste(isbn)){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Book not found :"+isbn);
            }
            
            Connection conn = new ConnectionManager().getConnection();
            PreparedStatement stmt = conn.prepareStatement("UPDATE StockBook SET quantite = quantite + ? WHERE isbn = ?");
            stmt.setInt(1, quantity);
            stmt.setString(2, isbn);
            stmt.executeUpdate();
            conn.close();
            System.out.println("WholeSaler Order Sent add "+ quantity +" for the book "+isbn);
            return ResponseEntity.ok("Wholesaler order sent");
        } catch (SQLException e) {
            System.out.println("Order sent probleme for book : "+isbn);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("order sent problem for book :"+isbn);
        }
    }
    
    /**
     * Modification de la base de donnéee permettant de supprimer du stock
     * @param isbn identifiant du livre
     * @param quantity quantité que l'on souhaite enlever
     * @return état de la fonction
     */
    public ResponseEntity<Object> removeDataBase(String isbn, int quantity){
        try {
            if (!IsbnExiste(isbn)){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Book not found :"+isbn);
            }
            Connection conn = new ConnectionManager().getConnection();
            PreparedStatement stmt = conn.prepareStatement("UPDATE StockBook SET quantite = quantite - ? WHERE isbn=?");
            stmt.setInt(1, quantity);
            stmt.setString(2, isbn);
            stmt.executeUpdate();
            conn.close();
            System.out.println("Withdrawal of "+quantity + " for the book : "+isbn);
            return ResponseEntity.ok("Withdrawal of "+quantity + " for the book : "+isbn);
        } catch (SQLException e) {
            System.out.println("Error in withdrawal");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error in withdrawal");
        }
    }
    
    /**
     * Insertion dans la base de donnée d'une nouvelle information
     * @param isbn identifiant du livre
     * @param quantite quantité que l'on souhaite pour initialiser par défaut 0
     * @return état de la statut
     */
    public ResponseEntity<Object> InsertDataBase(String isbn,int quantite){
        try {
            if (IsbnExiste(isbn)){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Book already exist :"+isbn);
            }
            Connection conn = new ConnectionManager().getConnection();
            PreparedStatement insert = conn.prepareStatement("INSERT INTO StockBook(isbn,quantite) VALUES (?,?)");
            insert.setString(1, isbn);
            insert.setInt(2, quantite);
            insert.executeQuery();
            conn.close();
            System.out.println("added the line for the book: "+isbn);
            return ResponseEntity.ok("added the line for the book: "+isbn);
            } catch (SQLException e) {
            System.out.println("Error adding line for the book :" +isbn);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error adding line for the book :" +isbn);
        }
    }
    
    /**
     * Permet de supprimer la table de stockBook
     * @return réussite où non de la suppression
     */
    public ResponseEntity<Object> dropTableDataBase(){
        try {
            Connection conn = new ConnectionManager().getConnection();

            Statement stmt = conn.createStatement();
            stmt.executeUpdate("DROP TABLE IF EXISTS StockBook");
            
            conn.close();
            System.out.println("Table StockBook Dropped");
            return ResponseEntity.ok("Table StockBook Dropped");
            } catch (SQLException e) {
            System.out.println("Erreur dans le dropDataBase");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Erreur dans le dropDataBase " + e.getMessage());
        }
    }
    
    /**
     * Création de la base de donnée stockBook
     * @return réussite où non de la création
     */
    public ResponseEntity<Object> createDataBase(){
        try {
            Connection conn = new ConnectionManager().getConnection();
                  
            Statement stmt = conn.createStatement();

            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS StockBook (isbn VARCHAR(13) PRIMARY KEY NOT NULL, quantite INT)");
            
            conn.close();
            System.out.println("Table StockBook Created");
            return ResponseEntity.ok("Table StockBook Created");
        } catch (SQLException e) {
            System.out.println("Erreur dans le création de la table");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Erreur dans le création de la table " + e.getMessage());
        }
    }
}
