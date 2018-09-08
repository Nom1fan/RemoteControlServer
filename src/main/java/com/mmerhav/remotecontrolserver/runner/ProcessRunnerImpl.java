package com.mmerhav.remotecontrolserver.runner;

import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;

@Component
public class ProcessRunnerImpl implements ProcessRunner {


    @Override
    public RunProcessResult runProcess(String executableAbsolutePath) {
        File file = new File(executableAbsolutePath);

        if (!file.exists()) {
            return new RunProcessResult(false, null, String.format("File [%s] doesn't exist", executableAbsolutePath));
        }

        String dir = file.getParent();

        try {
            //TODO Must detect if process is already running and not run it again
            Process process = Runtime.getRuntime().exec(executableAbsolutePath, null, new File(dir));
            return new RunProcessResult(true, process);
        } catch (IOException e) {
            e.printStackTrace();
            return new RunProcessResult(false, null, e.getMessage());
        }
    }
}
