package com.mmerhav.remotecontrolserver.manager;

import java.util.Set;

public interface ExecutablesManager {

    String getExecutableAbsolutePath(String execName);

    boolean isValidExecutable(String execName);
}
