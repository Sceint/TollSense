package fun.hye.tollsense;

import android.content.Intent;
import android.nfc.NdefMessage;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by sceint on 6/24/17.
 */

public class TollPage extends AppCompatActivity implements UploadStatus {

    NfcAdapter nfcAdpt;
    TextView textView;
    Intent nfcIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page_toll);


        textView = (TextView) findViewById(R.id.message);

        nfcIntent = new Intent(this, getClass());
        nfcIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);

        nfcAdpt = NfcAdapter.getDefaultAdapter(this);

        // Check if the smartphone has NFC
        try {
            new NFCManager(this).verifyNFC();

        } catch (
                NFCManager.NFCNotSupported nfcNotSupported)

        {
            Toast.makeText(this, "NFC not supported", Toast.LENGTH_LONG).show();
        } catch (
                NFCManager.NFCNotEnabled nfcNotEnabled)

        {
            Toast.makeText(this, "NFC Not enabled", Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public void onNewIntent(Intent intent) {
        setIntent(intent);
        resolveIntent(intent);
    }

    void resolveIntent(Intent intent) {
        String action = intent.getAction();
        if (NfcAdapter.ACTION_TAG_DISCOVERED.equals(action)
                || NfcAdapter.ACTION_TECH_DISCOVERED.equals(action)
                || NfcAdapter.ACTION_NDEF_DISCOVERED.equals(action)) {

            Parcelable[] rawMsgs = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);
            NdefMessage[] msgs;
            if (rawMsgs != null) {
                msgs = new NdefMessage[rawMsgs.length];
                for (int i = 0; i < rawMsgs.length; i++) {
                    msgs[i] = (NdefMessage) rawMsgs[i];
                }
                buildTagViews(msgs);
            }
        }
    }

    void buildTagViews(NdefMessage msgs[]) {
        String tagId = new String(msgs[0].getRecords()[0].getType());
        String body = new String(msgs[0].getRecords()[0].getPayload());
        textView.setText(tagId + "\n" + body);
    }

    @Override
    public void getDBStatus(String message) {

    }

    public void initiateReading(View v) {
        SendToOnlineDBHelper sendToOnlineDBHelper = new SendToOnlineDBHelper("", this);
        onNewIntent(nfcIntent);
    }
}
