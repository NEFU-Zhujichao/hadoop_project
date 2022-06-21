package com.example.mapreduce.writableComparable;

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
        job.setMapOutputKeyClass(FlowBean.class);
        job.setMapOutputValueClass(Text.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(FlowBean.class);

        FileInputFormat.addInputPath(job,new Path("D:\\BaiduNetdiskDownload\\Hadoop3.x\\资料\\hadoop_output\\output1\\part-r-00000"));
        FileOutputFormat.setOutputPath(job,new Path("D:\\BaiduNetdiskDownload\\Hadoop3.x\\资料\\hadoop_output\\output_writableComparable_1"));
        System.exit(job.waitForCompletion(true) ? 0 : 1);
    }
}
