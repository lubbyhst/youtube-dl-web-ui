package de.hst.lubby.youtubedlwebui.controller.web;

import java.io.File;

import de.hst.lubby.youtubedlwebui.model.Entry;
import de.hst.lubby.youtubedlwebui.service.QueueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/home")
public class HomeController {

    @Autowired
    private QueueService queueService;

    @RequestMapping(value = {"/", ""}, method = RequestMethod.GET)
    public String main(Model model){
        model.addAttribute("entry", new Entry());
        model.addAttribute("entries", queueService.getAllEntries());
        return "home.html";
    }

    @RequestMapping(value = "addEntry", method = RequestMethod.POST)
    public String addEntry(@ModelAttribute Entry entry, BindingResult errors, Model model){
        if(entry.getYtUrl() != null && !entry.getYtUrl().isEmpty()){
            queueService.addToQueue(entry);
        }
        return "redirect:/home/";
    }

    @RequestMapping(value = "download", method = RequestMethod.GET)
    @ResponseBody
    public FileSystemResource downloadEntry(@RequestParam String id){
        return new FileSystemResource(new File(queueService.getEntryById(id).getFileUri()));
    }
}
