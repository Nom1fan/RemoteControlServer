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
    private HttpServletResponse httpServletResponse;

    @Before
    public void before() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void startAndStopProcess_success() throws IOException {
        String processName = "TeamViewer";
        remoteControlLogic.startProcess(processName, httpServletResponse);
        remoteControlLogic.stopProcess(processName, httpServletResponse);

        verify(httpServletResponse, times(0)).sendError(anyInt());
    }

    @Test
    public void stopNonRunningProcess_noop() throws IOException {
        String processName = "UTORRENT";
        String logicResponse = remoteControlLogic.stopProcess(processName, httpServletResponse);
        Assert.assertEquals("ERROR: The process \"UTORRENT*\" not found.", logicResponse);
    }

}


