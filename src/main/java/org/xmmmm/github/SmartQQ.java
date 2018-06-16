package org.xmmmm.github;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.CookieStore;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.bukkit.map.MapView;
import org.json.JSONObject;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class SmartQQ {
    private long selfUIN=0;
    private CookieStore cookieStore=new BasicCookieStore();
    private String Jessionid=null;
    //第一次登陆返回网址
    private String loginreturnvalue=null;
    private String vfwebqq1=null;
    private String psessionid=null;
    //获得好友集合
    private List<Friend> friends=new ArrayList<>();
    private java.util.Map<Long, Friend> uinToFriend = new HashMap<>();
    private java.util.Map<String, Long> nameToUin = new HashMap<>();
    //获得群集合
    private List<Group> groups=new ArrayList<>();
    private java.util.Map<String, Long> groupnameToUin=new HashMap<>();
    private java.util.Map<Long, Group> uinToGroup=new HashMap<>();
    //自己的faceid
    private int face;
    //是否创建成功
    private boolean isCreated=false;

    final CloseableHttpClient client= HttpClientBuilder.create()
            .setDefaultRequestConfig(RequestConfig.custom()
                    .setCookieSpec(CookieSpecs.STANDARD).build()).setDefaultCookieStore(cookieStore).build();


    //first获取二维码
    private void getCode(String path) throws IOException {
        String url = "https://ssl.ptlogin2.qq.com/ptqrshow?appid=501004106&e=0&l=M&s=5&d=72&v=4&t=0.1";
        HttpGet httpGet = new HttpGet(url);
        CloseableHttpResponse httpResponse = client.execute(httpGet);
        if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            Logger.info("成功获取二维码，请扫码登陆");
            HttpEntity httpEntity = httpResponse.getEntity();
            getJessionid(httpResponse);
            InputStream inputStream = httpEntity.getContent();
            FileOutputStream fileOutputStream=new FileOutputStream(path);
            int b = inputStream.read();
            while (b!=-1){
                fileOutputStream.write(b);
                b=inputStream.read();
            }
            EntityUtils.consume(httpEntity);
            inputStream.close();
            fileOutputStream.close();
            httpResponse.close();
        }
    }

    //循环请求二维码状态
    private void forCodeState() throws IOException, InterruptedException {
        //获取请求地址
        String url=doGetTwoCodeStateURL(Jessionid);;
        while (true) {
            Thread.sleep(1000);
            CloseableHttpResponse httpResponse = null;
            try {
                HttpGet httpGet = new HttpGet(url);
                httpGet.setHeader("referer",
                        "https://xui.ptlogin2.qq.com/cgi-bin/xlogin?daid=164&target=self&style=40&pt_disable_pwd=1&mibao_css=m_webqq&appid=501004106&enable_qlogin=0&no_verifyimg=1&s_url=http://web2.qq.com/proxy.html&f_url=loginerroralert&strong_login=1&login_state=10&t=20131024001"
                );
                httpResponse = client.execute(httpGet);
                if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                    InputStream inputStream = httpResponse.getEntity().getContent();
                    String str = convertStreamToString(inputStream);
                    Logger.info(str);
                    if(str.contains("http")){
                        isCreated=true;
                        Logger.info("二维码认证成功！！！");
                        String[] ss=str.split(",");
                        loginreturnvalue=ss[2].replace("\'","")
                                .replace("%2F","/")
                                .replace("%3A",":");
                        break;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                httpResponse.close();
            }
        }

    }


    private void getPtwebqq() throws IOException {
        //请求登陆的时候获取的url
        HttpGet httpGet;
        CloseableHttpResponse httpResponse= null;
        httpGet = new HttpGet(loginreturnvalue);
        httpResponse = client.execute(httpGet);
        //打印cookie
        printCookie(httpResponse);
        //设置cookie中的uin
        setUin();
        //请求获取vfwebqq
        httpGet=new HttpGet("http://s.web2.qq.com/api/getvfwebqq?ptwebqq=&clientid=53999199&psessionid=&t="+System.currentTimeMillis());
        httpGet.setHeader("referer",
                "http://s.web2.qq.com/proxy.html?v=20130916001&callback=1&id=1"
        );
        httpResponse = client.execute(httpGet);
        //打印cookie
        printCookie(httpResponse);
        if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            InputStream inputStream=httpResponse.getEntity().getContent();
            String str = convertStreamToString(inputStream);
            JSONObject jsonObject=new JSONObject(str);
            JSONObject jsonObject1=jsonObject.getJSONObject("result");
            vfwebqq1=jsonObject1.getString("vfwebqq");
            Logger.info(vfwebqq1);
        }
        httpResponse.close();
    }

    //二次登陆
    private void postLogin2() throws IOException {
        HttpPost post = new HttpPost("http://d1.web2.qq.com/channel/login2");
        // 构造消息头
        post.setHeader("origin","http://d1.web2.qq.com");
        post.setHeader("referer","http://d1.web2.qq.com/proxy.html?v=20151105001&callback=1&id=2");
        // 构建消息实体
        StringEntity entity = new StringEntity("r={\"ptwebqq\":\"\"," +
                "\"clientid\":53999199," +
                "\"psessionid\":\"\"," +
                "\"status\":\"online\"}", Charset.forName("UTF-8"));
        entity.setContentEncoding("UTF-8");
        post.setEntity(entity);
        HttpResponse response = client.execute(post);
        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            String s=EntityUtils.toString(response.getEntity());
            JSONObject jsonObject=new JSONObject(s);
            JSONObject jsonObject1=jsonObject.getJSONObject("result");
            psessionid=jsonObject1.getString("psessionid");
            Logger.info(psessionid);
        }
        post.abort();
    }

    //获取好友列表
    private void getFriends() throws IOException {
        HttpPost post = new HttpPost("http://s.web2.qq.com/api/get_user_friends2");
        // 构造消息头
        post.setHeader("referer","http://s.web2.qq.com/proxy.html?v=20130916001&callback=1&id=1");
        StringEntity entity1 = new StringEntity("r={\"vfwebqq\":\""+vfwebqq1+"\",\"hash\":\""+Friend.hash2(selfUIN)+"\"}", Charset.forName("UTF-8"));
        entity1.setContentType("application/x-www-form-urlencoded");
        Logger.info("r={\"vfwebqq\":\""+vfwebqq1+"\",\"hash\":\""+Friend.hash2(selfUIN)+"\"}");
        post.setEntity(entity1);
        HttpResponse response1 = client.execute(post);
        if (response1.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            String result=EntityUtils.toString(response1.getEntity());
            Object[] objects=Json.getFriend(result);
            friends= (List<Friend>) objects[0];
            uinToFriend = (Map<Long, Friend>) objects[1];
            nameToUin= (Map<String, Long>) objects[2];
        }
        post.abort();
    }

    private void getGroups() throws IOException {
        HttpPost post = new HttpPost("http://s.web2.qq.com/api/get_group_name_list_mask2");
        // 构造消息头
        post.setHeader("referer","http://s.web2.qq.com/proxy.html?v=20130916001&callback=1&id=1");
        StringEntity entity1 = new StringEntity("r={\"vfwebqq\":\""+vfwebqq1+"\",\"hash\":\""+Friend.hash2(selfUIN)+"\"}", Charset.forName("UTF-8"));
        entity1.setContentType("application/x-www-form-urlencoded");
        Logger.info("r={\"vfwebqq\":\""+vfwebqq1+"\",\"hash\":\""+Friend.hash2(selfUIN)+"\"}");
        post.setEntity(entity1);
        HttpResponse response1 = client.execute(post);
        if (response1.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            String result=EntityUtils.toString(response1.getEntity());
            Object[] objects=Json.getGroup(result);
            groups= (List<Group>) objects[0];
            groupnameToUin= (Map<String, Long>) objects[1];
            uinToGroup = (Map<Long, Group>) objects[2];
        }
        post.abort();
    }

    private void getSelfFaceId() throws IOException {
        String url = "http://s.web2.qq.com/api/get_self_info2?t="+System.currentTimeMillis();
        HttpGet httpGet = new HttpGet(url);
        httpGet.setHeader("referer","http://s.web2.qq.com/proxy.html?v=20130916001&callback=1&id=1");
        CloseableHttpResponse httpResponse = client.execute(httpGet);
        if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            Logger.info("获取个人信息");
            String result=EntityUtils.toString(httpResponse.getEntity());
            Logger.info(result);
            JSONObject jsonObject=new JSONObject(result);
            JSONObject jsonObject1=jsonObject.getJSONObject("result");
            face=jsonObject1.getInt("face");
            Logger.info(face+"");
        }
    }

    public SmartQQ instanceSmartQQ(String path) throws IOException, InterruptedException {
        getCode(path);
        forCodeState();
        getPtwebqq();
        postLogin2();
        getFriends();
        getGroups();
        getSelfFaceId();
        sendMessageToGroup("xxx","【BK】MC服主rpg交流群");
        sendMessageToFriend("x1","接小型sk插件");
        return this;
    }

    public void test(String path) throws IOException, InterruptedException {
        getCode(path);

    }


    private void setUin(){
        for (int i = 0; i < cookieStore.getCookies().size(); i++) {
            if(cookieStore.getCookies().get(i).getName().equals("uin")){
                selfUIN=Long.parseLong(cookieStore.getCookies().get(i).getValue().replace("o",""));
                break;
            }
        }
    }

    private String doGetTwoCodeStateURL(String qrsiq){
        List<NameValuePair> params = new ArrayList<>();
        String url="https://ssl.ptlogin2.qq.com/" + "ptqrlogin?";
        params.add(new BasicNameValuePair("u1", "http://web2.qq.com/proxy.html"));
        params.add(new BasicNameValuePair("ptqrtoken", String.valueOf(hash33(qrsiq))));
        params.add(new BasicNameValuePair("ptredirect", "0"));
        params.add(new BasicNameValuePair("h", "1"));
        params.add(new BasicNameValuePair("t", "1"));
        params.add(new BasicNameValuePair("g", "1"));
        params.add(new BasicNameValuePair("from_ui", "1"));
        params.add(new BasicNameValuePair("ptlang", "2052"));
        params.add(new BasicNameValuePair("action", "0-0-" + System.currentTimeMillis()));
        params.add(new BasicNameValuePair("js_ver", "10270"));
        params.add(new BasicNameValuePair("js_type", "1"));
        params.add(new BasicNameValuePair("login_sig", ""));
        params.add(new BasicNameValuePair("pt_uistyle", "40"));
        params.add(new BasicNameValuePair("aid", "501004106"));
        params.add(new BasicNameValuePair("daid", "164"));
        params.add(new BasicNameValuePair("mibao_css", "m_webqq"));
        url += URLEncodedUtils.format(params, '&', StandardCharsets.UTF_8);
        return url;
    }

    private void  getJessionid(HttpResponse httpResponse){
        String setCookie = httpResponse.getFirstHeader("Set-Cookie")
                .getValue();
        String JSESSIONID = setCookie.substring("qrsig=".length(),
                setCookie.indexOf(";"));
        Jessionid=JSESSIONID;
    }

    //算出ptqrtoken值
    private int hash33(String qrsiq){
        int t=0;
        int n=qrsiq.length();
        Logger.info(qrsiq.length()+"");
        for (int e = 0; e < n; ++e) {
            t+=(t<<5)+qrsiq.charAt(e);
        }
        return (2147483647&t);
    }

    private static String convertStreamToString(InputStream is) {
        StringBuilder sb1 = new StringBuilder();
        byte[] bytes = new byte[4096];
        int size = 0;

        try {
            while ((size = is.read(bytes)) > 0) {
                String str = new String(bytes, 0, size, "UTF-8");
                sb1.append(str);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb1.toString();
    }


    public void sendMessageToFriend(String msg,String friendName) throws IOException {
        long uin=getUin(friendName);
        if(uin==0){
            Logger.info("不存在这个好友");
            return;
        }
        HttpPost post;
        StringEntity entity1;
        post = new HttpPost("http://d1.web2.qq.com/channel/send_buddy_msg2");
        // 构造消息头
        post.setHeader("referer", "http://d1.web2.qq.com/cfproxy.html?v=20151105001&callback=1");
        String s = "r={\"to\":"+uin+"," +
                "\"content\":\"[\\\""+msg+"\\\",[\\\"font\\\",{\\\"name\\\":\\\"宋体\\\",\\\"size\\\":10,\\\"style\\\":[0,0,0],\\\"color\\\":\\\"000000\\\"}]]\"," +
                "\"face\":"+face+"," +
                "\"clientid\":53999199," +
                "\"msg_id\":" + getMsgId() + "," +
                "\"psessionid\":\"" + psessionid + "\"}";
        entity1 = new StringEntity(s, Charset.forName("UTF-8"));
        entity1.setContentType("application/x-www-form-urlencoded");
        Logger.info("向uin好友:"+uin+"发送: "+msg);
        post.setEntity(entity1);
        HttpResponse response2 = client.execute(post);
        Logger.info(EntityUtils.toString(response2.getEntity()));
        if (response2.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
            Logger.info("消息发送不成功");
        }
    }

    public void sendMessageToGroup(String msg,String groupName) throws IOException {
        long groupuin=getGroupUin(groupName);
        if(groupuin==0){
            Logger.info("不存在这个群");
            return;
        }
        HttpPost post;
        StringEntity entity1;
        post = new HttpPost("http://d1.web2.qq.com/channel/send_qun_msg2");
        // 构造消息头
        post.setHeader("referer", "http://d1.web2.qq.com/cfproxy.html?v=20151105001&callback=1");
        String s = "r={\"group_uin\":"+groupuin+"," +
                "\"content\":\"[\\\""+msg+"\\\",[\\\"font\\\",{\\\"name\\\":\\\"宋体\\\",\\\"size\\\":10,\\\"style\\\":[0,0,0],\\\"color\\\":\\\"000000\\\"}]]\"," +
                "\"face\":"+face+"," +
                "\"clientid\":53999199," +
                "\"msg_id\":" + getMsgId() + "," +
                "\"psessionid\":\"" + psessionid + "\"}";
        Logger.info(s);
        entity1 = new StringEntity(s, Charset.forName("UTF-8"));
        entity1.setContentType("application/x-www-form-urlencoded");
        Logger.info("向groupuin:"+groupuin+"群发送: "+msg);
        post.setEntity(entity1);
        HttpResponse response2 = client.execute(post);
        Logger.info(EntityUtils.toString(response2.getEntity()));
        if (response2.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
            Logger.info("消息发送不成功");
        }
    }

    public String getMsgId(){
        long sequence = 0;
        long t = System.currentTimeMillis();
        t = (t - t % 1000) / 1000;
        t = t % 10000 * 10000;
        sequence++;
        System.out.println(t+sequence);
        return String.valueOf(t+sequence);
    }


    public long getUin(String friendName){
        if(nameToUin.get(friendName)==null)
        {
            return 0;
        }
        return nameToUin.get(friendName);
    }

    public long getGroupUin(String groupName){
        if(groupnameToUin.get(groupName)==null)
        {
            return 0;
        }
        return groupnameToUin.get(groupName);
    }

    //打印吗响应的cookie
    public void printCookie(HttpResponse httpResponse) {
        System.out.println(">>>>>>headers:");
        Arrays.stream(httpResponse.getAllHeaders()).forEach(System.out::println);
        System.out.println("\n>>>>>>cookies:");
        if(cookieStore.getCookies().size()==0)return;
        cookieStore.getCookies().forEach(System.out::println);
        System.out.println("\n");
    }

    public void closeClient() throws IOException {
        client.close();
    }

    public long getSelfUIN() {
        return selfUIN;
    }

    public CookieStore getCookieStore() {
        return cookieStore;
    }

    public String getJessionid() {
        return Jessionid;
    }

    public String getLoginreturnvalue() {
        return loginreturnvalue;
    }

    public String getVfwebqq1() {
        return vfwebqq1;
    }

    public String getPsessionid() {
        return psessionid;
    }

    public Map<Long, Friend> getUinToFriend() {
        return uinToFriend;
    }

    public Map<String, Long> getNameToUin() {
        return nameToUin;
    }

    public Map<String, Long> getGroupnameToUin() {
        return groupnameToUin;
    }

    public Map<Long, Group> getUinToGroup() {
        return uinToGroup;
    }

    public int getFace() {
        return face;
    }

    public boolean isCreated() {
        return isCreated;
    }

}
