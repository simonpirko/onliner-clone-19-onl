package by.tms.onlinerclone.controller;

import by.tms.onlinerclone.entity.Order;
import by.tms.onlinerclone.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping
    public String createOrder(@ModelAttribute Order order) {
        orderService.save(order);
        return "order";
    }

    @GetMapping("/user/{username}")
    public String findByUser(@PathVariable String username,
                             Model model,
                             @RequestParam(defaultValue = "1") int page,
                             @RequestParam(defaultValue = "5") @Min(1) @Max(10) int size) {
        List<Order> orderList = orderService.findPaginatedByUsername(username, page, size);
        model.addAttribute("orders", orderList);
        return "orderList";
    }


    @GetMapping("/{orderId}")
    public String findById(@PathVariable long orderId, Model model) {
        Optional<Order> byId = orderService.findById(orderId);
        if (byId.isPresent()) {
            Order order = byId.get();
            model.addAttribute("order", order);
            return "order";
        }
        model.addAttribute("orderNotFound");
        return "orderNotFound";
    }


    @PostMapping("/{orderId}")
    public String deleteOrder(@PathVariable long orderId) {
        Optional<Order> order = orderService.findById(orderId);
        if (order.isPresent()) {
            Order existingOrder = order.get();
            orderService.delete(existingOrder);
            return "orderList";
        } else {
            return "orderNotFound";
        }
    }

}
