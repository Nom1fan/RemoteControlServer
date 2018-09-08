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
            return new RunProcessResult(false, String.format("File [%s] doesn't exist", executableAbsolutePath));
        }

        String fileName = file.getName();
        String dir = file.getParent();

        try {
            Runtime.getRuntime().exec(fileName, null, new File(dir));
        } catch (IOException e) {
            e.printStackTrace();
            return new RunProcessResult(false, e.getMessage());
        }

        return new RunProcessResult(true);
    }
}
