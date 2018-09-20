package com.mmerhav.remotecontrolserver.manager;

public interface ProcessManager {

    RunProcessResult runProcess(String executableAbsolutePath);

    StopProcessResult stopProcess(String executableName);


}
