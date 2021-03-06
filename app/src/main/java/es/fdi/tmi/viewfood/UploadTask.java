package es.fdi.tmi.viewfood;

import android.os.AsyncTask;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Class used to communicate asynchronously with the project's server.
 * */
public class UploadTask extends AsyncTask<String, String, String>
{
    private final String SERVER_URL = "http://35.246.247.149/api/menu/menu/upload/";
    private MainActivity _mainActivity;

    public UploadTask(MainActivity ma)
    {
        _mainActivity = ma;
    }

    @Override
    protected String doInBackground(String... strings)
    {
        uploadFile(strings[0], strings[1]);

        return "task ended";
    }

    private void uploadFile(String path, String targetTranslation)
    {
        //Create a request with all the necessary fields to send to the server.
        File f = new File(path);
        RequestBody requestBody = new MultipartBody.Builder().setType(MultipartBody.FORM).
                addFormDataPart("image", f.getName(), RequestBody.create(MediaType.parse("image/*"), f)).
                addFormDataPart("lang", targetTranslation).
                build();
        Request request = new Request.Builder().url(SERVER_URL).post(requestBody).build();
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .build();

        //Send the request to the server.
        client.newCall(request).enqueue(new Callback()
        {
            @Override
            public void onFailure(Call call, IOException e)
            {
                _mainActivity.runOnUiThread(() -> _mainActivity.displayAlert("A network error has occurred. Please check your internet connection and try again"));
            }

            @Override
            public void onResponse(Call call, Response response)
            {
                //Make sure the response is successful before trying to access its fields.
                if(response.isSuccessful())
                {
                    try
                    {
                        JSONObject jObject = new JSONObject(response.body().string());
                        String translatedText = jObject.getString("description");
                        String translatedImageURL = jObject.getString("image");

                        _mainActivity.runOnUiThread(() -> _mainActivity.setResponseFromServer(translatedText, translatedImageURL));
                    }
                    catch(JSONException | IOException e)
                    {
                        _mainActivity.runOnUiThread(() -> _mainActivity.displayAlert("An error has occurred. Please try again"));
                    }
                }
                else
                {
                    _mainActivity.runOnUiThread(() -> _mainActivity.displayAlert("An error has occurred. Please try again"));
                }
            }
        });
    }
}