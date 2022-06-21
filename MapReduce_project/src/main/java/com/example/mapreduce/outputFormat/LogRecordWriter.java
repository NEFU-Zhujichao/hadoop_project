package com.example.mapreduce.outputFormat;

import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.RecordWriter;
import org.apache.hadoop.mapreduce.TaskAttemptContext;

import java.io.IOException;

public class LogRecordWriter extends RecordWriter<Text, NullWritable> {
    private FSDataOutputStream other;
    private FSDataOutputStream zhujichao;

    public LogRecordWriter(TaskAttemptContext job) {
        // 创建两条流
        try {
            FileSystem fs = FileSystem.get(job.getConfiguration());
            this.zhujichao = fs.create(new Path("D:\\BaiduNetdiskDownload\\Hadoop3.x\\资料\\hadoop_output\\output_customOutputFormat\\zhujichao.log"));
            this.other = fs.create(new Path("D:\\BaiduNetdiskDownload\\Hadoop3.x\\资料\\hadoop_output\\output_customOutputFormat\\other.log"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void write(Text key, NullWritable value) throws IOException, InterruptedException {
        if (key.toString().contains("atguigu")) {
            zhujichao.writeBytes(key.toString() + '\n');
        }else {
            other.writeBytes(key.toString() + '\n');
        }
    }

    @Override
    public void close(TaskAttemptContext context) throws IOException, InterruptedException {
        IOUtils.closeStreams(zhujichao,other);
    }
}
