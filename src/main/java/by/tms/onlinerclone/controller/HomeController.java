package by.tms.onlinerclone.controller;

import by.tms.onlinerclone.entity.Good;
import by.tms.onlinerclone.entity.GoodCategory;
import by.tms.onlinerclone.service.GoodCategoryService;
import by.tms.onlinerclone.service.GoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Andrei Lisouski (Andrlis) 10/07/2023 - 12:11
 */
@Controller
@RequestMapping("/")
public class HomeController {

    @Autowired
    private GoodService goodService;

    @Autowired
    private GoodCategoryService goodCategoryService;

    @GetMapping
    public String showHomePage(Model model){
        List<GoodCategory> allCategories = goodCategoryService.findAll();
        List<Good> mostTrendingOffers = goodService.getMostTrendingGoods();

        model.addAttribute("categories", allCategories);
        model.addAttribute("topGoods", mostTrendingOffers);

        return "home";
    }
}
