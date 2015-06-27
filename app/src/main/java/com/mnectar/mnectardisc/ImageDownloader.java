package com.mnectar.mnectardisc;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;

import java.io.IOException;

/**
 * Created by ian on 6/26/15.
 */
public class ImageDownloader extends AsyncTask<Uri, Void, Void > {
    private ImageView imageView;
    private RequestQueue queue;
    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    public ImageDownloader(ImageView imageView) {
        super();
        this.imageView = imageView;
        queue=RequestQueueSingleton.getInstance(imageView.getContext().getApplicationContext()).getRequestQueue();
    }

    @Override
    protected Void doInBackground(Uri... params) {
        Log.d("Uri: ", params[0].toString());
        ImageRequest imageRequest = new ImageRequest(params[0].toString(), new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap response) {
                imageView.setImageBitmap(response);
                ((View)imageView.getTag()).setBackground(imageView.getDrawable());
            }
        }, 0, 0, ImageView.ScaleType.CENTER_CROP, null, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        queue.add(imageRequest);
        return null;
    }
}
