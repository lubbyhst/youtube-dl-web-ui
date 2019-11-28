package com.github.lubbyhst.youtubedlwebui.enums;

public enum FormatOptions {
    VIDEO_HD("[height<=1080] --merge-output-format mp4", "Video 1080p mp4"),
    VIDEO_MMI("[height<=576] --merge-output-format mp4 --recode-video mp4 --prefer-ffmpeg", "Video MMI special"),
    VIDEO_SD("[height<=480] --merge-output-format mp4", "Video 480p mp4"),
    AUDIO_ONLY("--extract-audio --audio-format mp3", "Audio mp3");

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
