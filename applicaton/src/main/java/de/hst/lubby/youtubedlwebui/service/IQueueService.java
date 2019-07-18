package de.hst.lubby.youtubedlwebui.service;

import com.fasterxml.jackson.databind.JsonNode;
import de.hst.lubby.youtubedlwebui.model.Entry;

import java.util.List;

public interface IQueueService {

    void addToQueue(Entry entry);

    Entry getNextEntry();

    List<Entry> getAllEntries();

    boolean hasEntries();

    void clearQueue();
}
