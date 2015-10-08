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
import java.net.URLEncoder;

/**
 * Created by chenglin on 15-10-6.
 * 处理搜索页面
 */
public class SearchServlet extends HttpServlet {


    public static  String URI = "https://www.google.com.hk/search?newwindow=1&safe=strict&site=&source=hp&q=";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //URLencode不能处理 ‘+’和‘空格’
        String q = URLEncoder.encode(req.getParameter("q"),"UTF-8").replaceAll("\\+","%2B").replaceAll(" ", "%20");
        //google搜索的起始页参数
        String start = req.getParameter("start");
        String ei = req.getParameter("ei");

        //header
        String agent = req.getHeader("User-Agent");
        String acceptLanguage = req.getHeader("Accept-Language");


        boolean mobile = isMobile(agent);

        String url = "";
        if(null == start){//第一次请求
            url = URI +q;
        }else{
            url = URI +q +"&start="+start+"&ei="+ei+"&sa=N";
        }

        System.out.println("==============================");
        System.out.println("请求参数q:" + q);
        System.out.println("请求参数agent:" + agent);
        System.out.println("请求参数isMobile:" + mobile);
        System.out.println("==============================");

        String respData = execute(agent, acceptLanguage, url);


        if(mobile){//手机端默认的字符集是GBK
            resp.setCharacterEncoding("GBK");
        }else{
            resp.setCharacterEncoding("UTF-8");
        }

        resp.getWriter().print(respData);
    }

    /**
     * 发送http请求
     * @param agent 浏览器中的 User-Agent
     * @param acceptLanguage 浏览器中的 Accept-Language
     * @param url 请求的url
     * @return
     * @throws IOException
     */
    private String execute(String agent, String acceptLanguage, String url) throws  IOException{
        //设置重试,三次
        DefaultHttpRequestRetryHandler dhr = new DefaultHttpRequestRetryHandler(3,true);

        CloseableHttpClient client= HttpClients.custom().setRetryHandler(dhr).build();

        //设置代理
        HttpHost proxy = new HttpHost("62eshop.com", 8080,"http");
        RequestConfig config = RequestConfig.custom().setProxy(proxy).build();


        //请求起始行--HttpClient会根据信息自动构建
        HttpGet get=new HttpGet(url);
        //请求首部--可选的，User-Agent对于一些服务器必选，不加可能不会返回正确结果
//        get.setHeader("User-Agent", "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/45.0.2454.101 Safari/537.36");
        get.setHeader("User-Agent", agent);
//        get.setHeader("Accept-Language","zh-CN,zh;q=0.8,en-GB;q=0.6,en;q=0.4");
        get.setHeader("Accept-Language",acceptLanguage);
//        get.setConfig(config);

        //执行请求
        CloseableHttpResponse response= null;
        String respData = null;
        try {
            response = client.execute(get);
            //获得起始行
            System.out.println(response.getStatusLine().toString()+"\n");
            //获得首部---当然也可以使用其他方法获取
            Header[] hs=response.getAllHeaders();
            for(Header h:hs){
                System.out.println(h.getName()+":\t"+h.getValue()+"\n");
            }
            //获取实体
            HttpEntity ety=response.getEntity();
            respData = EntityUtils.toString(ety, "utf-8");
//            System.out.println(respData);
            EntityUtils.consume(ety);//释放实体
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        }finally {
            if(null != response) {
                try {
                    response.close();//关闭响应
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        }

        return respData;
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
