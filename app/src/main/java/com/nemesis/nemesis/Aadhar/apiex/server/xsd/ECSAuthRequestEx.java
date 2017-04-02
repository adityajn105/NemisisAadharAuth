package com.nemesis.nemesis.Aadhar.apiex.server.xsd;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;

@Root(name = "ECSAuthRequestEx")
public class ECSAuthRequestEx {

    @Attribute(name="aadhaarNumber")
    private String aadhaarNumber ;

    @Attribute(name="subType")
    private String subType ;

    @Attribute(name="fdc")
    private String fdc ;

    @Attribute(name="idc")
    private String idc ;

    @Attribute(name="ci")
    private String ci;

    @Attribute(name="key")
    private byte[] key ;

    @Attribute(name="hmac")
    private byte[] hMac ;

    @Attribute(name="pid")
    private byte[] pid ;

    @Attribute(name="pidType")
    private String pidType ;

    @Attribute(name="pidTs")
    private String pidTs ;

    @Attribute(name="terminalId")
    private String terminalId ;

	public String getAadhaarNumber() {
		return aadhaarNumber;
	}

	public void setAadhaarNumber(String aadhaarNumber) {
		this.aadhaarNumber = aadhaarNumber;
	}

	public String getSubType() {
		return subType;
	}

	public void setSubType(String subType) {
		this.subType = subType;
	}

	public String getFdc() {
		return fdc;
	}

	public void setFdc(String fdc) {
		this.fdc = fdc;
	}

	public String getIdc() {
		return idc;
	}

	public void setIdc(String idc) {
		this.idc = idc;
	}

	public String getCi() {
		return ci;
	}

	public void setCi(String ci) {
		this.ci = ci;
	}

	public byte[] getKey() {
		return key;
	}

	public void setKey(byte[] key) {
		this.key = key;
	}

	public byte[] gethMac() {
		return hMac;
	}

	public void sethMac(byte[] hMac) {
		this.hMac = hMac;
	}

	public byte[] getPid() {
		return pid;
	}

	public void setPid(byte[] pid) {
		this.pid = pid;
	}

	public String getPidType() {
		return pidType;
	}

	public void setPidType(String pidType) {
		this.pidType = pidType;
	}

	public String getPidTs() {
		return pidTs;
	}

	public void setPidTs(String pidTs) {
		this.pidTs = pidTs;
	}

	public String getTerminalId() {
		return terminalId;
	}

	public void setTerminalId(String terminalId) {
		this.terminalId = terminalId;
	}
}
