package com.xuecheng.manage_cms.dao;


import com.xuecheng.manage_cms.service.PageService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class PageServiceTest {

    @Autowired
    PageService pageService;
    @Test
    public void testGetPageHtml(){
        String pageHtml = pageService.getPageHtml("5d7ea6f3e02f460774c4a47f");
        System.out.println(pageHtml);


    }
}
