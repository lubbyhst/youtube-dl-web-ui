package com.github.lubbyhst.youtubedlwebui.controller;

import com.github.lubbyhst.youtubedlwebui.service.SseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestController
public class SseController {

    @Autowired
    private SseService sseService;

    @RequestMapping(path = "/event/update", method = RequestMethod.GET)
    public SseEmitter getEventUpdate() {
        return this.sseService.getEventUpdate();
    }
}
