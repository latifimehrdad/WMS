package com.ngra.wms.viewmodels;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Pair;

import androidx.core.content.FileProvider;

import com.ngra.wms.daggers.retrofit.RetrofitComponent;
import com.ngra.wms.utility.DownloadTask;
import com.ngra.wms.utility.StaticValues;
import com.ngra.wms.views.application.ApplicationWMS;
import com.yangp.ypwaveview.YPWaveView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VM_Update extends VM_Primary {

    private downloadFileTask downloadFileTask;
    private String fileName;
    private ProgressDownload progressDownload;


    //______________________________________________________________________________________________ VM_Update
    public VM_Update(Activity context, ProgressDownload progressDownload) {
        setContext(context);
        this.progressDownload = progressDownload;
    }
    //______________________________________________________________________________________________ VM_Update


    //______________________________________________________________________________________________ ProgressDownload
    public interface ProgressDownload {

        void onProgress(int progress);
    }
    //______________________________________________________________________________________________ ProgressDownload



    //______________________________________________________________________________________________ downloadFile
    public void downloadFile(String url, String filePath, YPWaveView ypWaveView) {


        DownloadTask downloadTask = new DownloadTask(getContext(), filePath, getPublishSubject());
        downloadTask.execute(url);
    }
    //______________________________________________________________________________________________ downloadFile




    //______________________________________________________________________________________________ downloadFile
    public void downloadFile(String url, String fileName) {

        this.fileName = fileName;

        RetrofitComponent retrofitComponent = ApplicationWMS.getApplicationWMS(getContext())
                .getRetrofitComponent();

        setPrimaryCall(
                retrofitComponent
                        .getRetrofitApiInterface()
                        .downloadFile(url));

        if (getPrimaryCall() == null)
            return;

        getPrimaryCall().enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, final Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    setResponseMessage("");
                    sendActionToObservable(StaticValues.ML_Success);
                    downloadFileTask = new downloadFileTask();
                    downloadFileTask.execute(response.body());

                } else {

                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                t.printStackTrace();
            }


            
        });


    }
    //______________________________________________________________________________________________ downloadFile


    //______________________________________________________________________________________________ downloadFileTask
    private class downloadFileTask extends AsyncTask<ResponseBody, Pair<Integer, Long>, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            sendActionToObservable(StaticValues.ML_FileDownloading);

        }

        @Override
        protected String doInBackground(ResponseBody... urls) {
            saveToDisk(urls[0], fileName);
            return null;
        }

        protected void onProgressUpdate(Pair<Integer, Long>... progress) {


            if (progress[0].first == 100)
                sendActionToObservable(StaticValues.ML_FileDownloaded);


            if (progress[0].second > 0) {
                int currentProgress = (int) ((double) progress[0].first / (double) progress[0].second * 100);
                progressDownload.onProgress(currentProgress);

            }

        }

        public void doProgress(Pair<Integer, Long> progressDetails) {
            publishProgress(progressDetails);
        }

        @Override
        protected void onPostExecute(String result) {

        }
    }
    //______________________________________________________________________________________________ downloadFileTask


    //______________________________________________________________________________________________ saveToDisk
    private void saveToDisk(ResponseBody body, String filename) {

        File file = new File(Environment.getExternalStorageDirectory() + "/WMS");
        if (!file.exists()) {
            file.mkdir();
        }

        File destinationFile = new File(Environment.getExternalStorageDirectory() + "/WMS/" + filename);

        try (InputStream inputStream = body.byteStream(); OutputStream outputStream = new FileOutputStream(destinationFile)) {
            byte[] data = new byte[4096];
            int count;
            int progress = 0;
            long fileSize = body.contentLength();
            while ((count = inputStream.read(data)) != -1) {
                outputStream.write(data, 0, count);
                progress += count;
                Pair<Integer, Long> pairs = new Pair<>(progress, fileSize);
                downloadFileTask.doProgress(pairs);
            }

            outputStream.flush();

            Pair<Integer, Long> pairs = new Pair<>(100, 100L);
            downloadFileTask.doProgress(pairs);
        } catch (IOException e) {
            e.printStackTrace();
            Pair<Integer, Long> pairs = new Pair<>(-1, (long) -1);
            downloadFileTask.doProgress(pairs);

        }
    }
    //______________________________________________________________________________________________ saveToDisk


    //______________________________________________________________________________________________ getTempUri
    public Uri getTempUri(String filename) {
        // Create an image file name
        File imageFile;
        imageFile = new File(Environment.getExternalStorageDirectory()
                + "/WMS/", filename);

//        String imagePath = Environment.getExternalStorageDirectory() + "/MyApp/"
//                + "Camera_" + dt + ".jpg";
        Uri imageUri;// = Uri.fromFile(imageFile);

        imageUri = FileProvider.getUriForFile(
                getContext(),
                "com.ngra.wms.provider", //(use your app signature + ".provider" )
                imageFile);

        return imageUri;
    }
    //______________________________________________________________________________________________ getTempUri


}
