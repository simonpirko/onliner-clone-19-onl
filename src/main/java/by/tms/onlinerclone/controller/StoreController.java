package by.tms.onlinerclone.controller;

import by.tms.onlinerclone.dto.GoodCreatorDto;
import by.tms.onlinerclone.dto.RegStoreDto;
import by.tms.onlinerclone.entity.Good;
import by.tms.onlinerclone.entity.PageableGoods;
import by.tms.onlinerclone.entity.SessionUser;
import by.tms.onlinerclone.service.StoreService;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.Optional;

@Controller
@RequestMapping("/store")
public class StoreController {

    @Autowired
    private StoreService storeService;

    @GetMapping("/create")
    public String reg(Model model) {
        model.addAttribute("newStore", new RegStoreDto());
        return "create-store";
    }

    @PostMapping("/create")
    public String reg(@ModelAttribute("newStore") @Valid RegStoreDto regStoreDto,
                      BindingResult bindingResult,
                      HttpSession httpSession,
                      Model model) {

        if (bindingResult.hasErrors()) return "create-store";

        SessionUser sessionUser = (SessionUser) httpSession.getAttribute("sessionUser");

        if (sessionUser != null) {

            regStoreDto.setUser(sessionUser);

            try {

                storeService.save(regStoreDto);
            } catch (ConstraintViolationException e) {

                model.addAttribute("createError", "The store with this name already exists");
                return "create-store";
            }

            return "redirect:/store/manage";
        }

        return "redirect:/user/login";
    }

    @GetMapping("/{storeName}/offers")
    public String allOffers(@PathVariable String storeName,
                         @RequestParam(defaultValue = "1") int page,
                         @RequestParam(defaultValue = "10") @Min(1) @Max(50) int size,
                         Model model,
                         HttpServletRequest request){

        PageableGoods pageableGoods = storeService.findPaginatedOffers(storeName, page, size);

        model.addAttribute("url", request.getRequestURL());
        model.addAttribute("goodList", pageableGoods.getGoodList());
        model.addAttribute("countOfPages", pageableGoods.getCountOfPages());
        model.addAttribute("page", page);

        return "store-offers";
    }

    @GetMapping("/{storeName}/create-offer")
    public String addOfferForm(@PathVariable String storeName,
                               Model model){
        model.addAttribute("newOffer", new GoodCreatorDto());
        model.addAttribute("storeName", storeName);
        return "create-offer";
    }

    @PostMapping("/{storeName}/create-offer")
    public String addOffer(@PathVariable String storeName,
                           @ModelAttribute("newOffer") @Valid GoodCreatorDto goodCreator,
                           BindingResult bindingResult,
                           @RequestParam("files") MultipartFile[] files,
                           Model model){

        if (bindingResult.hasErrors()) return "create-offer";

        if (!storeService.addOffer(storeName, goodCreator)) {

            model.addAttribute("createOfferError", "This offer did not created, try again");
        }

        return "redirect:/store/" + storeName + "/offers";
    }

    @GetMapping("/{storeName}/update-offer")
    public String update(@PathVariable String storeName,
                         @RequestParam String offerName,
                         Model model){

        model.addAttribute("storeName", storeName);
        Optional<Good> offerByName = storeService.findOfferByName(storeName, offerName);

        if (offerByName.isPresent()){

            model.addAttribute("offer", offerByName.get());
        } else {

            model.addAttribute("offerNotFound", "Offer with this name not found!");
        }

        return "update-offer";
    }

    @PostMapping("/{storeName}/update-offer")
    public String updateOffer(@PathVariable String storeName,
                              @ModelAttribute(name = "offer") Good good,
                              Model model){


        if (storeService.updateOffer(storeName, good)) {

            return "redirect:/store/" + storeName + "/offer?name=" + good.getName();
        }

        model.addAttribute("offerNotFound", "Offer with this name not found!");
        return "update-offer";
    }
}
