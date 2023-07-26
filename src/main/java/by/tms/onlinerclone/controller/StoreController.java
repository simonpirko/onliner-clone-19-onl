package by.tms.onlinerclone.controller;

import by.tms.onlinerclone.dto.GoodCreatorDto;
import by.tms.onlinerclone.dto.PageableGoodsShowerDto;
import by.tms.onlinerclone.dto.RegStoreDto;
import by.tms.onlinerclone.dto.StoreShowerDto;
import by.tms.onlinerclone.entity.*;
import by.tms.onlinerclone.mapper.PageableGoodsMapper;
import by.tms.onlinerclone.mapper.StoreMapper;
import by.tms.onlinerclone.service.StoreService;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.Optional;
import java.util.Set;

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

            return "redirect:/store/" + regStoreDto.getName() + "/manage";
        }

        return "redirect:/user/login";
    }

    @GetMapping("/{storeName}")
    public String storePage(@PathVariable String storeName,
                            HttpSession httpSession,
                            Model model) {

        Optional<Store> byName = storeService.findByName(storeName);
        SessionUser sessionUser = (SessionUser) httpSession.getAttribute("sessionUser");

        if (byName.isPresent()) {

            StoreShowerDto storeShowerDto = StoreMapper.storeToStoreShowerDto(byName.get());
            model.addAttribute("store", storeShowerDto);

            if (sessionUser != null) {

                boolean isAdministrator = false;

                for (User administrator : byName.get().getAdministrators()) {

                    if (administrator.getId().equals(sessionUser.getId())) {

                        model.addAttribute("isAdministrator", isAdministrator);
                    }
                }
            }

            return "store-page";
        }

        model.addAttribute("storeNotFound", "Store with this name not found");
        return "store-page";
    }

    @GetMapping("/{storeName}/offers")
    public String allOffers(@PathVariable String storeName,
                            @RequestParam(defaultValue = "1") int page,
                            @RequestParam(defaultValue = "10") @Min(1) @Max(50) int size,
                            Model model,
                            HttpServletRequest request) {

        PageableGoods pageableGoods = storeService.findPaginatedOffers(storeName, page, size);

        PageableGoodsShowerDto pageableGoodsShowerDto = PageableGoodsMapper.pageableGoodsToPageableGoodsShowDto(pageableGoods);

        model.addAttribute("url", request.getRequestURL());
        model.addAttribute("goodList", pageableGoodsShowerDto.getGoodList());
        model.addAttribute("countOfPages", pageableGoodsShowerDto.getCountOfPages());
        model.addAttribute("page", page);

        return "store-offers";
    }

    @GetMapping("/{storeName}/create-offer")
    public String addOfferForm(@PathVariable String storeName,
                               Model model) {
        GoodCreatorDto goodCreatorDto = new GoodCreatorDto();
        Set<GoodCharacters> characters = goodCreatorDto.getCharacters();

        for (int i = 0; i < 10; i++) {
            characters.add(new GoodCharacters());
        }
        goodCreatorDto.setCharacters(characters);

        model.addAttribute("newOffer", goodCreatorDto);
        model.addAttribute("storeName", storeName);
        return "create-offer";
    }

    @PostMapping("/{storeName}/create-offer")
    public String addOffer(@PathVariable String storeName,
                           @ModelAttribute("newOffer") @Valid GoodCreatorDto goodCreator,
                           BindingResult bindingResult,
                           Model model) {

        if (bindingResult.hasErrors()) return "create-offer";

        if (!storeService.addOffer(storeName, goodCreator)) {

            model.addAttribute("createOfferError", "This offer did not created, try again");
        }

        return "redirect:/store/" + storeName + "/offers";
    }

    @GetMapping("/{storeName}/update-offer")
    public String update(@PathVariable String storeName,
                         @RequestParam String offerName,
                         Model model) {

        model.addAttribute("storeName", storeName);
        Optional<Good> offerByName = storeService.findOfferByName(storeName, offerName);

        if (offerByName.isPresent()) {

            model.addAttribute("offer", offerByName.get());
        } else {

            model.addAttribute("offerNotFound", "Offer with this name not found!");
        }

        return "update-offer";
    }

    @PostMapping("/{storeName}/update-offer")
    public String updateOffer(@PathVariable String storeName,
                              @ModelAttribute(name = "offer") Good good,
                              Model model) {


        if (storeService.updateOffer(storeName, good)) {

            return "redirect:/store/" + storeName + "/offer?name=" + good.getName();
        }

        model.addAttribute("offerNotFound", "Offer with this name not found!");
        return "update-offer";
    }

    @GetMapping("/{storeName}/manage")
    public String manageView(@PathVariable String storeName,
                             HttpSession httpSession,
                             Model model) {

        model.addAttribute("storeName", storeName);
        SessionUser sessionUser = (SessionUser) httpSession.getAttribute("sessionUser");
        Optional<Store> byName = storeService.findByName(storeName);

        if (byName.isPresent()) {

            Store store = byName.get();
            model.addAttribute("store", store);

            if (sessionUser != null) {

                boolean isSuperAdmin = store.getSuperAdmin().getId().equals(sessionUser.getId());

                model.addAttribute("isSuperAdmin", isSuperAdmin);
            }

        } else {

            model.addAttribute("storeNotFound", "Store with this name not found!");
        }

        return "manage-store";
    }

    @PostMapping("/{storeName}/manage")
    public String manage(@PathVariable String storeName,
                         @ModelAttribute(name = "store") Store store,
                         Model model) {

        storeService.update(store);
        return "redirect:/store/" + store.getName();
    }

    @PostMapping("/{storeName}/manage/delete-admin")
    public String deleteAdmin(@PathVariable String storeName,
                              @RequestParam(name = "id") Long id,
                              Model model) {

        if (storeService.deleteAdmin(storeName, id)) {

            return "manage-store";
        }

        model.addAttribute("deleteError", "This User is not an admin in this Store!");
        return "manage-store";
    }

    @PostMapping("/{storeName}/manage/add-admin")
    public String addAdmin(@PathVariable String storeName,
                           @RequestParam(name = "email") String email,
                           Model model) {

        if (storeService.addAdmin(storeName, email)) {

            return "manage-store";
        }

        model.addAttribute("addError", "This User is already an admin in this Store, or this User is not exist!");
        return "manage-store";
    }
}
