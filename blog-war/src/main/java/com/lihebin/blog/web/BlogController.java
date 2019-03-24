package com.lihebin.blog.web;

import com.lihebin.blog.bean.*;
import com.lihebin.blog.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by lihebin on 2019/03/24.
 */
@RestController
public class BlogController {

    @Autowired
    private BlogService blogService;

    @RequestMapping(value = "/blog/getBlog", method = RequestMethod.GET)
    public Article getBlog(@RequestParam(value = "id", required = true) String id) {
        return blogService.getBlog(id);
    }

    @RequestMapping(value = "/blog/listBlog", method = RequestMethod.GET)
    public Articles listBlog(@RequestBody Articles articles) {
        return blogService.listBlog(articles);
    }

    @RequestMapping(value = "/api/blog/updateBlog", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public Article updateBlog(@RequestBody Article article) {
        return blogService.updateBlog(article);
    }


    @RequestMapping(value = "/api/blog/updateBlog",  method = RequestMethod.GET)
    @ResponseBody
    public void deleteBlog(@RequestParam(value = "id", required = true) String id) {
        blogService.deleteBlog(id);
    }


    @RequestMapping(value = "/api/blog/createBlog", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public Article createBlog(@RequestBody Article article) {
        return blogService.createBlog(article);
    }


}
