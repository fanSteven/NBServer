package org.nb.resource.register;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;
import org.nb.mybatis.model.INB_User;
import org.nb.mybatis.model.NB_User;
import org.nb.reshelper.ResHelper;

@Path("register/email/active")
public class Active extends ResHelper {
	private static final Logger logger = Logger.getLogger(Active.class);

	private static INB_User inb_User = null;
	static {
		inb_User = session.getMapper(INB_User.class);
	}

	@GET
	@Produces(MediaType.TEXT_HTML)
	public String active(@QueryParam("u") String u, @QueryParam("p") String p) {
		String htmlStr = "";
		String tempStr = "";
		FileInputStream is = null;
		try {
			String path = Active.class.getClassLoader().getResource("").toURI()
					.getPath();
			is = new FileInputStream(path
					+ "/org/nb/resource/register/login.html");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}// 读取模块文件
		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		try {
			while ((tempStr = br.readLine()) != null)
				htmlStr = htmlStr + tempStr;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			is.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			String userName = u;
			String encryPass = p;
			Map<String, String> params = new HashMap<String, String>();
			params.put("email", userName);
			params.put("password", encryPass);
			NB_User nb_User = inb_User.matchUserFromActivity(params);
			if (nb_User == null) {
				// operateLog.setId(Constant.ERRORCODE);
				// operateLog.setLog("验证失败");
			} else if (new Date().compareTo(nb_User.getRegistertime()) > 24 * 60 * 60) {
				// operateLog.setId(Constant.ERRORCODE);
				// operateLog.setLog("已经过期");
			}
		} catch (Exception ex) {
			logger.error("activity user error", ex);
			// operateLog.setId(Constant.ERRORCODE);
			// operateLog.setLog("服务器端异常");
		}

		return htmlStr;
	}
}
