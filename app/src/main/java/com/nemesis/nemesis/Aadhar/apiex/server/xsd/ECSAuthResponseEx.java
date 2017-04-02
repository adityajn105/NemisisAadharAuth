package com.nemesis.nemesis.Aadhar.apiex.server.xsd;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;

@Root(name = "ECSAuthResponseEx")
public class ECSAuthResponseEx {

	@Attribute(name="err")
    private boolean err ;

    @Attribute(name="errCode", required = false)
    private String errCode ;

    @Attribute(name="errMsg", required = false)
    private String errMsg ;

    @Attribute(name="actionCode", required = false)
    private String actionCode ;

    @Attribute(name="actionMsg", required = false)
    private String actionMsg ;

    @Attribute(name="code", required = false)
    private String aadhaarResponseCode ;

	public boolean isErr() {
		return err;
	}

	public void setErr(boolean err) {
		this.err = err;
	}

	public String getErrCode() {
		return errCode;
	}

	public void setErrCode(String errCode) {
		this.errCode = errCode;
	}

	public String getErrMsg() {
		return errMsg;
	}

	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}

	public String getActionCode() {
		return actionCode;
	}

	public void setActionCode(String actionCode) {
		this.actionCode = actionCode;
	}

	public String getActionMsg() {
		return actionMsg;
	}

	public void setActionMsg(String actionMsg) {
		this.actionMsg = actionMsg;
	}

	public String getAadhaarResponseCode() {
		return aadhaarResponseCode;
	}

	public void setAadhaarResponseCode(String aadhaarResponseCode) {
		this.aadhaarResponseCode = aadhaarResponseCode;
	}
}
