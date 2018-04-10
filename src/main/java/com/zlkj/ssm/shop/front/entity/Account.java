package com.zlkj.ssm.shop.front.entity;import java.io.Serializable;import com.zlkj.ssm.shop.enums.LoginTypeEnum;/** * 会员 */public class Account extends com.zlkj.ssm.shop.entity.common.Account implements Serializable {	private static final long serialVersionUID = 1L;	// 修改密码	private String password2;	private String newPassword;	private String newPassword2;	private String vcode;// 注册时候输入的验证码		public static final String account_freeze_y = "y";//已冻结	public static final String account_freeze_n = "n";//未冻结	public static final String account_emailIsActive_y = "y";//邮箱已激活	public static final String account_emailIsActive_n = "n";//邮箱未激活		private LoginTypeEnum loginType;//登陆方式		private String newEmail;//新邮箱，修改绑定的邮箱//	private String orderid;//用户订单号，订单完毕后更新此订单的积分到用户头上	private int addScore;//订单完毕后，此用户增加的积分数，此积分数最后会被增加到用户的积分账户上		public void clear() {		super.clear();		newEmail = null;		password2 = null;		newPassword = null;		newPassword2 = null;		loginType = null;//		orderid = null;		addScore = 0;	}	public String getPassword2() {		return password2;	}	public void setPassword2(String password2) {		this.password2 = password2;	}	public String getNewPassword() {		return newPassword;	}	public void setNewPassword(String newPassword) {		this.newPassword = newPassword;	}	public String getNewPassword2() {		return newPassword2;	}	public void setNewPassword2(String newPassword2) {		this.newPassword2 = newPassword2;	}	public String getVcode() {		return vcode;	}	public void setVcode(String vcode) {		this.vcode = vcode;	}	public LoginTypeEnum getLoginType() {		return loginType;	}	public void setLoginType(LoginTypeEnum loginType) {		this.loginType = loginType;	}	public String getNewEmail() {		return newEmail;	}	public void setNewEmail(String newEmail) {		this.newEmail = newEmail;	}	public int getAddScore() {		return addScore;	}	public void setAddScore(int addScore) {		this.addScore = addScore;	}}