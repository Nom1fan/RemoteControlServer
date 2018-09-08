package com.mmerhav.remotecontrolserver.logic;

import com.mmerhav.remotecontrolserver.manager.ExecutablesManager;
import com.mmerhav.remotecontrolserver.runner.ProcessRunner;
import com.mmerhav.remotecontrolserver.runner.RunProcessResult;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static javax.servlet.http.HttpServletResponse.SC_BAD_REQUEST;
import static javax.servlet.http.HttpServletResponse.SC_INTERNAL_SERVER_ERROR;

@Component
@Setter
public class RemoteControlLogicImpl implements RemoteControlLogic {

    private Map<String, Process> runningProcessesMap = new HashMap<>();

    @Autowired
    private ProcessRunner processRunner;

    @Autowired
    private ExecutablesManager executablesManager;

    @Override
    public void startProcess(String execName, HttpServletResponse response) throws IOException {
        if (isInvalidExecName(execName, response)) {
            return;
        }

        String executableAbsolutePath = executablesManager.getExecutableAbsolutePath(execName);
        RunProcessResult result = processRunner.runProcess(executableAbsolutePath);
        if (!result.isSuccess()) {
            response.sendError(SC_INTERNAL_SERVER_ERROR, result.getErrMsg());
            return;
        }

        runningProcessesMap.put(execName, result.getProcess());
    }

    @Override
    public void stopProcess(String execName, HttpServletResponse response) throws IOException {
        if (isInvalidExecName(execName, response)) {
            return;
        }

        if (runningProcessesMap.containsKey(execName)) {
            runningProcessesMap.get(execName).destroy();
            runningProcessesMap.remove(execName);
        } else {
            //TODO Add better way to kill process here (`taskkill /f /im teamviewer* /T` with admin rights perhaps
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
