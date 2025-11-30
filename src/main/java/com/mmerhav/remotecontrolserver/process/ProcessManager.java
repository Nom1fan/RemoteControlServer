package com.mmerhav.remotecontrolserver.process;

import java.io.IOException;

public interface ProcessManager {

    Result runProcess(String executableName) throws IOException;

    Result stopProcess(String executableName);


}
