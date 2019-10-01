package com.imall.page.controller;

import com.imall.page.service.PageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@Controller
public class PageController {

    @Autowired
    private PageService pageService;

    @RequestMapping("/item/{id}.html")
    public String page(@PathVariable("id") Long id, Model model){
        model.addAllAttributes(pageService.loadModel(id));
        return "item";
    }
}
