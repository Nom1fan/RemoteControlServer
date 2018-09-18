package com.mmerhav.remotecontrolserver.controller;

import com.mmerhav.remotecontrolserver.logic.RemoteControlLogic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class RemoteControlController {

    @Autowired
    private RemoteControlLogic logic;

    @RequestMapping(value = "/v1/runProcess/{execName}", method = RequestMethod.GET)
    @ResponseBody
    public String runProcess(@PathVariable String execName, HttpServletResponse response) throws IOException {
        return logic.startProcess(execName, response);
    }

    @RequestMapping(value = "/v1/stopProcess/{execName}", method = RequestMethod.GET)
    @ResponseBody
    public String stopProcess(@PathVariable String execName, HttpServletResponse response) throws IOException {
        return logic.stopProcess(execName, response);
    }

}
