package fun.hye.tollsense;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

/**
 * Created by sceint on 6/24/17.
 */

public class UserPage extends AppCompatActivity {

    EditText VIN, name;
    NFCManager nfcManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page_user);

        VIN = (EditText) findViewById(R.id.VIN);
        name = (EditText) findViewById(R.id.name);

        nfcManager = new NFCManager(this);
    }

    boolean checkVIN() {
        if (VIN.equals("")) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Enter VIN");
            builder.setMessage("Please Enter a VIN Number")
                    .setCancelable(false)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });
            AlertDialog alert = builder.create();
            alert.show();
            return false;
        }
        return true;
    }

    boolean checkName() {
        if (name.equals("")) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Enter Name");
            builder.setMessage("Please Enter a Name")
                    .setCancelable(false)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });
            AlertDialog alert = builder.create();
            alert.show();
            return false;
        }
        return true;
    }

    public void programNFC(View v) {
        if (checkVIN() && checkName()) {
            if (nfcManager.verifyNFC()) {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("NFC activated");
                builder.setMessage("NFC is activated on this device")
                        .setCancelable(false)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();
            }
        }
    }
}

