package com.david.util.excel;

import javax.servlet.http.HttpServletRequest;

public class ExcelFileNameUtil {
	/** 
     *  
     * @Title: processFileName 
     *  
     * @Description: ie,chrom,firfox下处理文件名显示乱码 
     */  
    public static String processFileName(HttpServletRequest request, String fileNames) {  
        String codedfilename = null;  
        try {  
            String agent = request.getHeader("USER-AGENT");  
            if (null != agent && -1 != agent.indexOf("MSIE") || null != agent  
                    && -1 != agent.indexOf("Trident")) {// ie  
  
                String name = java.net.URLEncoder.encode(fileNames, "UTF8");  
  
                codedfilename = name;  
            } else if (null != agent && -1 != agent.indexOf("Mozilla")) {// 火狐,chrome等  
  
  
                codedfilename = new String(fileNames.getBytes("UTF-8"), "iso-8859-1");  
            }  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        return codedfilename;  
    }  
    
}
