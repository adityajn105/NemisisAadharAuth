package com.startek.fm210;

public class tstlib {
	static {
    	System.loadLibrary("startek_jni");
    }
    public native static void SetFPLibraryPath(String filepath);
    public native static void InitialSDK();
    public native static int FP_ConnectCaptureDriver(int number);
    public native static void FP_DisconnectCaptureDriver();
    public native static int FP_Capture();
    public native static int FP_CheckBlank();
// TODO Remove unused code found by UCDetector
//     public native static void FP_SaveImageBMP(String filepath);
    public native static int FP_CreateEnrollHandle();
    public native static int FP_GetTemplate(byte[] m1);
    public native static int FP_ISOminutiaEnroll(byte[] m1, byte[] m2);
    public native static void FP_SaveISOminutia(byte[] m2, String filepath);
    public native static void FP_DestroyEnrollHandle();
    public native static int FP_LoadISOminutia(byte[] m2, String filepath); 
    public native static int FP_ISOminutiaMatchEx(byte[] m1, byte[] m2);
    public native static int FP_ISOminutiaMatch360Ex(byte[] m1, byte[] m2);
    public native static int Score();
    public native static void FP_GetImageBuffer(byte[] bmpBuffer);
// TODO Remove unused code found by UCDetector
//     public native static int FP_GetImageWidth();
    private native int FP_GetImageHeight();
    private native int FP_LedOff();

 
}
