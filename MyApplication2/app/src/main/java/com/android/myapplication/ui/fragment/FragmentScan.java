package com.android.myapplication.ui.fragment;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.android.myapplication.R;
import com.android.myapplication.constant.ReportType;
import com.android.myapplication.scan.NoScanResultException;
import com.android.myapplication.scan.ScanResultReceiver;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

/**
 * Created by menginar on 02.10.2017.
 */

public class FragmentScan extends Fragment {
    private String codeFormat, codeContent;
    private final String noResultErrorMsg = "Veri Okunamadı!!";
    private Bundle arguments;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            arguments = getArguments();

            IntentIntegrator integrator = new IntentIntegrator(this.getActivity()).forSupportFragment(this);
            // use forSupportFragment or forFragment method to use fragments instead of activity
            integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);

            integrator.setPrompt(this.getString(R.string.scan_bar_code));
            integrator.setResultDisplayDuration(0); // milliseconds to display result on screen after scan
            integrator.setCameraId(0);  // Use a specific camera of the device
            integrator.initiateScan();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    /**
     * function handle scan result
     *
     * @param requestCode scanned code
     * @param resultCode  result of scanned code
     * @param intent      intent
     */
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {

        try {
            //retrieve scan result
            IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
            ScanResultReceiver parentActivity = (ScanResultReceiver) this.getActivity();

            if (scanningResult != null) {
                //we have a result

                if (arguments != null && arguments.containsKey("fragmenttype")) {

                    codeContent = scanningResult.getContents();
                    codeFormat = scanningResult.getFormatName();

                    if (arguments.getString("fragmenttype").equals(ReportType.SEARCH)) {
                        parentActivity.scanResultData(codeFormat, codeContent, ReportType.SEARCH);
                    }

                    if (arguments.getString("fragmenttype").equals(ReportType.STOCKLIST)) {
                        parentActivity.scanResultData(codeFormat, codeContent, ReportType.STOCKLIST);
                    }

                    if (arguments.getString("fragmenttype").equals(ReportType.STOCKDELUP)) {
                        parentActivity.scanResultData(codeFormat, codeContent, ReportType.STOCKDELUP);
                    }

                    if (arguments.getString("fragmenttype").equals(ReportType.STOCKINOU)) {
                        parentActivity.scanResultData(codeFormat, codeContent, ReportType.STOCKINOU);
                    }

                    if (arguments.getString("fragmenttype").equals(ReportType.STOCKSAVE)) {
                        parentActivity.scanResultData(codeFormat, codeContent, ReportType.STOCKSAVE);
                    }
                }

            } else {
                parentActivity.scanResultData(new NoScanResultException(noResultErrorMsg));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}