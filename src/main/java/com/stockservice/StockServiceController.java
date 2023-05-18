/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.stockservice;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author adcoudour
 */
@RestController
public class StockServiceController {
    
    @GetMapping("/stock")
	public String index() { 
            return "stock";
	}   
    
    /**
    * Création de stockBook et appel de StockBook  pour créer la table dans la base de donnée
    * @return Une réponse de l'entité en fonction de la réussite où non de la création de la base de donnée 
    */
    @GetMapping("stock_service/create/database")
    public ResponseEntity<Object> CreateDataBaseRequest() { 
        StockBook stockbook = new StockBook();
        return stockbook.createDataBase();
    }   
    
    /**
     * Création de stockBook et appel de la méthode de StockBook  pour supprimer la table dans la base de donnée
     * @return Une réponse de l'entité en fonction de la réussite où non de la suppression de la base de donnée
     */
    @GetMapping("stock_service/drop/database")
    public ResponseEntity<Object> DropDataBaseRequest() { 
        StockBook stockbook = new StockBook();
        return stockbook.dropTableDataBase();
    }
        
    /**
     * Création de stockBook et appel de la méthode de StockBook  pour ajouter une ligne dans la table de la base de donnnée
     * @param isbn identifiant du livre
     * @return Une réponse de l'entité en fonction de la réussite où non d'insertion dans la table StockBook
     */
    @GetMapping("stock_service/new/book/{isbn}")
    public ResponseEntity<Object> newStockBookRequest(@PathVariable String isbn) { 
        StockBook stockbook = new StockBook();
        return stockbook.InsertDataBase(isbn,0);
    }
    /**
     * Cette méthode est appelé quand on a besoin de connaître le stock d'un livre.Une exception est levé si le livre récupéré est null.
     * @param isbn identifiant du livre
     * @return entier correspondant au nombre de livre pour l'isbn
     */
    @GetMapping("stock_service/get/book/{isbn}")
    public ResponseEntity<Object> getStockRequest(@PathVariable String isbn) { 
        StockBook stockbook = new StockBook();
        return stockbook.getStockDataBase(isbn);
    }
    
    /**
     *
     * @param isbn identifiant du livre
     * @param quantity quantité que l'on souhaite rajouter
     * @return Response de la requête
     */
    @GetMapping("stock_service/add/book/{isbn}/{quantity}")
    public ResponseEntity<Object> AddStockRequest(@PathVariable String isbn,@PathVariable int quantity){
        StockBook stockbook = new StockBook();
        return stockbook.addDataBase(isbn, quantity);
    }
    
    /**
     * 
     * @param isbn identifiant du livre
     * @param quantity quantité que l'on souhaite retirer 
     * @return Response de la requête
     */
    @GetMapping("stock_service/remove/book/{isbn}/{quantity}")
    public ResponseEntity<Object> RemoveStockRequest(@PathVariable String isbn,@PathVariable int quantity){
        StockBook stockbook = new StockBook();
        return stockbook.removeDataBase(isbn, quantity);
    }
}
