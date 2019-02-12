package com.example.jonathanstroz.backgroundnotificationreciever.Database;

import java.util.ArrayList;

public abstract class Table {

    private ArrayList<String> columns;
    private String tableName;
    private ArrayList<String> columnTypes;

    public Table(String name, ArrayList<String> cols){
        tableName = name;
        columns = copyArrayList(cols);
    }

    public Table(String name, String[] cols){
        tableName = name;
        columns = convertArray(cols);
    }

    public String toQuery(){
        String query = "SELECT * FROM "+tableName+";";
        return query;
    }

    public String toQuery(String field, String val){
        String query = "SELECT * FROM "+tableName+" WHERE "+field+" LIKE "+val+";";
        return query;
    }

    public String getCreateQuery(){
        String query = "CREATE TABLE "+tableName+ "(";

        if(columnTypes == null){
            query+=columns.get(0)+" TEXT NOT NULL";
            for(int i=1;i<columns.size();i++) {
                query += ", "+columns.get(i)+" TEXT NOT NULL";
            }
        }
        else{
            query+=columns.get(0)+" "+columnTypes.get(0);
            for(int i=1;i<columns.size();i++) {
                query += ", "+columns.get(i)+" "+columnTypes.get(i);
            }
        }
        query+= ");";

        return query;
    }

    public ArrayList<String> getColumns(){
        return copyArrayList(columns);
    }

    private ArrayList<String> copyArrayList(ArrayList<String> arr){
        ArrayList<String> copy = new ArrayList<String>();
        for(int i=0;i<arr.size();i++){
            copy.add(arr.get(i));
        }
        return copy;
    }

    public ArrayList<String> convertArray(String[] arr){
        ArrayList<String> newArr = new ArrayList<String>();

        for(int i=0; i<arr.length;i++){
            newArr.add(arr[i]);
        }

        return newArr;
    }
}
