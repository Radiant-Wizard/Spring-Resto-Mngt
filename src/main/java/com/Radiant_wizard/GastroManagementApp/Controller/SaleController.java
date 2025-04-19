package com.Radiant_wizard.GastroManagementApp.Controller;

import com.Radiant_wizard.GastroManagementApp.Service.Sale.SaleServiceImpl;
import com.Radiant_wizard.GastroManagementApp.entity.model.Sale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
public class SaleController {
    @Autowired
    private SaleServiceImpl saleService;

    @GetMapping("/sales")
    public ResponseEntity<Object> getSale(@RequestParam LocalDateTime startTime,
                                          @RequestParam LocalDateTime endTime
    ) {
        try {
            List<Sale> bestSale = saleService.getSale(startTime, endTime);
            return ResponseEntity.ok(bestSale);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e);
        }
    }
}
