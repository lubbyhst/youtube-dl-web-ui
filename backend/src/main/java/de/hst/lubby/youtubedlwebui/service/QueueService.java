package de.hst.lubby.youtubedlwebui.service;

import com.fasterxml.jackson.databind.JsonNode;
import de.hst.lubby.youtubedlwebui.model.Entry;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QueueService implements IQueueService{

    private final List<Entry> entryList = new ArrayList<>();

    @Override
    public void addToQueue(Entry entry) {
        this.entryList.add(entry);
    }

    @Override
    public Entry getNextEntry() {
        for (Entry entry :
                entryList) {
            if (!entry.isFinished()){
                return entry;
            }
        }
        return null;
    }

    @Override
    public List<Entry> getAllEntries() {
        return this.entryList;
    }

    @Override
    public boolean hasEntries() {
        return !entryList.isEmpty();
    }

    @Override
    public void clearQueue() {
        entryList.clear();
    }
}
