package com.github.lubbyhst.youtubedlwebui.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.github.lubbyhst.youtubedlwebui.model.Entry;

@RunWith(MockitoJUnitRunner.class)
public class YoutubeDlServiceTest {

    @Mock
    private QueueService queueService;

    @Mock
    private PropertiesService propertiesService;

    @InjectMocks
    private YoutubeDlService youtubeDlService;

    @Test
    public void checkQueueTest(){
       Entry entry = new Entry();
        Mockito.when(queueService.getNextEntry()).thenReturn(entry);

        youtubeDlService.checkQueue();
    }
}
