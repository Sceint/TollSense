package fun.hye.tollsense;

import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.nfc.NdefMessage;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class UserPage extends AppCompatActivity {

    TextView VIN, address;
    Spinner spinner;
    NFCManager nfcManager;
    Intent nfcIntent;
    Tag currentTag;
    NdefMessage message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page_user);

        VIN = (TextView) findViewById(R.id.VIN);
        address = (TextView) findViewById(R.id.address);
        spinner = (Spinner) findViewById(R.id.spinner);

        nfcManager = new NFCManager(this);
    }

    public void programNFC(View v) {
        String message = spinner.getSelectedItem().toString();
        if (message.equals("Select")) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Select User");
            builder.setMessage("Please Select a user from drop down")
                    .setCancelable(false)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });
            AlertDialog alert = builder.create();
            alert.show();
            return;
        }
        Cursor cursor = OfflineDBHelper.getInstance(this).getUserInfo(message);
        while (cursor.moveToNext()) {
            message += "," + cursor.getString(0);
            message += "," + cursor.getString(1);
            message += "," + cursor.getString(2);
            VIN.setText(cursor.getString(1));
            address.setText(cursor.getString(2));
        }
        this.message = nfcManager.createExternalMessage(message);
        onNewIntent(nfcIntent);
    }

    @Override
    protected void onResume() {
        super.onResume();


        try {
            nfcManager.verifyNFC();
            //nfcMger.enableDispatch();

            nfcIntent = new Intent(this, getClass());
            nfcIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, nfcIntent, 0);
            IntentFilter[] intentFiltersArray = new IntentFilter[]{};
            String[][] techList = new String[][]{{android.nfc.tech.Ndef.class.getName()}, {android.nfc.tech.NdefFormatable.class.getName()}};
            NfcAdapter nfcAdpt = NfcAdapter.getDefaultAdapter(this);
            nfcAdpt.enableForegroundDispatch(this, pendingIntent, intentFiltersArray, techList);
        } catch (NFCManager.NFCNotSupported nfcNotSupported) {
            Toast.makeText(this, "NFC not supported", Toast.LENGTH_LONG).show();
        } catch (NFCManager.NFCNotEnabled nfcNotEnabled) {
            Toast.makeText(this, "NFC Not enabled", Toast.LENGTH_LONG).show();
        }
    }


    @Override
    protected void onPause() {
        super.onPause();
        //nfcManager.disableDispatch();
    }

    @Override
    public void onNewIntent(Intent intent) {
        // It is the time to write the tag
        currentTag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
        if (message != null) {
            nfcManager.writeTag(currentTag, message);
            Toast.makeText(this, "Tag Written", Toast.LENGTH_LONG).show();

        } else {
            // Handle intent

        }
    }
}

