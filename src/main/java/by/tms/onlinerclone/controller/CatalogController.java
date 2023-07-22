package by.tms.onlinerclone.controller;

import by.tms.onlinerclone.dto.GoodShowerDto;
import by.tms.onlinerclone.dto.PageableGoodsShowerDto;
import by.tms.onlinerclone.entity.Good;
import by.tms.onlinerclone.entity.PageableGoods;
import by.tms.onlinerclone.mapper.GoodMapper;
import by.tms.onlinerclone.mapper.PageableGoodsMapper;
import by.tms.onlinerclone.service.GoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping("/catalog")
public class CatalogController {

    @Autowired
    private GoodService goodService;

    @GetMapping("/{categoryName}")
    public String searchOffersByCategory(@PathVariable String categoryName,
                                         @RequestParam(defaultValue = "1") int page,
                                         @RequestParam(defaultValue = "10") @Min(1) @Max(50) int size,
                                         Model model,
                                         HttpServletRequest request) {

        String currentUrl = getCurrentUrl(request);
        currentUrl.replace("page=" + page, "");
        model.addAttribute("url", currentUrl);

        Map<String, String[]> parameterMap = request.getParameterMap();

        Map<String, List<String>> charactersToSelect = goodService.findCharactersToSelect(categoryName);
        model.addAttribute("characters", charactersToSelect);

        PageableGoods pageableGoods;
        if (parameterMap.isEmpty()) {

            pageableGoods = goodService.findByCategoryNamePaginated(categoryName, page, size);
        } else {

            pageableGoods = goodService.findByCategoryNameAndByParameters(categoryName, page, size, parameterMap);
        }

        PageableGoodsShowerDto pageableGoodsShowerDto = PageableGoodsMapper.pageableGoodsToPageableGoodsShowDto(pageableGoods);

        model.addAttribute("goodList", pageableGoodsShowerDto.getGoodList());
        model.addAttribute("countOfPages", pageableGoodsShowerDto.getCountOfPages());
        model.addAttribute("page", page);

        return "category-searcher";
    }

    @GetMapping("/search")
    public String searchOffersByName(@RequestParam String offerName,
                                     @RequestParam(defaultValue = "1") int page,
                                     @RequestParam(defaultValue = "10") @Min(1) @Max(50) int size,
                                     Model model,
                                     HttpServletRequest request) {

        String currentUrl = getCurrentUrl(request);
        currentUrl.replace("page=" + page, "");
        model.addAttribute("url", currentUrl);

        PageableGoods pageableGoods = goodService.findBySimilarityInName(offerName, page, size);

        PageableGoodsShowerDto pageableGoodsShowerDto = PageableGoodsMapper.pageableGoodsToPageableGoodsShowDto(pageableGoods);
        model.addAttribute("goodList", pageableGoodsShowerDto.getGoodList());
        model.addAttribute("countOfPages", pageableGoodsShowerDto.getCountOfPages());
        model.addAttribute("page", page);

        return "name-searcher";
    }

    private String getCurrentUrl(HttpServletRequest request){
        StringBuffer requestURL = request.getRequestURL();
        if (request.getQueryString() != null) {
            requestURL.append("?").append(request.getQueryString());
        }
        return requestURL.toString();
    }
}
