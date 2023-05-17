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
            // Si le livre ne possède pas de ligne de stock on la crée.
            if (rs == null){
                // A voir si on le laisse
                InsertDataBase(isbn, 0);
            }else 
            if (rs.next()) {
                stock = rs.getInt(1);
            }
            conn.close();
            return ResponseEntity.ok(stock);
        } catch (SQLException ex) {
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
            PreparedStatement stmt = conn.prepareStatement("UPDATE StockBook SET quantite = quantite + ? WHERE isbn=?");
            stmt.setInt(1, quantity);
            stmt.setString(2, isbn);
            stmt.executeUpdate();
            conn.close();
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.ok("Wholesaler order sent");
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
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.ok("Wholesaler order sent");
    }
    
    /**
     * Insertion dans la base de donnée d'une nouvelle information
     * @param isbn identifiant du livre
     * @param quantite quantité que l'on souhaite pour initialiser par défaut 0
     * @return état de la statut
     */
    public String InsertDataBase(String isbn,int quantite){
        try {
            Connection conn = new ConnectionManager().getConnection();
            PreparedStatement insert = conn.prepareStatement("INSERT INTO StockBook (quantite,isbn) VALUES (?,?)");
            insert.setInt(1, quantite);
            insert.setString(2, isbn);
            
            conn.close();
            return "Insert";
            } catch (SQLException e) {
            
            return "There was an error: " + e.getMessage();
        }
    }
    
    /**
     * Permet de supprimer la table de stockBook
     * @return état de la fonction
     */
    public String dropTableDataBase(){
        try {
            Connection conn = new ConnectionManager().getConnection();

            Statement stmt = conn.createStatement();
            stmt.executeUpdate("DROP TABLE IF EXISTS StockBook");
            
            conn.close();
            return "Table StockBook Dropped";
            } catch (SQLException e) {
            
            return "There was an error: " + e.getMessage();
        }
    }
    
    /**
     * Création de la base de donnée stockBook
     * @return l'état de la fonction si réusssite où non.
     */
    public String createDataBase(){
        try {
            Connection conn = new ConnectionManager().getConnection();
                  
            Statement stmt = conn.createStatement();

            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS StockBook (id INT AUTO_INCREMENT PRIMARY KEY,quantite INT, isbn VARCHAR(13))");
            
            conn.close();
            return "ajout de StockBook a la base!";
        } catch (SQLException e) {
            
          return "There was an error: " + e.getMessage();
        }
    }
}
