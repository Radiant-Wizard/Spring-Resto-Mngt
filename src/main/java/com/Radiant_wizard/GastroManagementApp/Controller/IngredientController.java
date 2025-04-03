package com.Radiant_wizard.GastroManagementApp.Controller;

import com.Radiant_wizard.GastroManagementApp.Service.ingredientService.IngredientServiceImpl;
import com.Radiant_wizard.GastroManagementApp.entity.model.Criteria;
import com.Radiant_wizard.GastroManagementApp.entity.Enum.LogicalOperator;
import com.Radiant_wizard.GastroManagementApp.entity.model.Ingredient;
import com.Radiant_wizard.GastroManagementApp.entity.model.IngredientResponseEntity;
import jakarta.websocket.server.PathParam;
import jdk.jshell.Snippet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/ingredient")
public class IngredientController {
    @Autowired
    IngredientServiceImpl ingredientService;

    @GetMapping("/all")
    public ResponseEntity<Object> getByCriteria(
            @RequestParam(required = false, defaultValue = "0") Double priceMin,
            @RequestParam(required = false) Double priceMax,
            @RequestParam(required = false) String ingredientName,
            @RequestParam(required = false) Double unitPrice,
            @RequestParam(required = false) String operator,
            @RequestParam(required = false) String orderBy,
            @RequestParam(required = false, defaultValue = "true") Boolean ascending,
            @RequestParam(required = false, defaultValue = "10") Integer pageSize,
            @RequestParam(required = false, defaultValue = "1") Integer pageNumber
    ) throws SQLException {
        try {
            List<IngredientResponseEntity> ingredientResponseEntities =
                    ingredientService.getIngredientsByCriteria(priceMin, priceMax, ingredientName, unitPrice, operator, orderBy, ascending, pageSize, pageNumber);
            return ResponseEntity.ok(ingredientResponseEntities);
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e);
        }
    }


    @GetMapping("/ingredients/{id}")
    public ResponseEntity<Object> getById(@PathVariable("id") long ingredientId) {
        try {
            IngredientResponseEntity ingredientResponseEntity = ingredientService.getIngredientById(ingredientId);
            return ResponseEntity.ok(ingredientResponseEntity);
        } catch (NoSuchFieldException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }



}
