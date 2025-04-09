package com.Radiant_wizard.GastroManagementApp.Controller;

import com.Radiant_wizard.GastroManagementApp.Service.Order.OrderService;
import com.Radiant_wizard.GastroManagementApp.Service.Order.OrderServiceImpl;
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
    @GetMapping("/{reference}")
    public ResponseEntity<Object> getOrderByRef(@PathVariable ("reference") String reference ){
        Order order = orderService.getOrderByRef(reference);
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

}
