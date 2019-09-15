package com.xuecheng.test.freemarker.controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;


import java.util.Map;



@RequestMapping("/freemarker")
@Controller
public class FreemarkerController{

    @Autowired
    RestTemplate restTemplate;

    @RequestMapping("/banner")
    public String index_banner(Map<String, Object> map) {
            //准备使用restTemplate发送请求获取轮播图数据模型
        ResponseEntity<Map> forEntity = restTemplate.getForEntity("http://localhost:31001/cms/config/getmodel/5a791725dd573c3574ee333f", Map.class);
        Map body = forEntity.getBody();
        //设置模型数据
        map.putAll(body);
        return "index_banner";

    }
    @RequestMapping("/test1")
    public String test1(Map<String, Object> map){
        //将数据填入map
        map.put("name","子龙MC");
        return "test1";



    }

    }
