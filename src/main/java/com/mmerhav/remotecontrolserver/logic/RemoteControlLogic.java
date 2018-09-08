package com.mmerhav.remotecontrolserver.logic;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface RemoteControlLogic {

    void startProcess(String execName, HttpServletResponse response) throws IOException;

    void stopProcess(String execName, HttpServletResponse response) throws IOException;
}
