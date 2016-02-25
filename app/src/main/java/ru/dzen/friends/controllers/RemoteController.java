package ru.dzen.friends.controllers;

import android.os.AsyncTask;
import android.util.Log;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Random;

import ru.dzen.friends.models.GameModel;

public class RemoteController {

    private static RemoteController instance;

    /**
     * При первом получении instance пытается подключиться к серверу
     *
     * @return Instance - при удачном подключении
     * null - при неудачном подключении
     */
    public static RemoteController getInstance() {
        if (instance == null)
            instance = new RemoteController();
        return instance;
    }

    private RemoteController() {
    }

    /**
     * Залогиниться!
     *
     * @return true - если залогинился|
     * false - если не залогинился (ну это был шаблончик такой, возможно не надо будет ничего возвращать)
     */
    public boolean login(String vkId, String gPlusId) {
        //TODO Получить от сервера экземпляр класса RemoteUserModel и засторить в preferences id от сервера
        new HttpRequestTask().execute(
                "/login?" +
                        ((vkId != null) ? "vk_id=" + vkId : "") +
                        ((gPlusId != null) ? "email=" + gPlusId : "")
        );
        return true;
    }

    /**
     * Запросить у сервера список игр в моей локации
     *
     * @return Список игр
     * @deprecated ПОКА ЧТО С ТЕСТОВОЙ ЗАГЛУШКОЙ
     */
    public ArrayList<GameModel> getGamesAroundMe() {
        // TODO Вытянуть список игр и отправить его бродкастом в листвью
        ArrayList<GameModel> testArrayList = new ArrayList<>();
        for (int i = 0; i < 1.5 * ((new Random()).nextGaussian()) + 10; i++) {
            testArrayList.add(new GameModel("GameModel " + i, "Some place", (new Random()).nextBoolean()));
        }
        return testArrayList;
    }

    /**
     * Таск коннекта
     * Возвращает Object, который, чисто теоретически должен нормально кастоваться к нужному классу модели.
     * Если не получится, то придется определять какой запрос исполняется:(
     */
    private class HttpRequestTask extends AsyncTask<String, Void, Object> {
        @Override
        protected Object doInBackground(String... params) {
            try {
                final String url = "https://zen-server.herokuapp.com/" + params[0];
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                //Следующая строка как раз создает экземпляр класса с пропарсенными в него данными из json'a
                Object o = restTemplate.getForObject(url, Object.class);
                return o;
            } catch (Exception e) {
                Log.e("RemoteController", e.getMessage(), e);
            }
            return null;
        }
    }
}
