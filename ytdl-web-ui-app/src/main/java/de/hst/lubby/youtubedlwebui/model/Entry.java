package de.hst.lubby.youtubedlwebui.model;

import java.io.Serializable;

public class Entry implements Serializable {

    private static final long serialVersionUID = -8582553475226281591L;

    private String id;
    private String url;
    private double progress;
    private boolean finished;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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
}
