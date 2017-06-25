package fun.hye.tollsense;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

/**
 * Created by sceint on 6/25/17.
 */

public class UpdateDB extends AppCompatActivity {

    Spinner spinner;
    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.db_update);

        spinner = (Spinner) findViewById(R.id.spinner);

        editText = (EditText) findViewById(R.id.address);
    }

    public void updateUser(View v) {
        String user = spinner.getSelectedItem().toString();
        if (user.equals("Select")) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Select User");
            builder.setMessage("Please Select a User")
                    .setCancelable(false)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });
            AlertDialog alert = builder.create();
            alert.show();
        } else {
            OfflineDBHelper.getInstance(this).updateUser(user, editText.getText().toString());
            Toast.makeText(this, "Successful", Toast.LENGTH_LONG);
        }
    }
}
