package xmu.ringoer.myzone.news.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xmu.ringoer.myzone.news.service.NewsService;

/**
 * @author Ringoer
 */
@RestController
@RequestMapping(value = "", produces = {"application/json;charset=UTF-8"})
public class NewsController {

    @Autowired
    private NewsService newsService;

    @GetMapping("/elsInfo")
    public Object getElsInfo() {
        return newsService.getElsInfo();
    }
}
