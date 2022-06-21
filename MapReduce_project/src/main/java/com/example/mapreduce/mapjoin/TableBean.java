package com.example.mapreduce.mapjoin;

import org.apache.hadoop.io.Writable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class TableBean implements Writable {

    private String id;
    private String productId;
    private int amount;
    private String productName;
    private String tableName;

    public TableBean() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    @Override
    public void write(DataOutput dataOutput) throws IOException {
        dataOutput.writeUTF(id);
        dataOutput.writeUTF(productId);
        dataOutput.writeInt(amount);
        dataOutput.writeUTF(productName);
        dataOutput.writeUTF(tableName);
    }

    @Override
    public void readFields(DataInput dataInput) throws IOException {
        this.id = dataInput.readUTF();
        this.productId = dataInput.readUTF();
        this.amount = dataInput.readInt();
        this.productName = dataInput.readUTF();
        this.tableName = dataInput.readUTF();
    }

    @Override
    public String toString() {
        return id + "\t" + productName + "\t" + amount;
    }
}
