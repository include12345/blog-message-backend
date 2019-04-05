package com.lihebin.blog.service;

import com.lihebin.blog.bean.Result;
import org.apache.commons.collections.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.Map;

/**
 * Created by lihebin on 2019/4/4.
 */
@Service
public class FileServiceImpl implements FileService{
    private final Logger log = LoggerFactory.getLogger(getClass());
    @Value("${url.picture}")
    private String pictureUrl;


    @Value("${url.login.email}")
    private String loginEmail;

    @Value("${url.login.password}")
    private String loginPassword;

    private static final String CODE = "code";
    private static final String CODE_OK = "OK";
    private static final String DATA = "data";
    private static final String DATA_UUID = "uuid";

    private static final String EMAIL = "email";
    private static final String PASSWORD = "password";

    private static final String UPLOAD_USER_UUID = "userUuid";
    private static final String UPLOAD_ALIEN = "alien";
    private static final String UPLOAD_PUUID = "puuid";
    private static final String UPLOAD_FILE = "file";

    private static final Boolean UPLOAD_ALIEN_DEFAULT = false;
    private static final String UPLOAD_PUUID_DEFAULT = "root";

    @Autowired
    private RestTemplate restTemplate;




    @Override
    public Result uploadPicture(MultipartFile file) {
        Result result = new Result();
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add(EMAIL, loginEmail);
        map.add(PASSWORD, loginPassword);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        HttpEntity<MultiValueMap<String, String>> formEntity = new HttpEntity<>(map, headers);
        Map loginResult = restTemplate.postForObject(String.format("%s/api/user/login", pictureUrl), formEntity, Map.class);
        String code = MapUtils.getString(loginResult, CODE);
        if (!CODE_OK.equalsIgnoreCase(code)) {
            log.error("uploadPicture: {},{}", "登录失败", loginResult);
            result.setCode(Result.FAIL_CODE);
            result.setMessage("上传文件失败");
            return result;
        }
        String uuid = MapUtils.getString(MapUtils.getMap(loginResult, DATA), DATA_UUID);
        File tempFile = multipartFileToFile(file);
        if (null == tempFile) {
            log.error("uploadPicture: {},{}", "转换文件失败", file.getName());
            result.setCode(Result.FAIL_CODE);
            result.setMessage("上传文件失败");
            return result;
        }
        FileSystemResource resource = new FileSystemResource(tempFile);
        MultiValueMap<String, Object> param = new LinkedMultiValueMap<>();
        param.add(UPLOAD_FILE, resource);
        param.add(UPLOAD_USER_UUID, uuid);
        param.add(UPLOAD_PUUID, UPLOAD_PUUID_DEFAULT);
        param.add(UPLOAD_ALIEN, UPLOAD_ALIEN_DEFAULT);
        HttpHeaders headersUpload = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        HttpEntity<MultiValueMap<String, Object>> uploadEntity = new HttpEntity<>(param, headersUpload);
        Map uploadResult = restTemplate.postForObject(String.format("%s/api/matter/upload", pictureUrl), uploadEntity, Map.class);
        return null;
    }


    private static File multipartFileToFile(MultipartFile multiFile) {
        // 获取文件名
        String fileName = multiFile.getOriginalFilename();
        // 获取文件后缀
        String prefix = fileName.substring(fileName.lastIndexOf("."));
        // 用当前时间作为文件名，防止生成的临时文件重复
        try {
            File file = File.createTempFile(System.currentTimeMillis() + "", prefix);

            multiFile.transferTo(file);
            return file;
        } catch (Exception e) {
//            log.error("multipartFileToFile: {}", fileName, e);
        }
        return null;
    }

    public static void main(String[] args) {
        RestTemplate test = new RestTemplate();
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("email", "include_lihebin@163.com");
        map.add("password", "include155121214");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        HttpEntity<MultiValueMap<String, String>> formEntity = new HttpEntity<>(map, headers);
        ResponseEntity<Map> loginResult = test.postForEntity("http://47.95.208.59:6010/api/user/login", formEntity, Map.class);
        String cookie = loginResult.getHeaders().getFirst(HttpHeaders.SET_COOKIE);
        String code = MapUtils.getString(loginResult.getBody(), CODE);
        if (!CODE_OK.equalsIgnoreCase(code)) {
            return;
        }
        String uuid = MapUtils.getString(MapUtils.getMap(loginResult.getBody(), DATA), DATA_UUID);
        FileSystemResource resource = new FileSystemResource(new File("/Users/lihebin/Desktop/bg.jpg"));
        MultiValueMap<String, Object> param = new LinkedMultiValueMap<>();
        param.add(UPLOAD_FILE, resource);
        param.add(UPLOAD_USER_UUID, uuid);
        param.add(UPLOAD_PUUID, UPLOAD_PUUID_DEFAULT);
        param.add(UPLOAD_ALIEN, UPLOAD_ALIEN_DEFAULT);
        HttpHeaders headersUpload = new HttpHeaders();
        headersUpload.setContentType(MediaType.MULTIPART_FORM_DATA);
        headersUpload.add(HttpHeaders.COOKIE, cookie);
//        headersUpload.setCacheControl("no-cache");
        HttpEntity<MultiValueMap<String, Object>> uploadEntity = new HttpEntity<>(param, headersUpload);
        Map upload = test.postForObject("http://47.95.208.59:6010/api/matter/upload", uploadEntity, Map.class);

        System.out.println(upload);
    }
}
