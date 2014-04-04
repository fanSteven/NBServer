package org.nb.reshelper;

import org.apache.log4j.Logger;
import org.nb.mybatis.model.INB_User;
import org.nb.mybatis.model.NB_User;
import org.nb.resource.BaseResource;
import org.nb.resource.user.UnLegalUser;
import org.nb.resource.user.UsersResource;

public class ResHelper extends BaseResource {
	protected static final Logger logger = Logger
			.getLogger(UsersResource.class);

	protected static INB_User inb_User = null;
	static {
		inb_User = session.getMapper(INB_User.class);
	}

	protected UnLegalUser checkUser(NB_User user) {
		if (user.getEmail().trim().equals("")) {
			return UnLegalUser.noEmail;
		} else if (user.getPassword().trim().equals("")) {
			return UnLegalUser.noPassword;
		} else if (isExitEmail(user.getEmail())) {
			return UnLegalUser.exitEmail;
		} else if (isExitUserName(user.getName())) {
			return UnLegalUser.exitUserName;
		}
		return UnLegalUser.noerror;
	}

	private boolean isExitEmail(String email) {
		boolean contain = false;
		if (inb_User.checkEmail(email) != null)
			contain = true;
		return contain;
	}

	private boolean isExitUserName(String userName) {
		boolean contain = false;
		if (inb_User.checkEmail(userName) != null)
			contain = true;
		return contain;
	}
}
