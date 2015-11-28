package org.firelion.test;

import org.apache.http.client.utils.URLEncodedUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by chenglin on 15-10-7.
 */
public class Test {

    public static  String str = "background:url(//ssl.gstatic.com/gb/images/b_8d5afc09.png);goagent 下载";
   public static void main(String[] args) throws UnsupportedEncodingException {
       String s1 = str.replaceAll("background:url\\(//ssl.gstatic.com/gb/images/b_8d5afc09.png\\);","");
       System.out.println(s1);
   }
}
