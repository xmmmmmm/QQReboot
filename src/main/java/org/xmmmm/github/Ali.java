package org.xmmmm.github;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradePrecreateRequest;
import com.alipay.api.response.AlipayTradePrecreateResponse;
import com.google.gson.JsonObject;
import org.junit.Test;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Ali {
    //支付宝公钥
    String alipay_public_key=
            "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAvIGZutqUtEGc7TI/ZSDjNJWYw47ooHgBSwiUiDuzR3J1XCjmuviLdjEisG3muTm+9nmiU9+YYTd5K8EKADtZmk0I0HZfUbWk+gdk+KoEvIw/fUiB91r+Xw49exbfE5YUvhJcN2ENkCm9kAphVrC76SzcVyGqULhZR5LhcyFq/E2w8TjBgEtkzeZoguDcRLSm97nqAJmZ2xb7G0MRn6NClMvDIODWqbd7i4hqeepCCpfbYAK66hS16E7rryFb5kYi+fYinkUupCgPCqAISyTyk1FDz2zA/5JvCyAwr9gBoDz4yJYlszjMAMu0DO0NBE4LpgQ2SyOiHwTZkCjRXg+ZLQIDAQAB";

    //商户私钥
    String merchant_private_key=
            "MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQC8gZm62pS0QZztMj9lIOM0lZjDjuigeAFLCJSIO7NHcnVcKOa6+It2MSKwbea5Ob72eaJT35hhN3krwQoAO1maTQjQdl9RtaT6B2T4qgS8jD99SIH3Wv5fDj17Ft8TlhS+Elw3YQ2QKb2QCmFWsLvpLNxXIapQuFlHkuFzIWr8TbDxOMGAS2TN5miC4NxEtKb3ueoAmZnbFvsbQxGfo0KUy8Mg4Napt3uLiGp56kIKl9tgArrqFLXoTuuvIVvmRiL59iKeRS6kKA8KoAhLJPKTUUPPbMD/km8LIDCv2AGgPPjIliWzOMwAy7QM7Q0ETgumBDZLI6IfBNmQKNFeD5ktAgMBAAECggEAAhdR9D+ufJ1ptzvidiW6P4AVVL4GGJmcMg/jTeuTnoxHfwE5JgPR0DrVTq25bH6Wfrd0tId7ckCiSHj4um3uHYcTPPTDM+3W6ashVJzWNp07qxEd1/ebcMSArBfh257DliQv/7FE7D96SdnDoKmPPBPyUcke0Yxp+SBI/fT18dyJ3sP8QlX5iZX+0cZm/IWSFrlJXaV5rUwKOdlTBg6RQm43E4a8IB8JxETXW1vLv6vVLtLHavfAx2S+R9ysyYuSejX6Jb1DYE99Mn2tnmfJ6F0ZlCA7PrnAuxYt7ZvDT1M2IKVBCBP3RZKDwO/J/IvxsUPIPe1bTG7THHtSLT2mpQKBgQDzQkTn5Y/MJPLibH6ik8VOMBn0CETr0XPM4NyCCkncp9UfElGYgY1t3lmQsAre8X458RAAxPCj1WwKueNHQkOVOJCmmX81dcmw8IFFrPhqVmcbfpuIRSiLH6yPlh1R/KtehzARwDnO7mEypZ3Amhq6FX699OUPNE07AG1CgtseawKBgQDGYS6luleirKMYTZchEZM5sECAYayZV47sxUyMR6wJqvKWxFOAwrFPxOn28fY85z6n5JLcvkIXTdxNjbs2BAP0TWKKjk/37DcaJcjNJwKEqbiG9a6VAH0Yy8qZx8qPkF68dm+y/vgsxbn4+DJzYiA4IzF79ebQQw8b0AVGHoXcxwKBgGQ5t548DSU1TAVSYlKlw7NVSjwrdAodQDL3qqLBjnEljT/CEnBdYGIWLJzai4ATy8gftFI80lEOS/eXYzcm7CV9KswZjjn+j90hGoIUpD1b4BuJ7JcWt3+qyzRVyzrOeeT2mi2WH7tMqCtv5TFHzR9CX/fwALhk+jUHFf1DYPiFAoGAOwRkZUd3lNy5NJy4rkzVON9XgRoNyBzFV2km6cDUzJd/zhygHvlM2MdjN2UqdVnySO52RpePf/FY7fWRUXOGXaK8Ay5tWft7NRsyjt3hIOiBDh1/D7LyhHL0ViWa/HTdykGN33l9CYzVU9063Rq/LRRDP3abcS/SCdssBn1EI7cCgYAWor4oyWNIDbN3pwib3O4bOTc3Hvg/0aKrVyq2alIhyBwDzOduWi13PYi011SXitepPiz9YhC3ABjdXnDL82Z2vEIOHEuZEMEOH6mNwdNKBOHIs6W670e/gWQ++0gg+G3/ZMlJFokoNt0dC9MTYG/Md6DJPzX5omSqSUaH48ohfg==";

    String charset = "utf-8";

    String gatewayUrl="https://openapi.alipay.com/gateway.do";

    String appid ="2018061260372187";

    String notify_url="";

    //最大查询重试次数
    int MaxQueryRetry = 10;

            //查询间隔
    int QueryDuration = 3;

    String subject="xx服务器-支付码";

    double totalmoney = 0.1;
    @Test
    public void test() throws AlipayApiException {
        Date day=new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
        String nowdate=df.format(day);
        AlipayClient alipayClient = new DefaultAlipayClient(gatewayUrl,
                appid,
                merchant_private_key,
                "JSON",
                charset,
                alipay_public_key,
                "RSA2");
        AlipayTradePrecreateRequest request = new AlipayTradePrecreateRequest();

        request.setBizContent("{" +
                "\"out_trade_no\":\""+df+"\"," +
                "\"seller_id\":\"\"," +
                "\"total_amount\":88.88," +
                "\"discountable_amount\":8.88," +
                "\"subject\":\"Iphone616G\"," +
                "\"goods_detail\":[{" +
                "\"goods_id\":\"apple-01\"," +
                "\"goods_name\":\"ipad\"," +
                "\"quantity\":1," +
                "\"price\":2000," +
                "\"goods_category\":\"34543238\"," +
                "\"body\":\"特价手机\"," +
                "\"show_url\":\"http://www.alipay.com/xxx.jpg\"" +
                "}]," +
                "\"body\":\"Iphone616G\"," +
                "\"operator_id\":\"yx_001\"," +
                "\"store_id\":\"NJ_001\"," +
                "\"disable_pay_channels\":\"pcredit,moneyFund,debitCardExpress\"," +
                "\"enable_pay_channels\":\"pcredit,moneyFund,debitCardExpress\"," +
                "\"terminal_id\":\"NJ_T_001\"," +
                "\"extend_params\":{" +
                "\"sys_service_provider_id\":\"2088511833207846\"" +
                "}," +
                "\"timeout_express\":\"90m\"," +
                "\"business_params\":\"{\\\"data\\\":\\\"123\\\"}\"" +
                "}");
        AlipayTradePrecreateResponse response = alipayClient.execute(request);
        if(response.isSuccess()){
            System.out.println("调用成功");
        } else {
            System.out.println("调用失败");
        }

    }


}
