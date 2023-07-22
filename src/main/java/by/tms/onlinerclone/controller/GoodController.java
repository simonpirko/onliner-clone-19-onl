package by.tms.onlinerclone.controller;

import by.tms.onlinerclone.dto.GoodShowerDto;
import by.tms.onlinerclone.entity.Good;
import by.tms.onlinerclone.mapper.GoodMapper;
import by.tms.onlinerclone.service.GoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
@RequestMapping("/item")
public class GoodController {

    @Autowired
    private GoodService goodService;

    @GetMapping("/item/{id}")
    public String viewGoodById(@PathVariable long id, Model model){
        Optional<Good> goodOptional = goodService.findByID(id);

        if (goodOptional.isPresent()){
            GoodShowerDto goodShowerDto = GoodMapper.goodToGoodShowerDto(goodOptional.get());
            model.addAttribute("good", goodShowerDto);
            return "good-details";
        }

        return "good-not-found";
    }

    @GetMapping("/item/{id}/offers")
    public String viewGoodOffers(@PathVariable long id, Model model){
        Optional<Good> goodOptional = goodService.findByID(id);

        if (goodOptional.isPresent()){
            GoodShowerDto goodShowerDto = GoodMapper.goodToGoodShowerDto(goodOptional.get());
            model.addAttribute("good", goodShowerDto);
            return "good-details";
        }

        return "good-not-found";
    }
}
