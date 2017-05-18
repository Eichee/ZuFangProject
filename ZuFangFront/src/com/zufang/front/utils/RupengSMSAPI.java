package com.zufang.front.utils;

import java.io.IOException;
import java.net.URL;

import org.apache.commons.io.IOUtils;

import com.google.gson.Gson;
import com.zufang.service.SettingService;
import com.zufang.tools.CommonUtils;

public class RupengSMSAPI {

	public static RupengSMSResult send(String code,String phoneNum){
		SettingService settingService=new SettingService();
		String templateId=settingService.getValue("RuPengSMS.TemplateId");
		return send(code,phoneNum,templateId);
	}
	
	public static RupengSMSResult send(String code, String phoneNum,String templateId){
		SettingService settingService=new SettingService();
		String username=settingService.getValue("RuPengSMS.UserName");
		String appkey=settingService.getValue("RuPengSMS.AppKey");
		String sendUrl="http://sms.rupeng.cn/SendSms.ashx?userName="+CommonUtils.urlEncodeUTF8(username)
				+"&appKey="+CommonUtils.urlEncodeUTF8(appkey)
				+"&templateId="+CommonUtils.urlEncodeUTF8(templateId)
				+"&code="+CommonUtils.urlEncodeUTF8(code)
				+"&phoneNum="+CommonUtils.urlEncodeUTF8(phoneNum);
		
		try{
			String resp=IOUtils.toString(new URL(sendUrl),"UTF-8");
			Gson gson=CommonUtils.createGson();
			System.out.println(code);
			return gson.fromJson(resp,RupengSMSResult.class);
		}
		catch(IOException e){
			throw new RuntimeException(e);
		}
	}
	
}
