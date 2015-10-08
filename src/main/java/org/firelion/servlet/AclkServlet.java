package org.firelion.servlet;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by chenglin on 15-10-6.
 * 处理google广告 aclk
 */
public class AclkServlet extends HttpServlet {


    public static  String URI = "https://www.google.com.hk/search?newwindow=1&safe=strict&site=&source=hp&q=";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //URLencode不能处理 ‘+’和‘空格’
        String url = req.getParameter("adurl");
        //google搜索的起始页参数
        String start = req.getParameter("start");
        String ei = req.getParameter("ei");

        //header
        String agent = req.getHeader("User-Agent");
        String acceptLanguage = req.getHeader("Accept-Language");


        boolean mobile = isMobile(agent);


        System.out.println("==============================");
        System.out.println("请求参数url:" + url);
        System.out.println("请求参数agent:" + agent);
        System.out.println("请求参数isMobile:" + mobile);
        System.out.println("==============================");

        resp.sendRedirect(url);
    }


    /**
     * 判断是否手机端请求
     * @param agent
     * @return
     */
    private boolean isMobile(String agent) {
        boolean isAndroid =  agent.indexOf("Android") != -1 ;
        boolean isIPhone =  agent.indexOf("iPhone") != -1 ;
        return isAndroid || isIPhone ;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req,resp);
    }
}
