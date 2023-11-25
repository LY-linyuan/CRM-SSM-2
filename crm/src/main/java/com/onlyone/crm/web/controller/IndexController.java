package com.onlyone.crm.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Author 临渊
 * @Date 2023-11-24 14:17
 */

@Controller
public class IndexController {

    @RequestMapping("/")
    public String index() {
        return "index";
    }


}
