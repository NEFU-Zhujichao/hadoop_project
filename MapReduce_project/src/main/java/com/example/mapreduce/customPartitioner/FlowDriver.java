package com.example.mapreduce.customPartitioner;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;

public class FlowDriver {
    public static void main(String[] args) throws IOException, InterruptedException, ClassNotFoundException {

        Configuration configuration = new Configuration();
        Job job = Job.getInstance(configuration);
        job.setJarByClass(FlowDriver.class);
        job.setMapperClass(FlowMapper.class);
        job.setReducerClass(FlowReducer.class);
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(FlowBean.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(FlowBean.class);
        // 设置自定义分区策略类
        job.setPartitionerClass(ProvincePartitioner.class);
        // 设置reduceTask
        // 设置为1 走默认的内部类getPartitioner(){ return partitions - 1;}
        // 设置1 < num < 5 IO异常 非法分区数
        // 设置 num > 5 正常允许 输出目录多了 (num - 5) 个空文件
        job.setNumReduceTasks(5);
        FileInputFormat.addInputPath(job, new Path("D:\\BaiduNetdiskDownload\\Hadoop3.x\\资料\\11_input\\inputflow"));
        FileOutputFormat.setOutputPath(job, new Path("D:\\BaiduNetdiskDownload\\Hadoop3.x\\资料\\hadoop_output\\output_customPartitioner"));
        System.exit(job.waitForCompletion(true) ? 0 : 1);
    }
}
