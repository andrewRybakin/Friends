package ru.dzen.friends.controllers;

import android.os.AsyncTask;
import android.util.Log;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

/**
 * Таск коннекта
 * Возвращает объект, указанного в шаблоне типа
 */
public class HttpRequestTask<RequiredModel> extends AsyncTask<String, Void, RequiredModel> {
    private static final String LOG_TAG = "HttpRequestTask";
    Class<RequiredModel> type;
    //Функция для postExecute
    OnPostExecute<RequiredModel> onPostExecute;

    /**
     * @param type Тип класса модели, который нужно заполнить с сервера.
     *             ВНИМАНИЕ: класс должен быть прям совсем публичным (то биш в отдельном файле), иначе будут траблы
     */
    public HttpRequestTask(Class<RequiredModel> type) {
        this.type = type;
    }

    /**
     * @param type          Тип класса модели, который нужно заполнить с сервера.
     *                      ВНИМАНИЕ: класс должен быть прям совсем публичным (то биш в отдельном файле), иначе будут траблы
     * @param onPostExecute Функция, выполняемая в postExecute
     */
    public HttpRequestTask(Class<RequiredModel> type, OnPostExecute<RequiredModel> onPostExecute) {
        this.type = type;
        this.onPostExecute = onPostExecute;
    }

    @Override
    protected void onPostExecute(RequiredModel f) {
        Log.d(LOG_TAG, "PostExecute!");
        if (onPostExecute != null) {
            Log.d(LOG_TAG, "onPostExecute not null!");
            onPostExecute.onPostExecute(f);
        }
    }

    /**
     * @param params Требуется единственный параметр: адрес запрашиваемого дейстивя на сервере
     * @return Ответ сервера
     */
    @Override
    protected RequiredModel doInBackground(String... params) {
        try {
            final String url = "https://zen-server.herokuapp.com/" + params[0];
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
            //Следующая строка как раз создает экземпляр класса с пропарсенными в него данными из json'a
            RequiredModel o = restTemplate.getForObject(url, type);
            return o;
        } catch (Exception e) {
            Log.e("RemoteController", e.getMessage(), e);
        }
        return null;
    }

    interface OnPostExecute<RequiredModel> {
        void onPostExecute(RequiredModel model);
    }
}


