package com.example.mapreduce.reduceJoin;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class TableReducer extends Reducer<Text, TableBean, TableBean, NullWritable> {

    private TableBean outK = new TableBean();

    @Override
    protected void reduce(Text key, Iterable<TableBean> values, Reducer<Text, TableBean, TableBean, NullWritable>.Context context) throws IOException, InterruptedException {
        List<TableBean> orderList = new ArrayList<>();
        TableBean product = new TableBean();
        for (TableBean value : values) {

            TableBean temp = new TableBean();
            if ("order".equals(value.getTableName())) {
                try {
                    BeanUtils.copyProperties(temp, value);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
                orderList.add(temp);
            } else if ("pd".equals(value.getTableName())) {
                try {
                    BeanUtils.copyProperties(product, value);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
        for (TableBean tableBean : orderList) {
            tableBean.setProductName(product.getProductName());
            context.write(tableBean,NullWritable.get());
        }
    }
}
