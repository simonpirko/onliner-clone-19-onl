package by.tms.onlinerclone.controller;

import by.tms.onlinerclone.entity.Good;
import by.tms.onlinerclone.entity.GoodCharacters;
import by.tms.onlinerclone.service.GoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.net.URI;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/catalog")
public class CatalogController {

    @Autowired
    private GoodService goodService;

    @GetMapping("/{categoryId}")
    public String category(@PathVariable Long categoryId,
                           Model model,
                           @RequestParam(defaultValue = "1") int page,
                           @RequestParam(defaultValue = "10") @Min(1) @Max(50) int size,
                           HttpServletRequest request){

        model.addAttribute("uri", request.getRequestURI());
        Map<String, String[]> parameterMap = request.getParameterMap();

        Map<String, List<String>> charactersToSelect = goodService.findCharactersToSelect(categoryId);
        model.addAttribute("characters", charactersToSelect);

        List<Good> goodList;
        if (parameterMap.isEmpty()) {
            goodList = goodService.findByCategoryIdPaginated(categoryId, page, size);
        } else {
            goodList = goodService.findByCategoryIdAndByParameters(categoryId, page , size, parameterMap);
        }

        model.addAttribute("goodsList", goodList);
        return "catalog-searcher";
    }
}
