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
    
    public ResponseEntity<Object> showDataBase(){
        //try {
        //    Connection conn = new ConnectionManager().getConnection();

        //    Statement stmt = conn.createStatement("SELECT * from StockBook");
        //    return stmt;
            
        //    conn.close();
        //    return "Table StockBook Dropped";
        //    } catch (SQLException e) {
            
        //    return "There was an error: " + e.getMessage();
        //}
        return null;
    }
    
    /**
     * Récupère la quantité en fonction d'un livre
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
            }
            conn.close();
             System.out.println("Quantité pour isbn : "+isbn +" est de : "+ stock);
            return ResponseEntity.ok(stock);
        } catch (SQLException ex) {
             System.out.println("Problème erreur retourné" + ex.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage()+isbn);
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
            Connection conn = new ConnectionManager().getConnection();
            PreparedStatement stmt = conn.prepareStatement("UPDATE StockBook SET quantite = quantite + ? WHERE isbn = ?");
            stmt.setInt(1, quantity);
            stmt.setString(2, isbn);
            stmt.executeUpdate();
            conn.close();
            System.out.println("WholeSaler Order Sent");
            return ResponseEntity.ok("Wholesaler order sent");
        } catch (SQLException e) {
            System.out.println("Problème dans order sent");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
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
            Connection conn = new ConnectionManager().getConnection();
            PreparedStatement stmt = conn.prepareStatement("UPDATE StockBook SET quantite = quantite - ? WHERE isbn=?");
            stmt.setInt(1, quantity);
            stmt.setString(2, isbn);
            stmt.executeUpdate();
            conn.close();
            System.out.println("Retrait de "+quantity + " pour le livre : "+isbn);
            return ResponseEntity.ok("Retrait de "+quantity + " pour le livre : "+isbn);
        } catch (SQLException e) {
            System.out.println("Erreur pour le retrait");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
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
            Connection conn = new ConnectionManager().getConnection();
            PreparedStatement insert = conn.prepareStatement("INSERT INTO StockBook(isbn,quantite) VALUES (?,?)");
            insert.setString(1, isbn);
            insert.setInt(2, quantite);
            insert.executeQuery();
            conn.close();
            System.out.println("Insertion d'un ligne dans la table StockBook");
            return ResponseEntity.ok("ajout de la ligne pour le livre : "+isbn);
            } catch (SQLException e) {
            System.out.println("Erreur dans l'ajout de la ligne");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("There was an error: " + e.getMessage());
        }
    }
    
    /**
     * Permet de supprimer la table de stockBook
     * @return état de la fonction
     */
    public ResponseEntity<Object> dropTableDataBase(){
        try {
            Connection conn = new ConnectionManager().getConnection();

            Statement stmt = conn.createStatement();
            stmt.executeUpdate("DROP TABLE IF EXISTS StockBook");
            
            conn.close();
            return ResponseEntity.ok("Table StockBook Dropped");
            } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Erreur dans le dropDataBase " + e.getMessage());
        }
    }
    
    /**
     * Création de la base de donnée stockBook
     * @return l'état de la fonction si réusssite où non.
     */
    public ResponseEntity<Object> createDataBase(){
        try {
            Connection conn = new ConnectionManager().getConnection();
                  
            Statement stmt = conn.createStatement();

            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS StockBook (isbn VARCHAR(13) PRIMARY KEY NOT NULL, quantite INT)");
            
            conn.close();
            return ResponseEntity.ok("Table StockBook Created");
        } catch (SQLException e) {
            
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Erreur dans le création de la table " + e.getMessage());
        }
    }
}
