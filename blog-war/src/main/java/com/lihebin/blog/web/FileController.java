package com.lihebin.blog.web;

import com.lihebin.blog.bean.Result;
import com.lihebin.blog.service.FileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


/**
 * Created by lihebin on 2019/4/4.
 */
@RestController
public class FileController {

    private final static Logger log = LoggerFactory.getLogger(FileController.class);
    @Autowired
    private FileService fileService;

    @RequestMapping(value = "/api/file/uploadPicture", method = RequestMethod.POST)
    @ResponseBody
    public Result uploadPicture(@RequestParam("file") MultipartFile file) {
        return fileService.uploadPicture(file);
    }

    @RequestMapping(value = "/file/uploadPictureToken",  method = RequestMethod.GET)
    @ResponseBody
    public Result uploadPictureToken(@RequestParam(value = "filename", required = true) String filename, @RequestParam(value = "size", required = true) String size) {
        return fileService.uploadPictureToken(filename, size);
    }
}
