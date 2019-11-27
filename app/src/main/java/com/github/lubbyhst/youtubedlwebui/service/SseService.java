package com.github.lubbyhst.youtubedlwebui.service;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.logging.Level;
import java.util.logging.Logger;

@Component
public class SseService {
    private static final Logger logger = Logger.getLogger(SseService.class.getName());

    private final SseEmitter sseEmitter = new SseEmitter();

    public SseEmitter getEventUpdate() {
        return this.sseEmitter;
    }

    public void sendUpdateEvent() {
        try {
            this.sseEmitter.send(true);
        } catch (final Exception e) {
            logger.log(Level.FINE, "Sse message could not be send", e);
        }
    }
}
