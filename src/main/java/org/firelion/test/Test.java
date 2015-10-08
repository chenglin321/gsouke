package org.firelion.test;

import org.apache.http.client.utils.URLEncodedUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by chenglin on 15-10-7.
 */
public class Test {

    public static  String str = "goagent 下载";
   public static void main(String[] args) throws UnsupportedEncodingException {
       String s1 = str.replaceAll("\\+","%2B").replaceAll(" ","%20");
       System.out.println(s1);
   }
}
