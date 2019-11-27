package com.github.lubbyhst.youtubedlwebui.service;

import com.github.lubbyhst.youtubedlwebui.enums.PropertyKeys;
import com.github.lubbyhst.youtubedlwebui.model.Entry;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.io.ByteArrayInputStream;
import java.io.File;

@RunWith(PowerMockRunner.class)
@PrepareForTest(YoutubeDlService.class)
public class YoutubeDlServiceTest {

    @Mock
    private QueueService queueService;

    @Mock
    private PropertiesService propertiesService;

    @Mock
    private ProcessBuilder processBuilder;

    @Mock
    private Process process;

    @Mock
    private SseService sseService;

    @InjectMocks
    private YoutubeDlService youtubeDlService;

    private Entry entry;

    @Before
    public void before() throws Exception {
        MockitoAnnotations.initMocks(this);
        PowerMockito.whenNew(ProcessBuilder.class).withArguments(Mockito.anyList()).thenReturn(this.processBuilder);
        this.entry = new Entry();
        Mockito.when(this.queueService.getNextEntry()).thenReturn(this.entry);
        Mockito.when(this.propertiesService.getStringValue(PropertyKeys.YT_DL_OUTPUT_DIR, "downloads")).thenReturn("target");
        Mockito.when(this.processBuilder.start()).thenReturn(this.process);
    }

    @Test
    public void checkQueueTest() throws Exception {
        Mockito.when(this.process.getInputStream()).thenReturn(new ByteArrayInputStream("".getBytes()));
        Mockito.when(this.process.getErrorStream()).thenReturn(new ByteArrayInputStream("".getBytes()));
        this.youtubeDlService.checkQueue();
        Assert.assertTrue(this.entry.isFinished());
    }

    @Test
    public void checkProgressUpdateViaInputStreamTest() {
        Mockito.when(this.process.getInputStream()).thenReturn(this.getClass().getClassLoader().getResourceAsStream("downloadVideoSuccess.log"));
        Mockito.when(this.process.getErrorStream()).thenReturn(new ByteArrayInputStream("".getBytes()));
        Mockito.when(this.processBuilder.directory()).thenReturn(new File("target/"));
        this.youtubeDlService.checkQueue();
        Mockito.verify(this.sseService, Mockito.atLeastOnce()).sendUpdateEvent();
        Assert.assertEquals("Progress should be 100% after finished process.", this.entry.getProgress(), 100d, 0d);
    }
}
