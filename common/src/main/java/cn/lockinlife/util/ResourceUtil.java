package cn.lockinlife.util;


import javax.servlet.http.HttpServletRequest;

/**
 * 项目参数工具类
 * 
 */
public class ResourceUtil {

	public static String getRequestPath(HttpServletRequest request) {
		String queryString = request.getQueryString();
		String requestPath = request.getRequestURI();
		if(queryString != null){
			requestPath += "?" + queryString;
		}
		/*if (requestPath.indexOf("&") > -1) {
			requestPath = requestPath.substring(0, requestPath.indexOf("&"));
		}*/
		requestPath = requestPath.substring(request.getContextPath().length() + 1);// 去掉项目路径
		return requestPath;
	}

}
