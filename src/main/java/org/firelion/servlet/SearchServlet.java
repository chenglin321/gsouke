package org.firelion.servlet;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.config.AuthSchemes;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.client.cache.CacheConfig;
import org.apache.http.impl.client.cache.CachingHttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;
import org.firelion.util.PropertiesUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.Arrays;

/**
 * Created by chenglin on 15-10-6.
 * 处理搜索页面
 */
public class SearchServlet extends HttpServlet {

    public final static  String URI = "https://www.google.com.hk/search?newwindow=1&safe=strict&site=&source=hp&q=";

    public final static String STYLE= "<head><title>Google搜索</title><meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\"/></head><style data-jiis=\"cc\" id=\"gstyle\">.sbibps{padding:0px 9px 0;padding-top:0 !important;width:552px;}.gsfi,.lst{font:17px arial,sans-serif;line-height:26px !important;height:26px !important;}.lst{margin-top:-50px;margin-bottom:0;}.sfbgg{background-color:#f1f1f1;background-image:-webkit-gradient(radial,100 36,0,100 -40,120,from(#fafafa),to(#f1f1f1));border-bottom:1px solid #666;border-color:#e5e5e5;height:89px;}.lsb{background:transparent;border:0;font-size:0;height:30px;outline:0;width:100%;}.hp .nojsb,.srp .jsb{display:none;}body{color:#000;margin:0;}body{background:#fff}a.gb1,a.gb2,a.gb3,.link{color:#1a0dab !important}.ts{border-collapse:collapse}.ts td{padding:0}#res,#res .j{line-height:1.2}.g{line-height:1.2;text-align:left}a:link,.w,#prs a:visited,#prs a:active,.q:active,.q:visited,.kl:active,.tbotu{color:#1a0dab}.mblink:visited,a:visited{color:#609}.s{max-width:42em}.sl{font-size:82%}.hd{position:absolute;width:1px;height:1px;top:-1000em;overflow:hidden}.f,.f a:link,.m{color:#666}.c h2{color:#666}.mslg cite{display:none}.ng{color:#dd4b39}h1,ol,ul,li{margin:0;padding:0}.g,body,html,input,.std,h1{font-size:small;font-family:arial,sans-serif}.c h2,h1{font-weight:normal}.blk a{color:#000}#nav a{display:block}#nav .i{color:#a90a08;font-weight:bold}.csb,.ss{background-position:0 0;height:40px;display:block}.mbi{background-position:-153px -70px;display:inline-block;float:left;height:13px;margin-right:3px;margin-top:4px;width:13px}.mbt{color:#11c;float:left;font-size:13px;margin-right:5px;position:relative}#nav td{padding:0;text-align:center}.ch{cursor:pointer}h3,.med{font-size:medium;font-weight:normal;margin:0;padding:0}#res h3{font-size:18px}.e{margin:2px 0 .75em}.slk div{padding-left:12px;text-indent:-10px}.blk{border-top:1px solid #6b90da;background:#f0f7f9}#cnt{clear:both}#res{padding-right:1em;margin:0 16px}.xsm{font-size:x-small}ol li{list-style:none}.sm li{margin:0}.gl,#foot a,.nobr{white-space:nowrap}#foot #navcnt a{color:#4285f4;font-weight:normal}#foot #navcnt .cur{color:rgba(0,0,0,0.87);font-weight:normal}.sl,.r{display:inline;font-weight:normal;margin:0}.r{font-size:medium}h4.r{font-size:small}.vshid{display:none}.mdm #nyc{margin-left:683px}.mdm #rhs{margin-left:732px}.big #nyc{margin-left:743px}.big #rhs{margin-left:792px;}body .big #subform_ctrl{margin-left:229px}.vsc{display:inline-block;position:relative;width:100%}#nyc cite button.esw{display:none}#res .std,.c,.slk{line-height:146%}#tads,#tadsb{margin-bottom:1.4em}#res .r{line-height:1.2}#res .s{line-height:1.54}#res .g .ts{max-width:42em}.fwtc{width:42em}#mbEnd .ads-ad{line-height:1.54;margin:1.1em 0}#mbEnd h3,#mbEnd cite{line-height:1.3}#mbEnd h3{padding-bottom:1px}#guser{height:16px}.ab_button{font-size:12px !important}ol,ul,li{border:0;margin:0;padding:0}.g{margin-top:0;margin-bottom:23px}.ibk{display:inline-block;vertical-align:top}.tsw{width:595px}#cnt{min-width:833px;margin-left:0}.mw{max-width:1197px}.big .mw{max-width:1250px}#cnt #center_col,#cnt #foot{width:528px}.gbh{top:24px}#gbar{margin-left:8px;height:20px}#guser{margin-right:8px;padding-bottom:5px !important}.tsf-p{padding-left:126px;margin-right:46px;max-width:739px}.big .tsf-p{margin-right:322px;padding-left:126px;}.mbi{margin-bottom:-1px}.uc{padding-left:8px;position:relative;margin-left:128px;}.ucm{padding-bottom:5px;padding-top:5px;margin-bottom:8px}.col{float:left}#leftnavc,#center_col,#rhs{position:relative}#center_col{margin-left:138px;margin-right:264px;padding:0 8px;padding:0 8px;}.mdm #center_col{margin-left:138px;padding:0 8px}.big #center_col{margin-left:138px;padding:0 8px}#subform_ctrl{font-size:11px;margin-right:480px;position:relative;z-index:99}#subform_ctrl a.gl{color:#1a0dab}#center_col{clear:both}#res{border:0;margin:0;padding:0 8px;}#ires{padding-top:6px}.micon,.close_btn{border:0}.close_btn{background-position:-138px -84px;float:right;height:14px;width:14px}.mitem{border-bottom:1px solid transparent;line-height:29px;opacity:1.0;}.mitem .kl{padding-left:16px}.mitem .kl:hover,.msel .kls:hover{color:#222;display:block}.mitem>.kl{color:#222;display:block}.msel{color:#dd4b39;cursor:pointer}.msel .kls{border-left:5px solid #dd4b39;padding-left:11px}.mitem>.kl,.msel{font-size:13px}#tbd{display:block;min-height:1px}.tbt{font-size:13px;line-height:1.2}.tbnow{white-space:nowrap}.tbos,.tbots{font-weight:bold}.tbst{margin-top:8px}a:link,.w,.q:active,.q:visited{cursor:pointer}#tads a,#tadsb a,#res a,#rhs a,#taw a{text-decoration:none}body{color:#222}.s{color:#545454}.s a:visited em{color:#609}.s a:active em{color:#dd4b39}.sfcc{width:833px;}#tsf{width:833px;}.big .sfcc{max-width:1129px}.big #tsf{width:1109px}#topstuff .obp{padding-top:6px}.slk{margin-top:6px !important}.st{line-height:1.4;word-wrap:break-word}.kt{border-spacing:2px 0;margin-top:1px}.esw{vertical-align:text-bottom;}.ab_button{-webkit-border-radius:2px;border-radius:2px;cursor:default;font-family:arial,sans-serif;font-size:11px;font-weight:bold;height:27px;line-height:27px;margin:2px 0;min-width:54px;padding:0 8px;text-align:center;-webkit-transition:all 0.218s,visibility 0s;transition:all 0.218s,visibility 0s;-webkit-user-select:none}.kbtn-small{min-width:26px;width:26px}.ab_button.left{-webkit-border-radius:2px 0 0 2px;border-radius:2px 0 0 2px;border-right-color:transparent;margin-right:0}.ab_button.right{-webkit-border-radius:0 2px 2px 0;border-radius:0 2px 2px 0;margin-left:-1px}a.ksb,.div.ksb{color:#444;text-decoration:none;cursor:default}a.ab_button{color:#444;text-decoration:none;cursor:default}.ab_button:hover{-webkit-box-shadow:0 1px 1px rgba(0,0,0,0.1);box-shadow:0 1px 1px rgba(0,0,0,0.1);-webkit-transition:all 0.0s;transition:all 0.0s}#hdtb-tls:hover{-webkit-box-shadow:0 1px 1px rgba(0,0,0,0.1);box-shadow:0 1px 1px rgba(0,0,0,0.1);-webkit-transition:all 0.0s;transition:all 0.0s}.ab_ctl{display:inline-block;position:relative;margin-left:16px;margin-top:1px;vertical-align:middle}#hdtbSum .ab_ctl{line-height:1.2}a.ab_button,a.ab_button:visited{display:inline-block;color:#444;margin:0}a.ab_button:hover{color:#222}#appbar a.ab_button:active,a.ab_button.selected,a.ab_button.selected:hover{color:#333}.ab_dropdown{display:none;}#top_nav{-webkit-user-select:none}.ab_tnav_wrp{height:35px;}#hdtb-msb>.hdtb-mitem:first-child,.ab_tnav_wrp,#cnt #center_col,.mw #center_col{margin-left:120px}body.vasq #hdtbSum{height:59px;line-height:54px}body.vasq #hdtb-msb .hdtb-mitem.hdtb-msel,body.vasq #hdtb-msb .hdtb-mitem.hdtb-msel-pre{height:54px}body.vasq .ab_tnav_wrp{height:43px}body.vasq #topabar.vasqHeight{margin-top:-44px !important}body.vasq #resultStats{line-height:43px}body.vasq .hdtb-mn-o,body.vasq .hdtb-mn-c{top:50px}cite{color:#006621;font-style:normal;}em,.rbt b,.c b,#mbEnd b{color:#dd4b39;font-style:normal;font-weight:normal;}</style>";
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
        // Create a connection manager with custom configuration.
        PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager();

        // Configure total max or per route limits for persistent connections
        // that can be kept in the pool or leased by the connection manager.
        connManager.setMaxTotal(100);
        connManager.setDefaultMaxPerRoute(10);
        connManager.setMaxPerRoute(new HttpRoute(new HttpHost(PropertiesUtil.getProp("url"), 80)), 20);

        // Create global request configuration
        RequestConfig defaultRequestConfig = RequestConfig.custom()
                .setCookieSpec(CookieSpecs.BEST_MATCH)
                .setExpectContinueEnabled(true)
                .setStaleConnectionCheckEnabled(true)
                .setTargetPreferredAuthSchemes(Arrays.asList(AuthSchemes.NTLM, AuthSchemes.DIGEST))
                .setProxyPreferredAuthSchemes(Arrays.asList(AuthSchemes.BASIC))
                .build();


//        CredentialsProvider credsProvider = new BasicCredentialsProvider();
//        credsProvider.setCredentials(
//                new AuthScope(PropertiesUtil.getProp("proxyHost"), Integer.valueOf(PropertiesUtil.getProp("proxyPort"))),
//                new UsernamePasswordCredentials(PropertiesUtil.getProp("proxyUser"), PropertiesUtil.getProp("proxyPwd")));


        // Create an HttpClient with the given custom dependencies and configuration.
        CloseableHttpClient httpclient = HttpClients.custom()
//                .setDefaultCredentialsProvider(credsProvider)
                .setConnectionManager(connManager)
                .build();
        HttpGet httpget = new HttpGet(url);
        //请求首部--可选的，User-Agent对于一些服务器必选，不加可能不会返回正确结果
//        get.setHeader("User-Agent", "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/45.0.2454.101 Safari/537.36");
        httpget.setHeader("User-Agent", agent);
//        get.setHeader("Accept-Language","zh-CN,zh;q=0.8,en-GB;q=0.6,en;q=0.4");
        httpget.setHeader("Accept-Language",acceptLanguage);
        // Request configuration can be overridden at the request level.
        // They will take precedence over the one set at the client level.
        RequestConfig requestConfig = RequestConfig.copy(defaultRequestConfig)
                .setSocketTimeout(5000)
                .setConnectTimeout(5000)
//                .setProxy(new HttpHost(PropertiesUtil.getProp("proxyHost"), Integer.valueOf(PropertiesUtil.getProp("proxyPort"))))
                .setConnectionRequestTimeout(5000)
                .build();
        httpget.setConfig(requestConfig);


        System.out.println("executing request " + httpget.getURI());
        CloseableHttpResponse response = null;
        String respData = "";
        try {

            response = httpclient.execute(httpget);
            HttpEntity entity = response.getEntity();

            if (entity != null) {
                HttpEntity ety=response.getEntity();
                respData = EntityUtils.toString(ety, "utf-8");
                //去掉加载慢的背景图片

                Document doc = Jsoup.parse(respData);
//              Element gstyle = doc.getElementById("gstyle");
                doc.getElementById("doc-info").html("");
                doc.getElementById("cst").html("");
                doc.getElementById("hdtb").html("");

                doc.getElementById("xfoot").html("");

                doc.getElementById("taw").html("");
                doc.getElementById("rhscol").html("");
                doc.getElementById("mngb").html("");
                doc.getElementById("logo").html("");
                doc.getElementById("sbds").html("");
                Element content = doc.getElementById("gsr");

                respData = new StringBuffer(STYLE).append(content.html()).toString();
//                respData = respData.replaceAll("background:url\\(//ssl.gstatic.com/gb/images/b_8d5afc09.png\\);","");
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally {
            if(response != null){
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            try {
                httpclient.close();
            } catch (IOException e) {
                e.printStackTrace();
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
