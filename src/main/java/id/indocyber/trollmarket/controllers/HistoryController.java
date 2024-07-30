package id.indocyber.trollmarket.controllers;

import id.indocyber.trollmarket.dtos.history.HistoryGetFilterDto;
import id.indocyber.trollmarket.services.HistoryService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/history")
public class HistoryController {
    private HistoryService historyService;

    public HistoryController(HistoryService historyService) {
        this.historyService = historyService;
    }

    @GetMapping("")
    public ModelAndView index(HistoryGetFilterDto dto) {
        return new ModelAndView("history/index")
                .addObject("searchDto", dto)
                .addObject("sellers", historyService.getSellers())
                .addObject("buyers", historyService.getBuyers())
                .addObject("histories", historyService.get(dto));
    }
}
