package com.example.mapreduce.reduceJoin;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.InputSplit;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;

import java.io.IOException;

public class TableMapper extends Mapper<LongWritable, Text, Text, TableBean> {

    private String fileName;
    private Text outK = new Text();
    private TableBean outV = new TableBean();

    @Override
    protected void setup(Mapper<LongWritable, Text, Text, TableBean>.Context context) throws IOException, InterruptedException {
        FileSplit inputSplit = (FileSplit) context.getInputSplit();
        fileName = inputSplit.getPath().getName();
    }

    @Override
    protected void map(LongWritable key, Text value, Mapper<LongWritable, Text, Text, TableBean>.Context context) throws IOException, InterruptedException {
        String line = value.toString();
        if (fileName.contains("order")) {
            String[] split = line.split("\t");
            outK.set(split[1]);
            outV.setId(split[0]);
            outV.setProductId(split[1]);
            outV.setAmount(Integer.parseInt(split[2]));
            outV.setProductName("");
            outV.setTableName("order");
        } else if (fileName.contains("pd")) {
            String[] split = line.split("\t");
            outK.set(split[0]);
            outV.setId("");
            outV.setProductId(split[0]);
            outV.setAmount(0);
            outV.setProductName(split[1]);
            outV.setTableName("pd");
        }

        context.write(outK, outV);
    }
}
