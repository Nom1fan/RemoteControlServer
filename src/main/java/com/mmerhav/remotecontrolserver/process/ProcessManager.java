package com.mmerhav.remotecontrolserver.process;

public interface ProcessManager {

    RunProcessResult runProcess(String executableAbsolutePath);

    StopProcessResult stopProcess(String executableName);


}
