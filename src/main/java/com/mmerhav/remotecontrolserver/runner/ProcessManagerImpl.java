package com.mmerhav.remotecontrolserver.runner;

import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

@Component
public class ProcessManagerImpl implements ProcessManager {

    @Override
    public RunProcessResult runProcess(String executableAbsolutePath) {
        File file = new File(executableAbsolutePath);

        if (!file.exists()) {
            return new RunProcessResult(false, String.format("File [%s] doesn't exist", executableAbsolutePath));
        }

        String dir = file.getParent();

        try {
            Process process = Runtime.getRuntime().exec(executableAbsolutePath, null, new File(dir));
            String stdError = getStdError(process);

            if (!stdError.isEmpty()) {
                return new RunProcessResult(false, stdError);
            }

            return new RunProcessResult(true);
        } catch (IOException e) {
            e.printStackTrace();
            return new RunProcessResult(false, e.getMessage());
        }
    }

    @Override
    public StopProcessResult stopProcess(String executableName) {

        try {
            Process process = Runtime.getRuntime().exec(String.format("taskkill /f /im %s* /T", executableName)); // Needs admin rights
            String stdError = getStdError(process);

            if (!stdError.isEmpty()) {
                return new StopProcessResult(false, stdError);
            }

            return new StopProcessResult(true);
        } catch (IOException e) {
            e.printStackTrace();
            return new StopProcessResult(false, e.getMessage());
        }
    }

    private String getStdError(Process process) throws IOException {
        BufferedReader stdError = new BufferedReader(new InputStreamReader(process.getErrorStream()));
        StringBuilder stdErrorBuilder = new StringBuilder();
        String s;
        while ((s = stdError.readLine()) != null) {
            stdErrorBuilder.append(s);
        }
        return stdErrorBuilder.toString();
    }
}
