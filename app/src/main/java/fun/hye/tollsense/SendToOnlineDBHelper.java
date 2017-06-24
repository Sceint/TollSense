package fun.hye.tollsense;

import android.os.AsyncTask;

import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

class SendToOnlineDBHelper extends AsyncTask<JSONArray, Void, String> {
    private UploadStatus uploadStatus;
    String file;

    SendToOnlineDBHelper(String file, UploadStatus uploadStatus) {
        this.uploadStatus = uploadStatus;
        this.file = file;
    }

    @Override
    protected String doInBackground(JSONArray... resultArray) {
        try {
            String login_url = "https://sceint.000webhostapp.com/";
            URL url = new URL(login_url + file);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);

            OutputStream outputStream = httpURLConnection.getOutputStream();
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
            bufferedWriter.write(resultArray[0].toString());
            bufferedWriter.flush();
            bufferedWriter.close();
            outputStream.close();

            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
            String s;
            StringBuilder sb = new StringBuilder("");
            while ((s = bufferedReader.readLine()) != null)
                sb.append(s);
            bufferedReader.close();
            inputStream.close();
            httpURLConnection.disconnect();
            return sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        if (s == null || !s.equals("Successful"))
            uploadStatus.getDBStatus("Failed");
        else
            uploadStatus.getDBStatus(s);
    }
}