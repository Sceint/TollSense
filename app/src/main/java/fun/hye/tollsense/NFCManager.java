package fun.hye.tollsense;

import android.app.Activity;
import android.content.DialogInterface;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.nfc.tech.NdefFormatable;
import android.support.v7.app.AlertDialog;

import java.io.ByteArrayOutputStream;
import java.util.Locale;

/**
 * Created by sceint on 6/24/17.
 */

public class NFCManager {
    private Activity activity;
    private NfcAdapter nfcAdpt;

    public NFCManager(Activity activity) {
        this.activity = activity;
    }

    public boolean verifyNFC() {
        nfcAdpt = NfcAdapter.getDefaultAdapter(activity);

        if (nfcAdpt == null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(activity);
            builder.setTitle("NFC not supported");
            builder.setMessage("NFC is not supported with this device")
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

        if (!nfcAdpt.isEnabled()) {
            AlertDialog.Builder builder = new AlertDialog.Builder(activity);
            builder.setTitle("NFC not enabled");
            builder.setMessage("NFC is present but not turned on")
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

    public void writeTag(Tag tag, NdefMessage message) {
        if (tag != null) {
            try {
                Ndef ndefTag = Ndef.get(tag);
                if (ndefTag == null) {
                    // Let's try to format the Tag in NDEF
                    NdefFormatable nForm = NdefFormatable.get(tag);
                    if (nForm != null) {
                        nForm.connect();
                        nForm.format(message);
                        nForm.close();
                    }
                } else {
                    ndefTag.connect();
                    ndefTag.writeNdefMessage(message);
                    ndefTag.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    public NdefMessage createExternalMessage(String content) {
        NdefRecord externalRecord = NdefRecord.createExternal("com.survivingwithandroid", "data", content.getBytes());

        return new NdefMessage(new NdefRecord[]{externalRecord});
    }
}
