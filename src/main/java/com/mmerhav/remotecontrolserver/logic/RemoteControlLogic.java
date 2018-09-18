package com.mmerhav.remotecontrolserver.logic;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface RemoteControlLogic {

    String startProcess(String execName, HttpServletResponse response) throws IOException;

    String stopProcess(String execName, HttpServletResponse response) throws IOException;
}
