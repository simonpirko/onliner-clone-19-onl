package by.tms.onlinerclone.controller;

import by.tms.onlinerclone.entity.Good;
import by.tms.onlinerclone.entity.Order;
import by.tms.onlinerclone.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping
    public ResponseEntity<Order> createOrder(@RequestBody Order order) {
        orderService.save(order);
        return ResponseEntity.ok(order);
    }

    @GetMapping("/order")
    public ResponseEntity<List<Order>> findByUser(@PathVariable long id) {
        List<Order> orderList = orderService.findByUser(id);
        return ResponseEntity.ok(orderList);
    }


    @GetMapping("/order/{orderId}")
    public ResponseEntity<Order> findById(@PathVariable long orderId) {
        Optional<Order> byId = orderService.findById(orderId);
        if (byId.isPresent()) {
            Order order = byId.get();
            return ResponseEntity.ok(order);
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{orderId}")
    public ResponseEntity<Boolean> addGood(@PathVariable("orderId") long orderId, @RequestBody Good good) {
        boolean addedGood = orderService.addGood(orderId, good);
        if (addedGood) {
            return ResponseEntity.ok(addedGood);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @DeleteMapping("/{orderId}")
    public ResponseEntity<Void> deleteOrder(@PathVariable("orderId") long orderId) {
        Optional<Order> order = orderService.findById(orderId);
        if (order.isPresent()) {
            Order existingOrder = order.get();
            orderService.delete(existingOrder);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }


}
