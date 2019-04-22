package com.kgc.itrip.tuils.common;


import com.cloopen.rest.sdk.CCPRestSmsSDK;
import org.springframework.stereotype.Component;

import java.util.HashMap;


@Component
public class SMSSendUtil {
    public boolean sendSms(String phonenum,String b,String str[]) {
        HashMap<String, Object> result = null;
        boolean ok=false;


//初始化SDK
        CCPRestSmsSDK restAPI = new CCPRestSmsSDK();

restAPI.init("sandboxapp.cloopen.com", "8883");
restAPI.init("app.cloopen.com", "8883");


        restAPI.init("sandboxapp.cloopen.com", "8883");

        restAPI.setAccount("8aaf0708648862310164978c06390b86","88df45fd3b094aa1876dab47cb57a374");



        restAPI.setAppId("8aaf0708648862310164978c068c0b8c");

        result = restAPI.sendTemplateSMS(phonenum,b,str);

        System.out.println("SDKTestGetSubAccounts result=" + result);
        String resultstate=(String) result.get("statusCode");

        if("000000".equals(resultstate)){
// //正常返回输出data包体信息（map）
// HashMap<String,Object> data = (HashMap<String, Object>) result.get("data");
// Set<String> keySet = data.keySet();
// for(String key:keySet){
// Object object = data.get(key);
// System.out.println(key +" = "+object);
// }
            ok=true;
        }else{
//异常返回输出错误码和错误信息
            //log.info("错误码=" + result.get("statusCode") +" 错误信息= "+result.get("statusMsg"));
            System.out.println("错误码=" + result.get("statusCode") +" 错误信息= "+result.get("statusMsg"));
            ok=false;
        }
        return ok;
    }



}
