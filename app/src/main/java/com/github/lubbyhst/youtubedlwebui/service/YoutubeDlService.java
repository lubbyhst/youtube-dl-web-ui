package com.github.lubbyhst.youtubedlwebui.service;

import com.github.lubbyhst.youtubedlwebui.enums.FormatOptions;
import com.github.lubbyhst.youtubedlwebui.enums.PropertyKeys;
import com.github.lubbyhst.youtubedlwebui.model.Entry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class YoutubeDlService {

    private final Logger logger = Logger.getLogger(YoutubeDlService.class.getName());

    @Autowired
    private IQueueService queueService;

    @Autowired
    private PropertiesService propertiesService;


    @Scheduled(fixedRate = 1000)
    public void checkQueue() {
        final Entry entry = this.queueService.getNextEntry();
        try {
            startYtDownload(entry);
        } catch (final IOException | InterruptedException e) {
            this.logger.log(Level.SEVERE, "Exception occurs trying to download video from YT.", e);
            entry.setErrorMessage(e.getMessage());
            entry.setFinished(true);
        }
    }

    private void startYtDownload(final Entry entry) throws IOException, InterruptedException {
        if(entry == null){
            this.logger.fine("Entry was null.");
            return;
        }
        this.logger.info("Starting new download thread.");

        final ProcessBuilder processBuilder = new ProcessBuilder(generateParameters(entry.getYtUrl(), entry.getFormatOption()));
        processBuilder.directory(new File(this.propertiesService.getStringValue(PropertyKeys.YT_DL_OUTPUT_DIR)));
        processBuilder.redirectErrorStream(false);

        final Process process = processBuilder.start();

        BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String line;

        while ((line = br.readLine()) != null ){
            this.logger.fine(line);
                if(line.matches("\\[download\\].*%.of.*")){
                    final Matcher matcher = Pattern.compile("(?<=\\[download\\] ).{1,3}(?=.*%)").matcher(line);
                    if(matcher.find()){
                        entry.setProgress(Double.valueOf(matcher.group(0).trim()));
                    }
                }
            if (line.matches(".+[.][a-zA-Z0-9]{3} .*") || line.matches(".+[.][a-zA-Z0-9]{3}$")) {
                final Matcher fileAlreadyExistsMatcher = Pattern.compile("(?<=\\[download\\] ).+[.][a-zA-Z0-9]{3}").matcher(line);
                if (fileAlreadyExistsMatcher.find()) {
                    entry.setFileUri(processBuilder.directory().getAbsolutePath() + "//" + fileAlreadyExistsMatcher.group(0));
                    entry.setVideoName(fileAlreadyExistsMatcher.group(0));
                }
                final Matcher fileNotExistsMatcher = Pattern.compile("(?<=\\[download\\] Destination: ).+[.][a-zA-Z0-9]{3}.*$").matcher(line);
                if (fileNotExistsMatcher.find()) {
                    entry.setFileUri(processBuilder.directory().getAbsolutePath() + "//" + fileNotExistsMatcher.group(0));
                    entry.setVideoName(fileNotExistsMatcher.group(0));
                }
                this.logger.info(String.format("Downloaded video to %s", entry.getFileUri()));
            }
        }
        br = new BufferedReader(new InputStreamReader(process.getErrorStream()));
        line = null;
        while ((line = br.readLine()) != null ){
            if(line.contains("[debug]")){
                this.logger.info(line);
            }else {
                this.logger.warning(line);
            }
            if (line.contains("ERROR:") || line.contains("error:") || line.contains("Error:")) {
                if (entry.getErrorMessage() == null) {
                    entry.setErrorMessage(line);
                }
                entry.setErrorMessage(entry.getErrorMessage() + "\n" + line);
            }
        }

        process.waitFor();
        entry.setFinished(true);
        this.logger.info("Process finished.");
    }

    private String[] generateParameters(final String url, final FormatOptions formatOption) {
        final List<String> parameters = new ArrayList<>();
        parameters.add(this.propertiesService.getStringValue(PropertyKeys.YT_DL_COMMAND));
        if (Boolean.parseBoolean(this.propertiesService.getStringValue(PropertyKeys.YT_DL_VERBOSE))) {
            parameters.add("--verbose");
        }
        if (Boolean.parseBoolean(this.propertiesService.getStringValue(PropertyKeys.YT_DL_IGNORE_CONFIG))) {
            parameters.add("--ignore-config");
        }
        if (Boolean.parseBoolean(this.propertiesService.getStringValue(PropertyKeys.YT_DL_NO_CONTINUE))) {
            parameters.add("--no-continue");
        }
        if (Boolean.parseBoolean(this.propertiesService.getStringValue(PropertyKeys.YT_DL_RESTRICT_FILENAMES))) {
            parameters.add("--restrict-filenames");
        }
        parameters.add(String.format("-f %s", formatOption.getFormat()));
        parameters.add(String.format("-o %s", this.propertiesService.getStringValue(PropertyKeys.YT_DL_OUTPUT_TEMPLATE, "%(title)s.%(ext)s").trim()));
//        parameters.add("--get-filename");

        parameters.add(url);
        return parameters.toArray(new String[parameters.size()]);
    }



}
