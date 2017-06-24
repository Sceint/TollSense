package fun.hye.tollsense;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;


public class MainActivity extends AppCompatActivity {

    EditText user, pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        user = (EditText) findViewById(R.id.user);
        pass = (EditText) findViewById(R.id.pass);
    }

    public void goTo(View v) {
        Intent intent;
        if (user.getText().toString().equals("user") && pass.getText().toString().equals("password")) {
            intent = new Intent(MainActivity.this, UserPage.class);
            startActivity(intent);
        } else if (user.getText().toString().equals("toll") && pass.getText().toString().equals("password"))

        {

        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Invalid Details");
            builder.setMessage("Please Enter a Valid Username and password")
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
