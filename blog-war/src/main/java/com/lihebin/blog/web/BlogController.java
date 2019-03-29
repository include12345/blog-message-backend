package com.lihebin.blog.web;

import com.lihebin.blog.bean.*;
import com.lihebin.blog.dao.CacheDao;
import com.lihebin.blog.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by lihebin on 2019/03/24.
 */
@RestController
public class BlogController {

    @Autowired
    private BlogService blogService;

    @Autowired
    private CacheDao cacheDao;

    @RequestMapping(value = "/blog/getBlog", method = RequestMethod.GET)
    public Article getBlog(@RequestParam(value = "id", required = true) String id) {
        return blogService.getBlog(id);
    }

    @RequestMapping(value = "/blog/getPreNextBlog", method = RequestMethod.GET)
    public PreNext getPreNextBlog(@RequestParam(value = "id", required = true) String id) {
        return blogService.getPreNextBlog(id);
    }

    @RequestMapping(value = "/blog/listBlog", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    public Articles listBlog(@RequestBody Articles articles) {
        return blogService.listBlog(articles);
    }

    @RequestMapping(value = "/api/blog/updateBlog", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public Article updateBlog(@RequestBody Article article, HttpServletRequest request) {
        String token = request.getHeader(User.TOKEN);
        String[] param = token.split("-");
        String username = cacheDao.getValue(param[0]);
        article.setUpdateAuthorId(username);
        return blogService.updateBlog(article);
    }


    @RequestMapping(value = "/api/blog/deleteBlog",  method = RequestMethod.GET)
    @ResponseBody
    public void deleteBlog(@RequestParam(value = "id", required = true) String id) {
        blogService.deleteBlog(id);
    }


    @RequestMapping(value = "/blog/getClassify",  method = RequestMethod.GET)
    @ResponseBody
    public List<String> getClassify() {
        return blogService.getClassify();
    }


    @RequestMapping(value = "/api/blog/createBlog", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public Article createBlog(@RequestBody Article article, HttpServletRequest request) {
        String token = request.getHeader(User.TOKEN);
        String[] param = token.split("-");
        String username = cacheDao.getValue(param[0]);
        article.setCreateAuthorId(username);
        article.setUpdateAuthorId(username);
        return blogService.createBlog(article);
    }


}
