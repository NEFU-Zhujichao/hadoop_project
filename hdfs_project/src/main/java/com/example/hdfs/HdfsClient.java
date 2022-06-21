package com.example.hdfs;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class HdfsClient {

    private FileSystem fs;

    @Before
    public void init() throws IOException, InterruptedException, URISyntaxException {
        // 集群的NameNode地址
        URI uri = new URI("hdfs://192.168.10.102:8020");
        // 配置文件
        // 参数优先级：代码中设置的值 > xxx-site.xml > xxx-default.xml
        Configuration configuration = new Configuration();
        configuration.set("dfs.replication", "2");
        String user = "zhujichao";
        // 获取客户端对象
        fs = FileSystem.get(uri, configuration,user);

    }

    @After
    public void close() throws IOException {
        // 关闭资源
        fs.close();
    }

    @Test
    public void mkdir() throws URISyntaxException, IOException, InterruptedException {
        // 创建一个文件夹
        fs.mkdirs(new Path("/xiyou/huaguoshan2"));
    }

    @Test
    public void put() throws IOException {
        fs.copyFromLocalFile(false,true,new Path("D:\\BaiduNetdiskDownload\\Hadoop3.x\\资料\\sunwukong.txt"),new Path("/xiyou/huaguoshan"));
    }

    @Test
    public void get() throws IOException {
        fs.copyToLocalFile(false,new Path("/xiyou/huaguoshan"),new Path("D:\\"),false);
    }

    @Test
    public void rm() throws IOException {
        fs.delete(new Path("/xiyou/huaguoshan"),true);
    }
}
