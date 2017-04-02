package com.nemesis.nemesis.Aadhar.fm220;

public interface IFM220 {

	public void onFM220DeviceConnectError(String error);
	public void onFM220PermissionDevice();
	public void onFM220DeviceConnected();
	public void onFM220CaptureError(String error);
	public void onFM220CaptureCompleted(byte[] isoTemplate);
}
