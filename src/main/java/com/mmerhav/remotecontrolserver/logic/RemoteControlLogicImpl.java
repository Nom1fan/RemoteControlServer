package com.mmerhav.remotecontrolserver.logic;

import com.mmerhav.remotecontrolserver.manager.ExecutablesManager;
import com.mmerhav.remotecontrolserver.runner.ProcessManager;
import com.mmerhav.remotecontrolserver.runner.RunProcessResult;
import com.mmerhav.remotecontrolserver.runner.StopProcessResult;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static javax.servlet.http.HttpServletResponse.SC_BAD_REQUEST;
import static javax.servlet.http.HttpServletResponse.SC_INTERNAL_SERVER_ERROR;

@Component
@Setter
public class RemoteControlLogicImpl implements RemoteControlLogic {

    @Autowired
    private ProcessManager processManager;

    @Autowired
    private ExecutablesManager executablesManager;

    @Override
    public void startProcess(String execName, HttpServletResponse response) throws IOException {
        if (isInvalidExecName(execName, response)) {
            return;
        }

        String executableAbsolutePath = executablesManager.getExecutableAbsolutePath(execName);
        RunProcessResult result = processManager.runProcess(executableAbsolutePath);
        if (!result.isSuccess()) {
            response.sendError(SC_INTERNAL_SERVER_ERROR, result.getErrMsg());
        }
    }

    @Override
    public void stopProcess(String execName, HttpServletResponse response) throws IOException {
        if (isInvalidExecName(execName, response)) {
            return;
        }

        StopProcessResult result = processManager.stopProcess(execName);

        if (!result.isSuccess()) {
            response.sendError(SC_INTERNAL_SERVER_ERROR, result.getErrMsg());
        }
    }

    private boolean isInvalidExecName(String execName, HttpServletResponse response) throws IOException {
        if (!executablesManager.isValidExecutable(execName)) {
            response.sendError(SC_BAD_REQUEST, String.format("Remote executable not found: [%s]. Make sure the remote executable name is defined on the server.", execName));
            return true;
        }
        return false;
    }
}
