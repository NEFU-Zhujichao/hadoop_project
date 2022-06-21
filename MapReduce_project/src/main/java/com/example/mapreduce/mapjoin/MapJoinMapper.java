package com.example.mapreduce.mapjoin;

import org.apache.commons.lang3.StringUtils;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

public class MapJoinMapper extends Mapper<LongWritable, Text, Text, NullWritable> {

    private Text outK = new Text();
    private TableBean tableBean = new TableBean();
    private Map<String, String> map = new HashMap<>();

    @Override
    protected void setup(Mapper<LongWritable, Text, Text, NullWritable>.Context context) throws IOException, InterruptedException {

        URI[] cacheFiles = context.getCacheFiles();
        FileSystem fs = FileSystem.get(context.getConfiguration());
        FSDataInputStream dataInputStream = fs.open(new Path(cacheFiles[0]));
        BufferedReader reader = new BufferedReader(new InputStreamReader(dataInputStream, "UTF-8"));
        String line;
        while (StringUtils.isNotEmpty(line = reader.readLine())) {
            String[] split = line.split("\t");
            map.put(split[0], split[1]);
        }
        IOUtils.closeStreams(reader);
    }

    @Override
    protected void map(LongWritable key, Text value, Mapper<LongWritable, Text, Text, NullWritable>.Context context) throws IOException, InterruptedException {
        String[] split = value.toString().split("\t");
        String productId = split[1];
        String productName = map.get(productId);
        tableBean.setId(split[0]);
        tableBean.setProductId(productId);
        tableBean.setAmount(Integer.parseInt(split[2]));
        tableBean.setProductName(productName);
        outK.set(tableBean.toString());
        context.write(outK, NullWritable.get());
    }
}
