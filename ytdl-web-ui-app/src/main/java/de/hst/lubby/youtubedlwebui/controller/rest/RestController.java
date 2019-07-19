package de.hst.lubby.youtubedlwebui.controller.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.hst.lubby.youtubedlwebui.model.Entry;
import de.hst.lubby.youtubedlwebui.service.IQueueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;
import java.util.logging.Logger;

@org.springframework.web.bind.annotation.RestController
public class RestController {

    private Logger logger = Logger.getLogger(RestController.class.getName());

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private IQueueService queueService;

    @RequestMapping(value = "/addEntry", method = RequestMethod.PUT)
    public ResponseEntity<Void> addEntry(@RequestBody Entry entry) throws JsonProcessingException {
        logger.info("Received new entry.");
        logger.info(objectMapper.writeValueAsString(entry));
        queueService.addToQueue(entry);
        return  new ResponseEntity<Void>(HttpStatus.CREATED);
    }

    @RequestMapping(value = "/getAllEntries", method = RequestMethod.GET)
    public ResponseEntity<List<Entry>> getAllEntries(){
        return new ResponseEntity<List<Entry>>(queueService.getAllEntries(), HttpStatus.OK);
    }

    @RequestMapping(value = "/getEntry", method = RequestMethod.GET)
    public ResponseEntity<Entry> getEntry(){
        return new ResponseEntity<Entry>(queueService.getNextEntry(), HttpStatus.OK);
    }

    @RequestMapping(value = "/clearQueue", method = RequestMethod.GET)
    public ResponseEntity<Void> clearQueue(){
        queueService.clearQueue();
        return new ResponseEntity<Void>(HttpStatus.OK);
    }
}
