package com.my.kotlin.system.service.impl;

import com.alibaba.fastjson.JSON;
import com.my.kotlin.system.config.query.QueryHelp;
import com.my.kotlin.system.entity.QuestionInfo;
import com.my.kotlin.system.repository.QuestionInfoRepo;
import com.my.kotlin.system.service.QuestionInfoService;
import com.my.kotlin.system.service.query.QuestionInfoQuery;
import com.my.kotlin.util.FileUtil;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;

@Service
@AllArgsConstructor
public class QuestionInfoServiceImpl implements QuestionInfoService {

    private final QuestionInfoRepo questionInfoRepo;

    @Override
    public Page<QuestionInfo> query(QuestionInfoQuery questionInfoQuery, Pageable pageable) {
        return questionInfoRepo.findAll(((root, query, criteriaBuilder) -> QueryHelp.getPredicate(root, questionInfoQuery, criteriaBuilder)),pageable);
    }

    @Override
    public QuestionInfo save(QuestionInfo questionInfo) {
        if (questionInfo.getId() == null) {
            questionInfo.setCreateTime(Timestamp.from(Instant.now()));
        }
        return questionInfoRepo.save(questionInfo);
    }

    @Override
    public void update(QuestionInfo questionInfo) {
        if (questionInfo.getId() == null) {
            questionInfo.setCreateTime(Timestamp.from(Instant.now()));
        }
        questionInfoRepo.save(questionInfo);
    }

    @Override
    public void del(Integer id) {
        questionInfoRepo.deleteById(id);
    }

    @Override
    public String upload(MultipartFile file) {
        // 构造一个带指定Zone对象的配置类
        String accessKey = "70YewzuUXInvSUGxf6xR78PLQl09X41hulyu9m1m";
        String secretKey = "9EyT9RJuE1m07UowZCaHcUcxTVwfzZAAw7BqjcPw";
        //设置地区

        Configuration cfg = new Configuration(Region.huanan());
        UploadManager uploadManager = new UploadManager(cfg);
        Auth auth = Auth.create(accessKey, secretKey);

        try {
            String key = file.getOriginalFilename();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
            Date date = new Date();
            String s = FileUtil.getFileNameNoEx(key) + "-" +
                    sdf.format(date) +
                    "." +
                    FileUtil.getExtensionName(key);

            String upToken = auth.uploadToken("pangjie");
            Response response = uploadManager.put(file.getBytes(), key, upToken);
            //解析上传成功的结果

            DefaultPutRet putRet = JSON.parseObject(response.bodyString(), DefaultPutRet.class);


            String domainOfBucket = "pangjie";
            String encodedFileName = URLEncoder.encode(s, "utf-8").replace("+", "%20");
            String publicUrl = String.format("%s/%s", domainOfBucket, encodedFileName);

//            long expireInSeconds = 3600;//1小时，可以自定义链接过期时间

            return auth.privateDownloadUrl("http://image.pangjie1995.cn/" + putRet.key);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
