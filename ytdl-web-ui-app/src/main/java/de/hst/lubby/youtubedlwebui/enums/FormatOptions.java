package de.hst.lubby.youtubedlwebui.enums;

public enum FormatOptions {
    VIDEO_HD("bestvideo[height<=1080]+bestaudio/best[height<=1080][ext=mp4]"),
    VIDEO_SD("bestvideo[height<=480]+bestaudio/best[height<=480][ext=mp4]"),
    AUDIO_ONLY("bestaudio[ext=m4a]");

    private final String format;

    FormatOptions(String format){
        this.format = format;
    }

    public String getDisplayedName(){
        return this.name();
    }

    public String getFormat() {
        return format;
    }
}
