package com.mmerhav.remotecontrolserver.controller;

import com.mmerhav.remotecontrolserver.logic.RemoteControlLogic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class RemoteControlController {

    @Autowired
    private RemoteControlLogic logic;

    @RequestMapping(value = "/v1/model", method = RequestMethod.GET)
    public String model(Model model) {
        model.addAttribute("testAtt", "model");
        return "model";
    }

    @RequestMapping(value = "/v1/modelMap", method = RequestMethod.GET)
    public String modelMap(ModelMap modelMap) {
        modelMap.put("testAtt", "modelMap");
        return "modelMap";
    }

    @RequestMapping(value = "/v1/modelAndView", method = RequestMethod.GET)
    public ModelAndView modelAndView() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("modelAndView");
        modelAndView.addObject("testAtt", "modelAndView");
        return modelAndView;
    }

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
