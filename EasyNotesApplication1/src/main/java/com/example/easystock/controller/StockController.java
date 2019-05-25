package com.example.easystock.controller;

import com.example.easystock.exception.ResourceStockFoundException;
import com.example.easystock.model.Stock;
import com.example.easystock.repository.StockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
public class StockController {

    @Autowired
    StockRepository stockRepository;

    // Get All Stock
    @GetMapping("/stock")
    public List<Stock> getAllStock() {
        return stockRepository.findAll();
    }
    // Create a new Stock
    @PostMapping("/stock")
    public Stock createStock(@Valid @RequestBody Stock stock) {
        return stockRepository.save(stock);
    }
    // Get a Single Stock
    @GetMapping("/stock/{id}")
    public Stock getStockById(@PathVariable(value = "id") Long stockId) {
        return stockRepository.findById(stockId)
                .orElseThrow(() -> new ResourceStockFoundException("Stock", "id", stockId));
    }
    // Update a Stock
    @PutMapping("/stock/{id}")
    public Stock updateStock(@PathVariable(value = "id") Long stockId,
                                            @Valid @RequestBody Stock stockDetails) {

        Stock stock = stockRepository.findById(stockId)
                .orElseThrow(() -> new ResourceStockFoundException("Stock", "id", stockId));

        stock.setPname(stockDetails.getPname());
        stock.setPrice(stockDetails.getPrice());
        stock.setQuantity(stockDetails.getQuantity());
        stock.setDes(stockDetails.getDes());
        
        
        Stock updatedStock = stockRepository.save(stock);
        return updatedStock;
    }
    // Delete a Stock
    @DeleteMapping("/stock/{id}")
    public ResponseEntity<?> deleteStock(@PathVariable(value = "id") Long stockId) {
        Stock stock = stockRepository.findById(stockId)
                .orElseThrow(() -> new ResourceStockFoundException("Stock", "id", stockId));

        stockRepository.delete(stock);

        return ResponseEntity.ok().build();
    }
}