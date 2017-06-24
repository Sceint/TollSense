package fun.hye.tollsense;

import android.database.Cursor;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

class CreateJSON {
    JSONArray SQLite2JSON(Cursor cursor) {
        JSONArray resultArray = new JSONArray();

        int columnCount = cursor.getColumnCount();
        while (cursor.moveToNext()) {
            JSONObject row = new JSONObject();
            for (int i = 0; i < columnCount; i++)
                try {
                    row.put(cursor.getColumnName(i), cursor.getString(i));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            resultArray.put(row);
        }
        return resultArray;
    }
}
