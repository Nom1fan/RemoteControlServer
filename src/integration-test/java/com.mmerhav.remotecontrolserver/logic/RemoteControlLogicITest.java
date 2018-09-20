package com.mmerhav.remotecontrolserver.logic;

import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.io.IOException;
import javax.servlet.http.HttpServletResponse;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RemoteControlLogicITest {

    @Autowired
    private RemoteControlLogicImpl remoteControlLogic;

    @Mock
    private HttpServletResponse response;

    @Before
    public void before() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void startAndStopProcess_success() throws IOException {
        String processName = "TeamViewer";
        remoteControlLogic.startProcess(processName, response);
        remoteControlLogic.stopProcess(processName, response);

        verify(response, times(0)).sendError(anyInt());
    }

    @Test
    public void stopNonRunningProcess_noop() throws IOException {
        String processName = "UTORRENT";
        String response = remoteControlLogic.stopProcess(processName, this.response);
        Assert.assertEquals("ERROR: The process \"UTORRENT*\" not found.", response);
    }

}


