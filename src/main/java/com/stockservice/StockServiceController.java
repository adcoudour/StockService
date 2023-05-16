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
 * @author ASUS
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
     * @param corr Id de corrélation composé de isbn et account
     * @return entier correspondant au nombre de livre pour l'isbn
     */
    @GetMapping("/isbn/{isbn}/corr/{corr}")
    public ResponseEntity<Object> getStockRequest(@PathVariable String isbn,@PathVariable String corr) { 
        StockBook stockbook = new StockBook();
        ResponseEntity<Object> resp = stockbook.getStockDataBase(isbn);
        return resp;
    }
    
    /**
     *
     * @param isbn
     * @param quantity
     * @return
     */
    @GetMapping("/add/isbn/{isbn}/quantity/{quantity}")
    public ResponseEntity<Object> AddStockRequest(@PathVariable String isbn,@PathVariable int quantity){
        StockBook stockbook = new StockBook();
        ResponseEntity<Object> resp = stockbook.addDataBase(isbn, quantity);
        return resp;
    }
    
    /**
     * 
     * @param isbn
     * @param quantity
     * @return 
     */
    @GetMapping("/remove/isbn/{isbn}/quantity/{quantity}")
    public ResponseEntity<Object> RemoveStockRequest(@PathVariable String isbn,@PathVariable int quantity){
        StockBook stockbook = new StockBook();
        ResponseEntity<Object> resp = stockbook.removeDataBase(isbn, quantity);
        return resp;
    }
}
