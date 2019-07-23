package de.hst.lubby.youtubedlwebui.model;

import java.io.Serializable;

public class Entry implements Serializable {

    private static final long serialVersionUID = -8582553475226281591L;

    private String id;
    private String ytUrl;
    private double progress;
    private boolean finished;
    private String fileUri;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getYtUrl() {
        return ytUrl;
    }

    public void setYtUrl(String ytUrl) {
        this.ytUrl = ytUrl;
    }

    public double getProgress() {
        return progress;
    }

    public void setProgress(double progress) {
        this.progress = progress;
    }

    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    public String getFileUri() {
        return fileUri;
    }

    public void setFileUri(String fileUri) {
        this.fileUri = fileUri;
    }
}
