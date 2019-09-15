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
    GridFsTemplate gridFsTemplate;//�ֲ�ʽ�ļ��洢ģ����
    @Autowired
    GridFSBucket gridFSBucket;//ȡģ���ļ��Ķ���


    @Test       //��ģ���ļ�
    public void testGridFsTemplate() throws FileNotFoundException {
        //����file
        File file = new File("G:\\griFS\\index_banner.ftl");
        //����fileInputstream���������ļ�����
        FileInputStream fileInputStream=new FileInputStream(file);
        //���ļ�ϵͳ�洢�ļ�
        ObjectId store = gridFsTemplate.store(fileInputStream, "index_banner.ftl");
        //�õ��ļ�ID
        String fileId = store.toString();

    }
    //ȡ��ģ���ļ�
    @Test
    public void queryFile() throws IOException {
        String fileId = "5d7e95bfe02f4630e0afdaaa";
        //����id��ѯ�ļ�
        GridFSFile gridFSFile = gridFsTemplate.findOne(Query.query(Criteria.where("_id").is(fileId)));
        //������������
        GridFSDownloadStream gridFSDownloadStream = gridFSBucket.openDownloadStream(gridFSFile.getObjectId());
        //����gridFsResource�����ڻ�ȡ������
        GridFsResource gridFsResource = new GridFsResource(gridFSFile,gridFSDownloadStream);
        //��ȡ���е�����
        String s = IOUtils.toString(gridFsResource.getInputStream(), "UTF-8");
        System.out.println(s);
    }


    //ɾ���ļ�
    @Test
    public void testDelFile() throws IOException {
        //�����ļ�idɾ��fs.files��fs.chunks�еļ�¼
        gridFsTemplate.delete(Query.query(Criteria.where("_id").is("5b32480ed3a022164c4d2f92")));
    }
    //�õ���ģ��Id�����ļ�ID,ģ��id��ʵ�ʴ洢���ļ��и����ļ�id��ѯ�ļ�

}
