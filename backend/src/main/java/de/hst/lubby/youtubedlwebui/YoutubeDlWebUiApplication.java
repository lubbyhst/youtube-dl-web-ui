package de.hst.lubby.youtubedlwebui;

import de.hst.lubby.youtubedlwebui.service.YoutubeDlService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.logging.Logger;

@SpringBootApplication
@EnableAsync
@EnableScheduling
public class YoutubeDlWebUiApplication extends SpringBootServletInitializer {

    private Logger logger = Logger.getLogger(YoutubeDlWebUiApplication.class.getName());

    public static void main(String[] args) {
        SpringApplication.run(YoutubeDlWebUiApplication.class, args);
    }
}
