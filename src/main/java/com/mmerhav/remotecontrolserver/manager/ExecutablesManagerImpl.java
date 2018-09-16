package com.mmerhav.remotecontrolserver.manager;

import java.util.HashMap;
import java.util.Map;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties
public class ExecutablesManagerImpl implements ExecutablesManager {

    @Getter
    @Setter
    private Map<String,String> executables = new HashMap<>();

    @Override
    public String getExecutableAbsolutePath(String execName) {
        return executables.get(execName.toUpperCase());
    }

    @Override
    public boolean isValidExecutable(String execName) {
        return executables.containsKey(execName.toUpperCase());
    }


}
