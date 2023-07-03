package by.tms.onlinerclone.controller;

import by.tms.onlinerclone.entity.Good;
import by.tms.onlinerclone.entity.GoodCharacters;
import by.tms.onlinerclone.service.GoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/catalog")
public class CatalogController {

    @Autowired
    private GoodService goodService;

    @GetMapping
    public String main(){
        return "main";
    }

    @GetMapping("/{categoryId}")
    public String category(@PathVariable Long categoryId,
                           Model model,
                           @RequestParam(defaultValue = "1") int page,
                           @RequestParam(defaultValue = "10") @Min(1) @Max(50) int size){

        Map<String, List<String>> charactersToSelect = goodService.findCharactersToSelect(categoryId);
        model.addAttribute("characters", charactersToSelect);

        List<Good> byCategory = goodService.findByCategoryId(categoryId);

        model.addAttribute("goodsList", byCategory);
        return "catalog-searcher";
    }
}
