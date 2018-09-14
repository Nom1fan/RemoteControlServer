package com.mmerhav.remotecontrolserver.manager;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.apache.commons.io.IOUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.nio.charset.Charset;
import java.util.Map;

@Component
public class ExecutablesManagerImpl implements ExecutablesManager {

    private Map<String,String> name2ExecMap;

    @PostConstruct
    public void init() throws IOException {
        ClassPathResource classPathResource = new ClassPathResource("name2Exec.json");
        try (InputStream inputStream = classPathResource.getInputStream()) {
            String data = IOUtils.toString(inputStream, Charset.forName("UTF8"));
            Type type = new TypeToken<Map<String, String>>(){}.getType();
            name2ExecMap = new Gson().fromJson(data, type);
        }
    }

    @Override
    public String getExecutableAbsolutePath(String execName) {
        return name2ExecMap.get(execName.toUpperCase());
    }

    @Override
    public boolean isValidExecutable(String execName) {
        return name2ExecMap.containsKey(execName.toUpperCase());
    }


}
