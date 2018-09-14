package com.mmerhav.remotecontrolserver.runner;

public interface ProcessManager {

    RunProcessResult runProcess(String executableAbsolutePath);

    StopProcessResult stopProcess(String executableName);


}
