package com.mmerhav.remotecontrolserver.controller;

import com.mmerhav.remotecontrolserver.process.ProcessManager;
import com.mmerhav.remotecontrolserver.process.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;

@Controller
public class RemoteControlController {

    @Autowired
    private ProcessManager processManager;

    @RequestMapping(value = "/v1/runProcess/{execName}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<String> runProcess(@PathVariable String execName) throws IOException {
        Result result = processManager.runProcess(execName);
        return ResponseEntity.status(result.statusCode())
                .body(result.msg());
    }

    @RequestMapping(value = "/v1/stopProcess/{execName}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<String> stopProcess(@PathVariable String execName) {
        Result result = processManager.stopProcess(execName);
        return ResponseEntity.status(result.statusCode())
                .body(result.msg());
    }
}