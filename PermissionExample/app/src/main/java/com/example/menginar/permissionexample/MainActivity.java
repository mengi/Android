package com.example.menginar.permissionexample;

import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;

import rebus.permissionutils.AskAgainCallback;
import rebus.permissionutils.FullCallback;
import rebus.permissionutils.PermissionEnum;
import rebus.permissionutils.PermissionManager;

public class MainActivity extends AppCompatActivity implements FullCallback {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ArrayList<PermissionEnum> permissionEnumArrayList = new ArrayList<>();
        permissionEnumArrayList.add(PermissionEnum.ACCESS_FINE_LOCATION);
        permissionEnumArrayList.add(PermissionEnum.GET_ACCOUNTS);
        permissionEnumArrayList.add(PermissionEnum.READ_CONTACTS);


        PermissionManager.Builder()
                .key(9000)
                .permissions(permissionEnumArrayList)
                .askAgain(true)
                .askAgainCallback(new AskAgainCallback() {
                    @Override
                    public void showRequestPermission(UserResponse response) {
                        showDialog(response);
                    }
                })
                .callback(MainActivity.this)
                .ask(MainActivity.this);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        PermissionManager.handleResult(this, requestCode, permissions, grantResults);
    }

    @Override
    public void result(ArrayList<PermissionEnum> arrayList, ArrayList<PermissionEnum> arrayList1, ArrayList<PermissionEnum> arrayList2, ArrayList<PermissionEnum> arrayList3) {

    }

    private void showDialog(final AskAgainCallback.UserResponse response) {
        new AlertDialog.Builder(MainActivity.this)
                .setTitle("Permission needed")
                .setMessage("This app realy need to use this permission, you wont to authorize it?")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        response.result(true);
                    }
                })
                .setNegativeButton("NOT NOW", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        response.result(false);
                    }
                })
                .setCancelable(false)
                .show();
    }
}
