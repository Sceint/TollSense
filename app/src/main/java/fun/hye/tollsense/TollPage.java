package fun.hye.tollsense;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class TollPage extends AppCompatActivity implements UploadStatus {

    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page_toll);

        textView = (TextView) findViewById(R.id.message);
    }

    @Override
    public void getDBStatus(String message) {

    }

    @Override

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);

        if (result != null) {
            if (result.getContents() == null) {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("No Data");
                builder.setMessage("QR code not scanned properly")
                        .setCancelable(false)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();
            } else {
                String res[] = result.toString().split("\n");
                String content[] = res[1].split(": ");
                textView.setText(content[1]);
                Socket socket;
                try {
                    socket = IO.socket("http://10.104.240.8:1337/");
                    socket.connect();

                    // This line is cached until the connection is establisched.
                    socket.send(content[1]);
                } catch (Exception e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }

    }

    public void initiateReading(View v) {
        IntentIntegrator i = new IntentIntegrator(this);
        i.initiateScan();
        i.setOrientationLocked(true);
    }
}
