package com.xuecheng.manage_cms.dao;

import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSDownloadStream;
import com.mongodb.client.gridfs.model.GridFSFile;
import com.xuecheng.manage_cms.config.MongoConfig;
import org.apache.commons.io.IOUtils;
import org.bson.types.ObjectId;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.io.*;
import java.util.Map;

@SpringBootTest
@RunWith(SpringRunner.class)
public class GridFSTest {


    @Autowired
    GridFsTemplate gridFsTemplate;//分布式文件存储模板类
    @Autowired
    GridFSBucket gridFSBucket;//取模板文件的对象


    @Test       //存模板文件
    public void testGridFsTemplate() throws FileNotFoundException {
        //定义file
        File file = new File("G:\\griFS\\index_banner.ftl");
        //定义fileInputstream流，用于文件传输
        FileInputStream fileInputStream=new FileInputStream(file);
        //向文件系统存储文件
        ObjectId store = gridFsTemplate.store(fileInputStream, "index_banner.ftl");
        //得到文件ID
        String fileId = store.toString();

    }
    //取出模板文件
    @Test
    public void queryFile() throws IOException {
        String fileId = "5d7e95bfe02f4630e0afdaaa";
        //根据id查询文件
        GridFSFile gridFSFile = gridFsTemplate.findOne(Query.query(Criteria.where("_id").is(fileId)));
        //打开下载流对象
        GridFSDownloadStream gridFSDownloadStream = gridFSBucket.openDownloadStream(gridFSFile.getObjectId());
        //创建gridFsResource，用于获取流对象
        GridFsResource gridFsResource = new GridFsResource(gridFSFile,gridFSDownloadStream);
        //获取流中的数据
        String s = IOUtils.toString(gridFsResource.getInputStream(), "UTF-8");
        System.out.println(s);
    }


    //删除文件
    @Test
    public void testDelFile() throws IOException {
        //根据文件id删除fs.files和fs.chunks中的记录
        gridFsTemplate.delete(Query.query(Criteria.where("_id").is("5b32480ed3a022164c4d2f92")));
    }
    //得到的模板Id就是文件ID,模板id在实际存储的文件中根据文件id查询文件

}
