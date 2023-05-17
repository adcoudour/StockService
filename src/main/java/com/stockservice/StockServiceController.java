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
		return "Stock";
	}
        
    /**
     * Cette méthode est appelé quand on a besoin de connaître le stock d'un livre.Une exception est levé si le livre récupéré est null.
     * @param isbn identifiant du livre
     * @return entier correspondant au nombre de livre pour l'isbn
     */
    @GetMapping("/get/{isbn}")
    public ResponseEntity<Object> getStockRequest(@PathVariable String isbn) { 
        StockBook stockbook = new StockBook();
        return stockbook.getStockDataBase(isbn);
    }
    
    /**
     *
     * @param isbn
     * @param quantity
     * @return
     */
    @GetMapping("/add/{isbn}/{quantity}")
    public ResponseEntity<Object> AddStockRequest(@PathVariable String isbn,@PathVariable int quantity){
        StockBook stockbook = new StockBook();
        return stockbook.addDataBase(isbn, quantity);
    }
    
    /**
     * 
     * @param isbn
     * @param quantity
     * @return 
     */
    @GetMapping("/remove/{isbn}/{quantity}")
    public ResponseEntity<Object> RemoveStockRequest(@PathVariable String isbn,@PathVariable int quantity){
        StockBook stockbook = new StockBook();
        return stockbook.removeDataBase(isbn, quantity);
    }
}
