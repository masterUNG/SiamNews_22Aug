package siam.go.mint.num.siamdailynew.manage;

import android.content.Context;
import android.os.AsyncTask;

import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

/**
 * Created by masterung on 8/22/2017 AD.
 */

public class PostNewMember extends AsyncTask<String, Void, String>{

    private Context context;

    public PostNewMember(Context context) {
        this.context = context;
    }

    @Override
    protected String doInBackground(String... strings) {

        try {

            MyConstant myConstant = new MyConstant();
            String[] columnStrings1 = myConstant.getColumnMemberStrings();

            OkHttpClient okHttpClient = new OkHttpClient();
            RequestBody requestBody = new FormEncodingBuilder()
                    .add("isAdd", "true")
                    .add(columnStrings1[0], strings[0])
                    .add(columnStrings1[1], strings[1])
                    .add(columnStrings1[2], strings[2])
                    .add(columnStrings1[3], strings[3])
                    .add(columnStrings1[4], strings[4])
                    .add(columnStrings1[5], strings[5])
                    .add(columnStrings1[6], strings[6])
                    .build();
            Request.Builder builder = new Request.Builder();
            Request request = builder.url(strings[7]).post(requestBody).build();
            Response response = okHttpClient.newCall(request).execute();
            return response.body().string();

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }


    }
}   // Main Class
