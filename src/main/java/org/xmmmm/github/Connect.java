package org.xmmmm.github;




import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static sun.plugin.javascript.navig.JSType.URL;

public class Connect {
//
//    static CookieStore cookieStore=new BasicCookieStore();
//    static HttpClientContext context=null;
//    static String Jessionid=null;
//    static String loginreturnvalue=null;
//    static String vfwebqq1=null;
//    static String vfwebqq2=null;
//    static String cip=null;
//    static String f=null;
//    static String index=null;
//    static String port=null;
//    static String psessionid=null;
//    static String uin=null;
//
//    final CloseableHttpClient client =       HttpClientBuilder.create()
//            .setDefaultRequestConfig(RequestConfig.custom()
//                    .setCookieSpec(CookieSpecs.STANDARD).build()).setDefaultCookieStore(cookieStore).build();
//
//    @Test
//    public void getSmartQQTwoCode() throws IOException {
//
//        String url = "https://ssl.ptlogin2.qq.com/ptqrshow?appid=501004106&e=0&l=M&s=5&d=72&v=4&t=0.1";
//        HttpGet httpGet;
//
//        CloseableHttpResponse httpResponse= null;
//        try {
//            httpGet = new HttpGet(url);
//
//            httpResponse = client.execute(httpGet);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
//            InputStream inputStream;
//            FileOutputStream fileOutputStream = null;
//            HttpEntity httpEntity = httpResponse.getEntity();
//            setCookieStore(httpResponse);
////            setContext();
//           // getCookie(httpResponse);
//            try {
//                inputStream = httpEntity.getContent();
//                fileOutputStream=new FileOutputStream("/Users/huangyitao/Documents/xxxxxxx.png");
//                int b = inputStream.read();
//                while (b!=-1){
//                    fileOutputStream.write(b);
//                    b=inputStream.read();
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            }finally {
//                EntityUtils.consume(httpEntity);
//                fileOutputStream.close();
//                httpResponse.close();
//            }
//        }
//    }
//
//    @Test
//    public void getTwoCodeStatus() throws IOException, InterruptedException {
//        getSmartQQTwoCode();
//        String url=doGetTwoCodeStateURL(Jessionid);;
//        HttpGet httpGet;
//        CloseableHttpResponse httpResponse= null;
//        try {
//            httpGet = new HttpGet(url);
//
//            httpGet.setHeader("referer",
//                    "https://xui.ptlogin2.qq.com/cgi-bin/xlogin?daid=164&target=self&style=40&pt_disable_pwd=1&mibao_css=m_webqq&appid=501004106&enable_qlogin=0&no_verifyimg=1&s_url=http://web2.qq.com/proxy.html&f_url=loginerroralert&strong_login=1&login_state=10&t=20131024001"
//            );
//            httpResponse = client.execute(httpGet);
////            getCookie(httpResponse);
//            System.out.println(httpResponse.getStatusLine().toString());
//            if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
//                InputStream inputStream=httpResponse.getEntity().getContent();
//                String str = convertStreamToString(inputStream);
//                System.out.println(str);
//                }
//                while (true){
//                    Thread.sleep(2000);
//                    if(lunxun()==1)return;
//                }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//    }
//
//
//
//    public static String convertStreamToString(InputStream is) {
//        StringBuilder sb1 = new StringBuilder();
//        byte[] bytes = new byte[4096];
//        int size = 0;
//
//        try {
//            while ((size = is.read(bytes)) > 0) {
//                String str = new String(bytes, 0, size, "UTF-8");
//                sb1.append(str);
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                is.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//        return sb1.toString();
//    }
//
//    public int lunxun() throws IOException {
//        String url=doGetTwoCodeStateURL(Jessionid);
//        System.out.println(url);
//        HttpGet httpGet;
//        CloseableHttpResponse httpResponse= null;
//        httpGet = new HttpGet(url);
//
//        httpGet.setHeader("referer",
//                "https://xui.ptlogin2.qq.com/cgi-bin/xlogin?daid=164&target=self&style=40&pt_disable_pwd=1&mibao_css=m_webqq&appid=501004106&enable_qlogin=0&no_verifyimg=1&s_url=http://web2.qq.com/proxy.html&f_url=loginerroralert&strong_login=1&login_state=10&t=20131024001"
//        );
//        httpResponse = client.execute(httpGet);
//        if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
//            InputStream inputStream=httpResponse.getEntity().getContent();
//            String str = convertStreamToString(inputStream);
//            System.out.println(str);
//            if(str.contains("http")){
//                System.out.println("认证成功");
//                String[] ss=str.split(",");
//                loginreturnvalue=ss[2].replace("\'","")
//                        .replace("%2F","/")
//                        .replace("%3A",":");
//                return 1;
//            }
//        }
//        return 0;
//
//    }
//    public String doGetTwoCodeStateURL(String qrsiq){
//        List<NameValuePair> params = new ArrayList<>();
//        String url="https://ssl.ptlogin2.qq.com/" + "ptqrlogin?";
//        params.add(new BasicNameValuePair("u1", "http://web2.qq.com/proxy.html"));
//        params.add(new BasicNameValuePair("ptqrtoken", String.valueOf(hash33(qrsiq))));
//        params.add(new BasicNameValuePair("ptredirect", "0"));
//        params.add(new BasicNameValuePair("h", "1"));
//        params.add(new BasicNameValuePair("t", "1"));
//        params.add(new BasicNameValuePair("g", "1"));
//        params.add(new BasicNameValuePair("from_ui", "1"));
//        params.add(new BasicNameValuePair("ptlang", "2052"));
//        params.add(new BasicNameValuePair("action", "0-0-" + System.currentTimeMillis()));
//        params.add(new BasicNameValuePair("js_ver", "10270"));
//        params.add(new BasicNameValuePair("js_type", "1"));
//        params.add(new BasicNameValuePair("login_sig", ""));
//        params.add(new BasicNameValuePair("pt_uistyle", "40"));
//        params.add(new BasicNameValuePair("aid", "501004106"));
//        params.add(new BasicNameValuePair("daid", "164"));
//        params.add(new BasicNameValuePair("mibao_css", "m_webqq"));
//        url += URLEncodedUtils.format(params, '&', StandardCharsets.UTF_8);
//        return url;
//    }
//
//
//    //算出ptqrtoken值
//    public int hash33(String qrsiq){
//        int t=0;
//        int n=qrsiq.length();
//        System.out.println(qrsiq.length());
//        for (int e = 0; e < n; ++e) {
//            t+=(t<<5)+qrsiq.charAt(e);
//        }
//        System.out.println(Integer.MAX_VALUE);
//        return (2147483647&t);
//    }
//    //第一次请求的时候保存context用于保持会话持续
//
//    public static void setContext() {
//        System.out.println("----setContext");
//        context = HttpClientContext.create();
//        Registry<CookieSpecProvider> registry = RegistryBuilder
//                .<CookieSpecProvider> create()
//                .register(CookieSpecs.BEST_MATCH, new BestMatchSpecFactory())
//                .register(CookieSpecs.BROWSER_COMPATIBILITY,
//                        new BrowserCompatSpecFactory()).build();
//        context.setCookieSpecRegistry(registry);
//        context.setCookieStore(cookieStore);
//    }
//
//
//
//    @Test
//    public void doGetPtwebqq() throws IOException, InterruptedException {
//        getTwoCodeStatus();
//        HttpGet httpGet;
//        CloseableHttpResponse httpResponse= null;
//        httpGet = new HttpGet(loginreturnvalue);
//        httpResponse = client.execute(httpGet);
//        getCookie(httpResponse);
//        httpGet=new HttpGet("http://s.web2.qq.com/api/getvfwebqq?ptwebqq=&clientid=53999199&psessionid=&t="+System.currentTimeMillis());
//        httpGet.setHeader("referer",
//                "http://s.web2.qq.com/proxy.html?v=20130916001&callback=1&id=1"
//        );
//        httpResponse = client.execute(httpGet);
//        getCookie(httpResponse);
//        if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
//            InputStream inputStream=httpResponse.getEntity().getContent();
//            String str = convertStreamToString(inputStream);
//            JSONObject jsonObject=new JSONObject(str);
//            JSONObject jsonObject1=jsonObject.getJSONObject("result");
//            vfwebqq1=jsonObject1.getString("vfwebqq");
//            System.out.println(vfwebqq1);
//        }
//        //////////login2
//        HttpPost post=null;
//        post = new HttpPost("http://d1.web2.qq.com/channel/login2");
//        // 构造消息头
//        post.setHeader("origin","http://d1.web2.qq.com");
//        post.setHeader("referer","http://d1.web2.qq.com/proxy.html?v=20151105001&callback=1&id=2");
//        // 构建消息实体
//
//        StringEntity entity = new StringEntity("r={\"ptwebqq\":\"\",\"clientid\":53999199,\"psessionid\":\"\",\"status\":\"online\"}", Charset.forName("UTF-8"));
//        System.out.println("r={\"ptwebqq\":\"\",\"clientid\":53999199,\"psessionid\":\"\",\"status\":\"online\"}");
//        entity.setContentEncoding("UTF-8");
//        post.setEntity(entity);
//        HttpResponse response = client.execute(post);
//
//        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
//            String s=EntityUtils.toString(response.getEntity());
//            JSONObject jsonObject=new JSONObject(s);
//            JSONObject jsonObject1=jsonObject.getJSONObject("result");
//            vfwebqq2=jsonObject1.getString("vfwebqq");
//            psessionid=jsonObject1.getString("psessionid");
//            System.out.println(vfwebqq2);
//        }
//        post.abort();
//        ///////getfriends
//        Thread.sleep(1000);
//        post = new HttpPost("http://s.web2.qq.com/api/get_user_friends2");
//
//        // 构造消息头
//        post.setHeader("referer","http://s.web2.qq.com/proxy.html?v=20130916001&callback=1&id=1");
//        StringEntity entity1 = new StringEntity("r={\"vfwebqq\":\""+vfwebqq1+"\",\"hash\":\""+Friend.hash2(2324314173l)+"\"}", Charset.forName("UTF-8"));
//        entity1.setContentType("application/x-www-form-urlencoded");
//        System.out.println("r={\"vfwebqq\":\""+vfwebqq1+"\",\"hash\":\"00CF00C900770076\"}");
//        post.setEntity(entity1);
//        HttpResponse response1 = client.execute(post);
//        if (response1.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
//            System.out.println(EntityUtils.toString(response1.getEntity()));
//
//        }
//        //send msg
//        while (true) {
//            Thread.sleep(3000);
//            post = new HttpPost("http://d1.web2.qq.com/channel/send_qun_msg2");
//
//            // 构造消息头
//            post.setHeader("referer", "http://d1.web2.qq.com/cfproxy.html?v=20151105001&callback=1");
//            String s = "r={\"group_uin\":1428749859," +
//                            "\"content\":\"[\\\"test\\\",[\\\"font\\\",{\\\"name\\\":\\\"宋体\\\",\\\"size\\\":10,\\\"style\\\":[0,0,0],\\\"color\\\":\\\"000000\\\"}]]\"," +
//                            "\"face\":567," +
//                            "\"clientid\":53999199," +
//                            "\"msg_id\":" + getMsgId() + "," +
//                            "\"psessionid\":\"" + psessionid + "\"}";
//
//            entity1 = new StringEntity(s, Charset.forName("UTF-8"));
//            entity1.setContentType("application/x-www-form-urlencoded");
//            System.out.println(s);
//            post.setEntity(entity1);
//            HttpResponse response2 = client.execute(post);
//            if (response2.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
//                System.out.println(EntityUtils.toString(response2.getEntity()));
//
//            }
//
//
//        }
//    }
//
//    @Test
//    public void test(){
//        String  s=
//                " {\"to\":2840566131," +
//                        "\"content\":\"[\"233\",[\"font\",{\"name\":\"宋体\",\"size\":10,\"style\":[0,0,0],\"color\":\"000000\"}]]\"," +
//                        "\"face\":567," +
//                        "\"clientid\":53999199," +
//                        "\"msg_id\":"+getMsgId()+"," +
//                        "\"psessionid\":\""+psessionid+"\"}";
//    }
//
//
//    //第一次请求二维码的时候保存cookie
//
//    public void  setCookieStore(HttpResponse httpResponse){
//        String setCookie = httpResponse.getFirstHeader("Set-Cookie")
//                .getValue();
//        String JSESSIONID = setCookie.substring("qrsig=".length(),
//                setCookie.indexOf(";"));
//        Jessionid=JSESSIONID;
//    }
//
//    //打印响应内容
//
//    public static void printResponse(org.apache.http.HttpResponse httpResponse)
//            throws ParseException, IOException {
//        // 获取响应消息实体
//        HttpEntity entity = httpResponse.getEntity();
//        // 响应状态
//        System.out.println("status:" + httpResponse.getStatusLine());
//        System.out.println("headers:");
//        HeaderIterator iterator = httpResponse.headerIterator();
//        while (iterator.hasNext()) {
//            System.out.println("\t" + iterator.next());
//        }
//        // 判断响应实体是否为空
//        if (entity != null) {
//            String responseString = EntityUtils.toString(entity);
//            System.out.println("response length:" + responseString.length());
//            System.out.println("response content:"
//                    + responseString.replace("\r\n", ""));
//        }
//    }
//
//
//    public String getMsgId(){
//        long sequence = 0;
//        long t = System.currentTimeMillis();
//        t = (t - t % 1000) / 1000;
//        t = t % 10000 * 10000;
//        sequence++;
//        System.out.println(t+sequence);
//        return String.valueOf(t+sequence);
//    }
//
//    //打印吗响应的cookie
//    public void getCookie(HttpResponse httpResponse) {
//            System.out.println(">>>>>>headers:");
//            Arrays.stream(httpResponse.getAllHeaders()).forEach(System.out::println);
//            System.out.println("\n>>>>>>cookies:");
////            if()
////            context.getCookieStore().getCookies().forEach(System.out::println);
//            if(cookieStore.getCookies().size()==0)return;
//            cookieStore.getCookies().forEach(System.out::println);
//        for (int i = 0; i < cookieStore.getCookies().size(); i++) {
//            System.out.println(cookieStore.getCookies().get(i).getName());
//            System.out.println(cookieStore.getCookies().get(i).getValue());
//        }
//            System.out.println("\n");
//
//
//    }
}
