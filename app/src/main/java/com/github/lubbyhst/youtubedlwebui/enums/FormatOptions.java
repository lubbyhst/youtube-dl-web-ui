package com.github.lubbyhst.youtubedlwebui.enums;

public enum FormatOptions {
    VIDEO_HD("best", "Video 1080p mp4"),
    VIDEO_MMI("best", "Video MMI special"),
    VIDEO_SD("best", "Video 480p mp4"),
    AUDIO_ONLY("best --extract-audio", "Audio mp3");

    private final String format;
    private final String displayedName;

    FormatOptions(final String format, final String displayedName) {
        this.displayedName = displayedName;
        this.format = format;
    }

    public String getDisplayedName(){
        return this.displayedName;
    }

    public String getFormat() {
        return this.format;
    }
}
