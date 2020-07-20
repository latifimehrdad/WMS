package com.ngra.wms.viewmodels.main;

import android.app.Activity;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Pair;

import androidx.core.content.FileProvider;

import com.ngra.wms.daggers.retrofit.RetrofitComponent;
import com.ngra.wms.utility.StaticValues;
import com.ngra.wms.viewmodels.VM_Primary;
import com.ngra.wms.views.application.ApplicationWMS;

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

    private DownloadZipFileTask downloadZipFileTask;
    private String FileName;
    private ProgressDownload progressDownload;

    public VM_Update(Activity context, ProgressDownload progressDownload) {//_______________________ VM_Update
        setContext(context);
        this.progressDownload = progressDownload;
    }//_____________________________________________________________________________________________ VM_Update


    public interface ProgressDownload {//___________________________________________________________ ProgressDownload

        void onProgress(int progress);
    }//_____________________________________________________________________________________________ ProgressDownload


    public void DownloadFile(String url, String fileName) {//_______________________________________ DownloadFile

        FileName = fileName;

        RetrofitComponent retrofitComponent = ApplicationWMS.getApplicationWMS(getContext())
                .getRetrofitComponent();

        setPrimaryCall(
                retrofitComponent
                        .getRetrofitApiInterface()
                        .downloadFile(url));

        getPrimaryCall().enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, final Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    setResponseMessage("");
                    SendMessageToObservable(StaticValues.ML_Success);
                    downloadZipFileTask = new DownloadZipFileTask();
                    downloadZipFileTask.execute(response.body());

                } else {

                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                t.printStackTrace();
            }
        });


    }//_____________________________________________________________________________________________ DownloadFile


    private class DownloadZipFileTask extends AsyncTask<ResponseBody, Pair<Integer, Long>, String> {// DownloadZipFileTask

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            SendMessageToObservable(StaticValues.ML_FileDownloading);

        }

        @Override
        protected String doInBackground(ResponseBody... urls) {
            saveToDisk(urls[0], FileName);
            return null;
        }

        protected void onProgressUpdate(Pair<Integer, Long>... progress) {


            if (progress[0].first == 100)
                SendMessageToObservable(StaticValues.ML_FileDownloaded);


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
    }//_____________________________________________________________________________________________ DownloadZipFileTask


    private void saveToDisk(ResponseBody body, String filename) {//_________________________________ saveToDisk
        try {

            File file = new File(Environment.getExternalStorageDirectory() + "/WMS");
            if (!file.exists()) {
                file.mkdir();
            }

            File destinationFile = new File(Environment.getExternalStorageDirectory() + "/WMS/" + filename);

            InputStream inputStream = null;
            OutputStream outputStream = null;

            try {
                inputStream = body.byteStream();
                outputStream = new FileOutputStream(destinationFile);
                byte data[] = new byte[4096];
                int count;
                int progress = 0;
                long fileSize = body.contentLength();
                while ((count = inputStream.read(data)) != -1) {
                    outputStream.write(data, 0, count);
                    progress += count;
                    Pair<Integer, Long> pairs = new Pair<>(progress, fileSize);
                    downloadZipFileTask.doProgress(pairs);
                }

                outputStream.flush();

                Pair<Integer, Long> pairs = new Pair<>(100, 100L);
                downloadZipFileTask.doProgress(pairs);
                return;
            } catch (IOException e) {
                e.printStackTrace();
                Pair<Integer, Long> pairs = new Pair<>(-1, Long.valueOf(-1));
                downloadZipFileTask.doProgress(pairs);

                return;
            } finally {
                if (inputStream != null) inputStream.close();
                if (outputStream != null) outputStream.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
    }//_____________________________________________________________________________________________ saveToDisk



    public Uri getTempUri(String filename) {//____________________________________________________________________ Start getTempUri
        // Create an image file name
        File imageFile = null;
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
    }//_____________________________________________________________________________________________ End getTempUri



    public String getFileName() {//_________________________________________________________________ getFileName
        return FileName;
    }//_____________________________________________________________________________________________ getFileName


}
