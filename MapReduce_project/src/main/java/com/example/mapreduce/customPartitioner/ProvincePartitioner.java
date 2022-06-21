package com.example.mapreduce.customPartitioner;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Partitioner;

public class ProvincePartitioner extends Partitioner<Text, FlowBean> {
    @Override
    public int getPartition(Text text, FlowBean flowBean, int numPartitions) {
        String key = text.toString().substring(0, 3);
        int partition = 0;
        if ("136".equals(key)) {
            partition = 0;
        } else if ("137".equals(key)) {
            partition = 1;
        } else if ("138".equals(key)) {
            partition = 2;
        } else if ("139".equals(key)) {
            partition = 3;
        } else {
            partition = 4;
        }
        return partition;
    }
}
