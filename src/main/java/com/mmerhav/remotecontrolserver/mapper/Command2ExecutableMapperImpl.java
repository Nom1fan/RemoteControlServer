package com.mmerhav.remotecontrolserver.mapper;

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
public class Command2ExecutableMapperImpl implements Command2ExecutableMapper {

    private Map<String,String> cmd2ExecMap;

    @PostConstruct
    public void init() throws IOException {
        ClassPathResource classPathResource = new ClassPathResource("cmd2Exec.json");
        try (InputStream inputStream = classPathResource.getInputStream()) {
            String data = IOUtils.toString(inputStream, Charset.forName("UTF8"));
            Type type = new TypeToken<Map<String, String>>(){}.getType();
            cmd2ExecMap = new Gson().fromJson(data, type);
        }
    }

    @Override
    public String getExecutableAbsolutePath(String command) {
        if (!cmd2ExecMap.containsKey(command)) {
            throw new CommandNotFoundException(command);
        }
        return cmd2ExecMap.get(command);
    }
}
