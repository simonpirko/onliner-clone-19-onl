package by.tms.onlinerclone.controller;

import by.tms.onlinerclone.entity.Order;
import by.tms.onlinerclone.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
        return "redirect:/order";
    }

    @GetMapping
    public String findByUser(@PathVariable long id, Model model) {
        List<Order> orderList = orderService.findByUser(id);
        model.addAttribute("orders", orderList);
        return "orderList";
    }


    @GetMapping("/{orderId}")
    public String findById(@PathVariable long orderId, Model model) {
        Optional<Order> byId = orderService.findById(orderId);
        if (byId.isPresent()) {
            Order order = byId.get();
            model.addAttribute("order", order);
            return "/order";
        }
        model.addAttribute("orderNotFound", "Order not found!");
        return "orderNotFound";
    }

    @DeleteMapping("/{orderId}")
    public String deleteOrder(@PathVariable long orderId, Model model) {
        Optional<Order> order = orderService.findById(orderId);
        if (order.isPresent()) {
            Order existingOrder = order.get();
            orderService.delete(existingOrder);
            return "/order";
        } else {
            model.addAttribute("deleteError", "An error occurred while deleting the order!");
            return "deleteError";
        }
    }

}
