package com.android.myapplication.scan;

/**
 * Created by menginar on 02.10.2017.
 */

public interface ScanResultReceiver {

    /**
     * function to receive scanresult
     * @param codeFormat format of the barcode scanned
     * @param codeContent data of the barcode scanned
     */
    public void scanResultData(String codeFormat, String codeContent, String typeFragment);

    public void scanResultData(NoScanResultException noScanData);
}
