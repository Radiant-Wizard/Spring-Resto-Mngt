package com.Radiant_wizard.AttendanceRecordApp;

import com.Radiant_wizard.GastroManagementApp.GastroManagementApp;
import com.Radiant_wizard.GastroManagementApp.configuration.Datasource;
import com.Radiant_wizard.GastroManagementApp.entity.Enum.LogicalOperator;
import com.Radiant_wizard.GastroManagementApp.entity.Enum.Unit;
import com.Radiant_wizard.GastroManagementApp.entity.model.Criteria;
import com.Radiant_wizard.GastroManagementApp.entity.model.Dish;
import com.Radiant_wizard.GastroManagementApp.entity.model.Ingredient;
import com.Radiant_wizard.GastroManagementApp.entity.model.Price;
import com.Radiant_wizard.GastroManagementApp.repository.dish.DishesDaoImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(classes = GastroManagementApp.class)
public class DishDaoImplTest {
    @Autowired
    Datasource datasource;
    @Autowired
    DishesDaoImpl dishesDao;


    @Test
    public void testGetDishes() throws SQLException, IllegalAccessException {
        Dish dish = dishesDao.getDishesById(1);
        assertEquals(5500, dish.getTotalCostIngredient(LocalDateTime.now()));
    }

    @Test
    public void testCreateDishes() throws SQLException, IllegalAccessException {
        List<Ingredient> ingredients = List.of(
                new Ingredient(1L, "tomato", Unit.G, List.of(new Price(LocalDateTime.now(), 20.0)), 12.0),
                new Ingredient(2L, "Chicken Breast", Unit.G, List.of(new Price(LocalDateTime.now(), 100.0)), 250.0),
                new Ingredient(3L, "Lettuce", Unit.G, List.of(new Price(LocalDateTime.now(), 30.0)), 100.0)
        );
        // Prepare a dish
        Dish dish = new Dish(2L, "Burger", 5000, ingredients);

        // Create the dish in the database
        dishesDao.saveDishes(List.of(dish));

        // Verify the dish was correctly inserted into the database
        Dish retrievedDish = dishesDao.getDishesById(2);
        assertNotNull(retrievedDish);
        assertEquals(2, retrievedDish.getDishId());
        assertEquals("Burger", retrievedDish.getDishName());
        assertEquals(5000, retrievedDish.getPrice());
    }

    @Test
    public void testGetAvailable() throws SQLException, IllegalAccessException {
        LocalDateTime testDate = LocalDateTime.of(2025, 2, 24, 12, 0);

        Dish hotdog = dishesDao.getDishesById(1);

        assertEquals(30, hotdog.getAvailableQuantity(testDate));
        assertEquals(30, hotdog.getAvailableQuantity());
    }
    @Test
    @DisplayName("test if the getDishByCriteria work fine")
    public void testGetByCriteria() throws SQLException {
        List<Criteria> criteriaList = new ArrayList<>();
        criteriaList.add(new Criteria("dish_name", "BURGER", "LIKE", LogicalOperator.AND));
//        criteriaList.add(new Criteria("dish_price", 8000, "=", LogicalOperator.AND));
        List<Dish> dishes = dishesDao.getDishesByCriteria(criteriaList, "dish_name", true, 2, 1);


        assertEquals(1, dishes.size());
    }


}
