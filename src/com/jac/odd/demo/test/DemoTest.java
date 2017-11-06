package com.jac.odd.demo.test;

import java.util.HashMap;
import java.util.Map;
import net.sf.json.JSONObject;
import com.jac.odd.demo.utils.DataUtils;
import com.jac.odd.demo.utils.UnicodeUtils;
import com.jac.odd.demo.utils.requestUtil;
//
public class DemoTest {
	private static String URL = "http://192.168.10.92:8080/odd_server/odd_server?";//测试地址,根据实际提供地址修改
	private static String URL_LOCALHOST = "http://localhost:8086/odd/odd_server?";//本地测试地址
	private static String URL_OUT = "http://220.178.49.203:9088//odd_server/odd_server?";//测试外网地址,根据实际提供地址修改
	private static String ACTION_QUERY = "actionQuery";
	private static String method = "test_name";//根据实际需求修改
	private static String airm = "insert";
	public static void main(String[] args) throws Exception {
		//data主业务数据业务逻辑
		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put("order_no", "订单号");
		dataMap.put("dealer_code", "经销商编码");
		dataMap.put("driver_name", "司机姓名");
		//转换成json格式
		JSONObject jsonObject = new JSONObject();
		jsonObject.putAll(DataUtils.convertObjectToStringParams(dataMap));
		String postData = jsonObject.toString();
		System.out.println("NO1:"+postData);
		//对JSON内容进行ENCODE转换
		postData = UnicodeUtils.toUnicodeString(postData);
		System.out.println("NO2:"+postData);
		
		//invoke type01 no 
//		String html = requestUtil.getResponseData(URL+requestData(ACTION_QUERY, method, airm)+postData, postData);
		
		//invoke type02
		String html = requestUtil.post(DataUtils.convertObjectToStringParams(getParamMap(postData)), URL_LOCALHOST);
		
		System.out.println("NO4:"+html);
	}
	
	private static String requestData(String interfaceType,String method,String airm) {
		StringBuffer urlBuf = new StringBuffer();
		urlBuf.append("interfaceType=");
		urlBuf.append(interfaceType);
		urlBuf.append("&action=");
		urlBuf.append(method);
		urlBuf.append("&airm=");
		urlBuf.append(airm);
		urlBuf.append("&data=");
		return urlBuf.toString();
	}
	private static Map<String, Object> getParamMap(String postData){
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("interfaceType", ACTION_QUERY);
		paramMap.put("action", method);
		paramMap.put("airm",airm);
		paramMap.put("data", postData);
		return paramMap;
	}
	/**
	>>>SUCESS MESSAGE
	NO1:{"driver_name":"司机姓名","dealer_code":"经销商编码","order_no":"订单号"}
	NO2:{"driver_name":"\u53f8\u673a\u59d3\u540d","dealer_code":"\u7ecf\u9500\u5546\u7f16\u7801","order_no":"\u8ba2\u5355\u53f7"}
	NO4:{"code":101,"message":"success"}
	
	>>>ERROR MESSAGE
	NO4:{"code":404,"message":"网络连接超时！"}
	NO4:{"code":202,"message":"URL IS ERROR！"}
	NO4:FORBIDDEN!
	 */
}