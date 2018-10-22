package de.hst.lubby.youtubedlwebui;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class YoutubeDlWebUiApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(YoutubeDlWebUiApplication.class, args);
    }
}
