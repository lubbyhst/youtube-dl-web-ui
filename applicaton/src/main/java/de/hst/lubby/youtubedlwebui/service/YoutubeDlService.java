package de.hst.lubby.youtubedlwebui.service;

import com.fasterxml.jackson.databind.JsonNode;
import de.hst.lubby.youtubedlwebui.controller.RestController;
import de.hst.lubby.youtubedlwebui.enums.PropertyKeys;
import de.hst.lubby.youtubedlwebui.model.Entry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class YoutubeDlService {

    private Logger logger = Logger.getLogger(YoutubeDlService.class.getName());

    @Autowired
    private IQueueService queueService;

    @Autowired
    private PropertiesService propertiesService;


    @Scheduled(fixedRate = 1000)
    public void checkQueue() throws IOException, InterruptedException {
        startYtDownload(queueService.getNextEntry());
    }

    private void startYtDownload(Entry entry) throws IOException, InterruptedException {
        if(entry == null){
            logger.fine("Entry was null.");
            return;
        }
        logger.info("Starting new download thread.");

        ProcessBuilder processBuilder = new ProcessBuilder(generateParameters(entry.getUrl()));
        processBuilder.directory(new File(propertiesService.getStringValue(PropertyKeys.YT_DL_OUTPUT_DIR)));
        processBuilder.redirectErrorStream(false);

        Process process = processBuilder.start();

        BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String line;

        while ((line = br.readLine()) != null ){
                logger.fine(line);
                if(line.matches("\\[download\\].*%.of.*")){
                    Matcher matcher = Pattern.compile("(?<=\\[download\\] ).....(?=.*%)").matcher(line);
                    if(matcher.find()){
                        entry.setProgress(Double.valueOf(matcher.group(0).trim()));
                    }
                }
        }
        br = new BufferedReader(new InputStreamReader(process.getErrorStream()));
        line = null;
        while ((line = br.readLine()) != null ){
            logger.warning(line);
        }

        process.waitFor();
        entry.setFinished(true);
        logger.info("Process finished.");
    }

    private String[] generateParameters(String url){
        List<String> parameters = new ArrayList<>();
        parameters.add(propertiesService.getStringValue(PropertyKeys.YT_DL_COMMAND));
        if(Boolean.parseBoolean(propertiesService.getStringValue(PropertyKeys.YT_DL_VERBOSE))){
            parameters.add("--verbose");
        }
        if(Boolean.parseBoolean(propertiesService.getStringValue(PropertyKeys.YT_DL_IGNORE_CONFIG))){
            parameters.add("--ignore-config");
        }
        if(Boolean.parseBoolean(propertiesService.getStringValue(PropertyKeys.YT_DL_NO_CONTINUE))){
            parameters.add("--no-continue");
        }
        if(Boolean.parseBoolean(propertiesService.getStringValue(PropertyKeys.YT_DL_RESTRICT_FILENAMES))){
            parameters.add("--restrict-filenames");
        }
        parameters.add(String.format("-f %s", propertiesService.getStringValue(PropertyKeys.YT_DL_FORMAT, "mp4")));
        parameters.add(String.format("-o %s", propertiesService.getStringValue(PropertyKeys.YT_DL_OUTPUT_TEMPLATE, "/tmp/%(title)s.%(ext)s")));

        parameters.add(url);
        return parameters.toArray(new String[parameters.size()]);
    }



}
