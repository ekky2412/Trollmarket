package id.indocyber.trollmarket.controllers;

import id.indocyber.trollmarket.dtos.merchandise.MerchandiseFormDto;
import id.indocyber.trollmarket.dtos.shipment.ShipmentFormDto;
import id.indocyber.trollmarket.services.ShipmentService;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/shipment")
public class ShipmentController {
    private ShipmentService shipmentService;

    public ShipmentController(ShipmentService shipmentService) {
        this.shipmentService = shipmentService;
    }

    @GetMapping("")
    public ModelAndView index(Authentication authentication) {
        return new ModelAndView("shipment/index")
                .addObject("shippers", shipmentService.getShippers());
    }

    @GetMapping("/edit/{id}")
    public ModelAndView edit(@PathVariable Integer id, Authentication authentication) {
        return new ModelAndView("shipment/index")
                .addObject("shipper", shipmentService.getShipperDetail(id))
                .addObject("shippers", shipmentService.getShippers());
    }

    //    TODO
    //    ADD addShipper, editShipper, stopService
    @PostMapping("/upsert")
    public String upsertShipper(@Valid @ModelAttribute("shipper") ShipmentFormDto dto,
                                BindingResult bindingResult,
                                Model model){
        if(bindingResult.hasErrors()){
            return "shipment/index";
        }
        shipmentService.upsert(dto);
        return "redirect:/shipment";
    }

    @GetMapping("/delete/{id}")
    public ModelAndView delete(@PathVariable Integer id, Authentication authentication) {
        Integer orderCount = shipmentService.getOrderCountByShipper(id);
        if (orderCount > 0) {
            return new ModelAndView("shipment/delete-rejected")
                    .addObject("orderCount", orderCount);
        }
        return new ModelAndView("shipment/delete-confirmation")
                .addObject("shipper", shipmentService.getShipperDetail(id));
    }

    @PostMapping("/delete/confirm")
    public String deleteConfirm(ShipmentFormDto dto) {
        shipmentService.delete(dto);
        return "redirect:/shipment";
    }

    @GetMapping("/stopService/{id}")
    public ModelAndView stopServiceView(@PathVariable Integer id, Authentication authentication) {
        return new ModelAndView("shipment/stop-service-confirmation")
                .addObject("shipper", shipmentService.getShipperDetail(id));
    }

    @PostMapping("/stopService/{id}")
    public String stopService(@PathVariable Integer id){
        shipmentService.stopService(id);
        return "redirect:/shipment";
    }

}
