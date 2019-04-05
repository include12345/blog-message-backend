package com.lihebin.blog.service;

import com.lihebin.blog.annotation.BackendAnnotation;
import com.lihebin.blog.bean.Result;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

/**
 * Created by lihebin on 2019/4/4.
 */
@BackendAnnotation
@Validated
public interface FileService {
    /**
     * 上传图片至云存储，获取返回的url
     * @param file
     * @return
     */
    Result uploadPicture(MultipartFile file);
}
