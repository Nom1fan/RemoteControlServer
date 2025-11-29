package com.mmerhav.remotecontrolserver.logic;

import com.mmerhav.remotecontrolserver.exec.ExecutablesManager;
import com.mmerhav.remotecontrolserver.process.ProcessManager;
import com.mmerhav.remotecontrolserver.process.RunProcessResult;
import com.mmerhav.remotecontrolserver.process.StopProcessResult;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import static jakarta.servlet.http.HttpServletResponse.SC_BAD_REQUEST;
import static jakarta.servlet.http.HttpServletResponse.SC_INTERNAL_SERVER_ERROR;

@Component
@Setter
public class RemoteControlLogicImpl implements RemoteControlLogic {

    @Autowired
    private ProcessManager processManager;

    @Autowired
    private ExecutablesManager executablesManager;

    @Override
    public String startProcess(String execName, HttpServletResponse response) throws IOException {
        if (isInvalidExecName(execName, response)) {
            return "";
        }

        String executableAbsolutePath = executablesManager.getExecutableAbsolutePath(execName);
        RunProcessResult result = processManager.runProcess(executableAbsolutePath);
        if (!result.isSuccess()) {
            response.sendError(SC_INTERNAL_SERVER_ERROR, result.getErrMsg());
        }

        return String.format("Process [%s] has been successfully started", execName);
    }

    @Override
    public String stopProcess(String execName, HttpServletResponse response) throws IOException {
        if (isInvalidExecName(execName, response)) {
            return "";
        }

        StopProcessResult result = processManager.stopProcess(execName);

        if (!result.isSuccess()) {
            return result.getErrMsg();
        }

        return String.format("Process [%s] has been successfully stopped", execName);
    }

    private boolean isInvalidExecName(String execName, HttpServletResponse response) throws IOException {
        if (!executablesManager.isValidExecutable(execName)) {
            response.sendError(SC_BAD_REQUEST, String.format("Remote executable not found: [%s]. Make sure the remote executable name is defined on the server.", execName));
            return true;
        }
        return false;
    }
}
