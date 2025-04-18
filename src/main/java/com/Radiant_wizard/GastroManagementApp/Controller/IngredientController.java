package com.Radiant_wizard.GastroManagementApp.Controller;

import com.Radiant_wizard.GastroManagementApp.Service.Ingredient.IngredientServiceImpl;
import com.Radiant_wizard.GastroManagementApp.entity.DTO.ingredient.Ingredient;
import com.Radiant_wizard.GastroManagementApp.entity.DTO.stockMovement.StockMovementDto;
import com.Radiant_wizard.GastroManagementApp.entity.model.Price;
import com.Radiant_wizard.GastroManagementApp.entity.model.StockMovement;
import com.Radiant_wizard.GastroManagementApp.repository.ingredient.IngredientDaoImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/ingredients")
public class IngredientController {
    @Autowired
    IngredientDaoImpl ingredientDao;
    @Autowired
    IngredientServiceImpl ingredientService;

    @GetMapping("/")
    public ResponseEntity<Object> getByCriteria(
            @RequestParam(required = false, defaultValue = "0") Double priceMin,
            @RequestParam(required = false) Double priceMax,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Double unitPrice,
            @RequestParam(required = false) String operator,
            @RequestParam(required = false) String orderBy,
            @RequestParam(required = false, defaultValue = "true") Boolean ascending,
            @RequestParam(required = false, defaultValue = "10") Integer pageSize,
            @RequestParam(required = false, defaultValue = "1") Integer pageNumber
    ){
        try {
            List<Ingredient> ingredientResponseEntities =
                    ingredientService.getIngredientsByCriteria(priceMin, priceMax, name, unitPrice, operator, orderBy, ascending, pageSize, pageNumber);
            return ResponseEntity.ok(ingredientResponseEntities);
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getById(@PathVariable("id") long ingredientId) {
        try {
            Ingredient ingredient = ingredientService.getIngredientById(ingredientId);
            return ResponseEntity.ok(ingredient);
        } catch (NoSuchFieldException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
    @GetMapping("/{id}/stockMovements")
    public ResponseEntity<Object> getStock(@PathVariable("id") long ingredientId) {
        try {
            List<StockMovement> stockMovements =  ingredientDao.getIngredientById(ingredientId).getStockMovements();
            return ResponseEntity.ok(stockMovements);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }


    @PutMapping("/{id}/price")
    public ResponseEntity<Object> updatePrice(@PathVariable("id") long ingredientId, @RequestBody List<Price> prices){
        try {
            Ingredient ingredient = ingredientService.updatePrice(prices, ingredientId);

            return ResponseEntity.ok(ingredient);
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error has occurred : " + e);
        }
    }

    @PutMapping("/{id}/stockMovements")
    public ResponseEntity<Object> updateStock(@PathVariable("id") long ingredientId,
                                              @RequestBody List<StockMovementDto> stockMovements){
        try {
            ingredientService.addStockMovement(stockMovements, ingredientId);
            return ResponseEntity.ok("updated");
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error has occurred : " + e);
        }
    }
}
