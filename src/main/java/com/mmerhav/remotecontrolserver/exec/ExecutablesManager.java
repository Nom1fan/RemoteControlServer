package com.mmerhav.remotecontrolserver.exec;

public interface ExecutablesManager {

    String getExecutableAbsolutePath(String execName);

    boolean isValidExecutable(String execName);
}
