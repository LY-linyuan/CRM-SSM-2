package com.onlyone.crm.workbench.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Author 临渊
 * @Date 2023-11-25 9:18
 */

@Controller
public class WorkBenchIndexController {

    @RequestMapping("/workbench/index")
    public String index() {
        return "workbench/index";
    }

}
