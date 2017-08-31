package com.market.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.market.utils.HttpUtils;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by daifei on 2017/8/31.
 */
public class TT {

    public static void main(String[] args) throws Exception {
        Set<String> platformSet = new HashSet<String>();
        List<String> lines = FileUtils.readLines(new File("C:\\Users\\wintech\\Desktop\\1111.txt"), "utf-8");
        for (String line : lines) {
            platform(platformSet, line);
        }
        System.out.println(platformSet);
    }

    private static void platform(Set<String> platformSet, String id) throws Exception {
        String url = "http://www.bicoin.info/before/add/addIndex/" + id;
        String response = HttpUtils.httpGetWithResponseString(url, null);
        String s = response.split("data1=")[1];
        JSONArray jsonArray = JSONArray.parseArray(s.split(";")[0]);
        for (int i=0;i<jsonArray.size();i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            platformSet.add(jsonObject.getString("text"));
        }
    }
}
