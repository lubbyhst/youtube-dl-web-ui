package com.github.lubbyhst.youtubedlwebui.service;

import com.github.lubbyhst.youtubedlwebui.model.Entry;

import java.util.List;

public interface IQueueService {

    void addToQueue(Entry entry);

    Entry getEntryById(String id);

    Entry getNextEntry();

    List<Entry> getAllEntries();

    boolean hasEntries();

    void clearQueue();
}
