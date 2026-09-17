package org.example.controller;

import org.example.entity.Record;
import org.example.entity.RecordStatus;
import org.example.entity.dto.RecordsContainerDto;
import org.example.service.RecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

import static org.example.entity.RecordStatus.ACTIVE;
import static org.example.entity.RecordStatus.DONE;

@Controller
public class CommonController {
    private final RecordService recordService;

    @Autowired
    public CommonController(RecordService recordService) {
        this.recordService = recordService;
    }

    @RequestMapping("/")
    public String redirectToHomePage(){
        return "redirect:/home";
    }


    @RequestMapping("/home")
    public String getMainPage(Model model, @RequestParam(value = "filter", required = false) String filterMode){
        RecordsContainerDto container =  recordService.findAllRecords(filterMode);
        model.addAttribute("records", container.getRecords());
        model.addAttribute("numberOfDoneRecords", container.getNumberOfDoneRecords());
        model.addAttribute("numberOfActiveRecords", container.getNumberOfActiveRecords());
        return "main-page";
    }

    @RequestMapping(value = "/add-record", method = RequestMethod.POST)
    public String addRecord(@RequestParam("title") String title){
        recordService.saveRecord(title);
        return "redirect:/home";
    }

    @RequestMapping(value = "/make-record-done", method = RequestMethod.POST)
    public String makeRecordDone(@RequestParam("id") int id,
                                 @RequestParam(name = "filter", required = false) String filterMode){
        recordService.updateRecordStatus(id, DONE);
        return "redirect:/home" + (filterMode != null && !filterMode.isBlank() ? "?filter=" + filterMode : "");
    }

    @RequestMapping(value = "/delete-record", method = RequestMethod.POST)
    public String deleteRecord(@RequestParam("id") int id,
                               @RequestParam(name = "filter", required = false) String filterMode){
        recordService.deleteRecord(id);
        return "redirect:/home" + (filterMode != null && !filterMode.isBlank() ? "?filter=" + filterMode : "");
    }
}
