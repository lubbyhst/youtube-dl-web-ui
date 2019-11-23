package de.hst.lubby.youtubedlwebui.controller.web;

import de.hst.lubby.youtubedlwebui.model.Entry;
import de.hst.lubby.youtubedlwebui.service.QueueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.util.logging.Logger;

@Controller
@RequestMapping(value = "/home")
public class HomeController {

    private static final Logger logger = Logger.getLogger(HomeController.class.getName());

    @Autowired
    private QueueService queueService;

    @RequestMapping(value = {"/", ""}, method = RequestMethod.GET)
    public String main(final Model model) {
        model.addAttribute("entry", new Entry());
        model.addAttribute("entries", this.queueService.getAllEntries());
        return "home.html";
    }

    @RequestMapping(value = "addEntry", method = RequestMethod.POST)
    public String addEntry(@ModelAttribute final Entry entry, final BindingResult errors, final Model model) {
        if(entry.getYtUrl() != null && !entry.getYtUrl().isEmpty()){
            this.queueService.addToQueue(entry);
        }
        return "redirect:/home/";
    }

    @RequestMapping(value = "download", method = RequestMethod.GET, produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    @ResponseBody
    public FileSystemResource downloadEntry(@RequestParam final String id) {
        final Entry entry = this.queueService.getEntryById(id);
        if (entry == null) {
            logger.severe(String.format("Entry with id [%s] could not be found.", id));
            return null;
        }
        if (entry.getFileUri() == null) {
            logger.severe(String.format("Path to file not available of download [%s]", entry.getYtUrl()));
            return null;
        }
        final File fileResource = new File(this.queueService.getEntryById(id).getFileUri());
        if (!fileResource.exists()) {
            logger.warning(String.format("File could not be found. [%s]", fileResource.getAbsolutePath()));
            return null;
        }
        return new FileSystemResource(new File(this.queueService.getEntryById(id).getFileUri()));
    }
}
