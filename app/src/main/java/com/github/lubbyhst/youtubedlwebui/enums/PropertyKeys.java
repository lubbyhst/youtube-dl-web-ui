package com.github.lubbyhst.youtubedlwebui.enums;

public enum PropertyKeys {

    YT_DL_COMMAND("yt.dl.command"),
    YT_DL_IGNORE_CONFIG("yt.dl.ignore-config"),
    YT_DL_NO_CONTINUE("yt.dl.no-continue"),
    YT_DL_RESTRICT_FILENAMES("yt.dl.restrict-filenames"),
    YT_DL_FORMAT("yt.dl.format"),
    YT_DL_OUTPUT_TEMPLATE("yt.dl.output.template"),
    YT_DL_VERBOSE("yt.dl.verbose"),
    YT_DL_OUTPUT_DIR("yt.dl.output.dir");

    private final String key;

    PropertyKeys(final String key) {
        this.key = key;
    }

    public String getKey() {
        return this.key;
    }
}
