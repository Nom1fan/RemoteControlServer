package com.mmerhav.remotecontrolserver.logic;

import com.mmerhav.remotecontrolserver.exec.ExecutablesManager;
import com.mmerhav.remotecontrolserver.process.ProcessManager;
import com.mmerhav.remotecontrolserver.process.RunProcessResult;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;

import static jakarta.servlet.http.HttpServletResponse.SC_BAD_REQUEST;
import static jakarta.servlet.http.HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RemoteControlLogicTest {

    @InjectMocks
    private RemoteControlLogicImpl remoteControlLogic;

    @Mock
    private ExecutablesManager executablesManager;

    @Mock
    private ProcessManager processManager;

    @Mock
    private HttpServletResponse response;

    @Test
    public void startProcess_name2ExecutableMapperReturnsInvalidExecutableName_LogicSendsBadRequestHttpError()
            throws IOException {
        String execName = "execName";
        when(executablesManager.isValidExecutable(eq(execName))).thenReturn(false);
        remoteControlLogic.startProcess(execName, response);
        verify(response, times(1))
                .sendError(SC_BAD_REQUEST,
                        String.format("Remote executable not found: [%s]. " +
                                "Make sure the remote executable name is defined on the server.", execName));
    }

    @Test
    public void startProcess_processManagerReturnsFailedResult_LogicSendsInternalServerError() throws IOException {
        String processName = "TeamViewer";
        String pathToExec = "/path/to/executable/executable.exe";
        when(executablesManager.isValidExecutable(eq(processName))).thenReturn(true);
        when(executablesManager.getExecutableAbsolutePath(eq(processName))).thenReturn(pathToExec);
        when(processManager.runProcess(eq(pathToExec)))
                .thenReturn(new RunProcessResult(false, "Test message"));

        remoteControlLogic.startProcess(processName, response);

        verify(response, times(1)).sendError(SC_INTERNAL_SERVER_ERROR, "Test message");
    }
}