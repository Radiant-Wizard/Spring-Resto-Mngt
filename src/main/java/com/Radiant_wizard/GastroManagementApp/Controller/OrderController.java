package com.Radiant_wizard.GastroManagementApp.Controller;

import com.Radiant_wizard.GastroManagementApp.Service.Order.OrderService;
import com.Radiant_wizard.GastroManagementApp.Service.Order.OrderServiceImpl;
import com.Radiant_wizard.GastroManagementApp.Service.Status.DishStatusServiceImpl;
import com.Radiant_wizard.GastroManagementApp.entity.DTO.dish.OrderDish;
import com.Radiant_wizard.GastroManagementApp.entity.DTO.order.OrderDto;
import com.Radiant_wizard.GastroManagementApp.entity.Enum.StatusType;
import com.Radiant_wizard.GastroManagementApp.entity.model.DishOrder;
import com.Radiant_wizard.GastroManagementApp.entity.model.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private DishStatusServiceImpl dishStatusService;


    @GetMapping("/{reference}")
    public ResponseEntity<Object> getOrderByRef(@PathVariable ("reference") String reference ){
        OrderDto order = orderService.getOrderByRef(reference);
        return ResponseEntity.ok(order);
    }

    @GetMapping("/")
    public ResponseEntity<Object> getAll(@RequestParam(required = false, defaultValue = "10") Integer pageSize,
                                         @RequestParam(required = false, defaultValue = "1") Integer pageNumber
                                         ){
        try {
            List<Order> orders = orderService.getAll(pageSize, pageNumber);
            return ResponseEntity.ok(orders);
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e);
        }
    }

    @PutMapping("/{reference}/dishes")
    public ResponseEntity<Object> addDishes(@PathVariable("reference") String reference, @RequestBody List<OrderDish> dishOrders){
        try {
            OrderDto orderDto = orderService.addDishesToOrder(dishOrders, reference);
            return ResponseEntity.ok(orderDto);
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e);
        }
    }

    @PutMapping("/{reference}/dishes/{dishId}")
    public ResponseEntity<Object> updateDishStatus(@PathVariable("reference") String reference, @PathVariable("dishId") long dishId, @RequestBody StatusType status){
        try {
            OrderDto orderDto = dishStatusService.updateDishStatus(status, dishId, reference);
            return ResponseEntity.ok(orderDto);
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e);
        }
    }


}
