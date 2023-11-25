package com.onlyone.crm.workbench.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Author 临渊
 * @Date 2023-11-25 15:33
 */

@Controller
public class MainController {

    @RequestMapping("/workbench/main/index")
    public String index() {
        return "workbench/main/index";
    }

}
