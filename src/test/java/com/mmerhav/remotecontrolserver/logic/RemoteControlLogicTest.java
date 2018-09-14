package com.mmerhav.remotecontrolserver.logic;

import com.mmerhav.remotecontrolserver.manager.ExecutablesManager;
import com.mmerhav.remotecontrolserver.runner.ProcessManager;
import com.mmerhav.remotecontrolserver.runner.RunProcessResult;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static javax.servlet.http.HttpServletResponse.SC_BAD_REQUEST;
import static javax.servlet.http.HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

public class RemoteControlLogicTest {

    @InjectMocks
    private RemoteControlLogicImpl remoteControlLogic;

    @Mock
    private ExecutablesManager executablesManager;

    @Mock
    private ProcessManager processManager;

    @Mock
    private HttpServletResponse response;

    @Before
    public void before() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void startProcess_name2ExecutableMapperReturnsInvalidExecutableName_LogicSendsBadRequestHttpError() throws IOException {
        String execName = "execName";
        when(executablesManager.isValidExecutable(eq(execName))).thenReturn(false);
        remoteControlLogic.startProcess(execName, response);
        verify(response, times(1)).sendError(SC_BAD_REQUEST, String.format("Remote executable not found: [%s]. Make sure the remote executable name is defined on the server.", execName));
    }

    @Test
    public void startProcess_processManagerReturnsFailedResult_LogicSendsInternalServerError() throws IOException {
        String processName = "TeamViewer";
        String pathToExec = "/path/to/executable/executable.exe";
        when(executablesManager.isValidExecutable(eq(processName))).thenReturn(true);
        when(executablesManager.getExecutableAbsolutePath(eq(processName))).thenReturn(pathToExec);
        when(processManager.runProcess(eq(pathToExec))).thenReturn(new RunProcessResult(false, null, "Test message"));

        remoteControlLogic.startProcess(processName, response);

        verify(response, times(1)).sendError(SC_INTERNAL_SERVER_ERROR, "Test message");
    }

    @Test
    public void stopProcess_ProcessWasNeverRun_LogicSendsBadRequestHttpError() throws IOException {
        String processName = "TeamViewer";
        remoteControlLogic.stopProcess(processName, response);
        verify(response, times(1)).sendError(SC_BAD_REQUEST, String.format("Remote executable not found: [TeamViewer]. Make sure the remote executable name is defined on the server.", processName));
    }
}
