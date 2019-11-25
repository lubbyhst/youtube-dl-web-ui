package com.github.lubbyhst.youtubedlwebui.model;

import com.github.lubbyhst.youtubedlwebui.enums.FormatOptions;

import java.io.Serializable;

public class Entry implements Serializable {

    private static final long serialVersionUID = -8582553475226281591L;

    private String id;
    private String ytUrl;
    private String videoName;
    private double progress;
    private boolean finished;
    private String fileUri;
    private String errorMessage;
    private FormatOptions formatOption;

    public FormatOptions getFormatOption() {
        return this.formatOption;
    }

    public void setFormatOption(final FormatOptions formatOption) {
        this.formatOption = formatOption;
    }

    public String getId() {
        return this.id;
    }

    public void setId(final String id) {
        this.id = id;
    }

    public String getYtUrl() {
        return this.ytUrl;
    }

    public void setYtUrl(final String ytUrl) {
        this.ytUrl = ytUrl;
    }

    public double getProgress() {
        return this.progress;
    }

    public void setProgress(final double progress) {
        this.progress = progress;
    }

    public boolean isFinished() {
        return this.finished;
    }

    public void setFinished(final boolean finished) {
        this.finished = finished;
    }

    public String getFileUri() {
        return this.fileUri;
    }

    public void setFileUri(final String fileUri) {
        this.fileUri = fileUri;
    }

    public String getErrorMessage() {
        return this.errorMessage;
    }

    public void setErrorMessage(final String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getVideoName() {
        return this.videoName;
    }

    public void setVideoName(final String videoName) {
        this.videoName = videoName;
    }
}
