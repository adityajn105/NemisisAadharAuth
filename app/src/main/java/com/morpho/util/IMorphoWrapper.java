package com.morpho.util;

public interface IMorphoWrapper {

	void onFingerDeviceError(String error);
	void onFingerCaptured(final byte[] fingertemplate);
}
