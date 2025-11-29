package com.mmerhav.remotecontrolserver.logic;

import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;

import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class RemoteControlLogicITest {

    @Autowired
    private RemoteControlLogicImpl remoteControlLogic;

    @Mock
    private HttpServletResponse httpServletResponse;

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
        Assertions.assertEquals("ERROR: The process \"UTORRENT*\" not found.", logicResponse);
    }

}


