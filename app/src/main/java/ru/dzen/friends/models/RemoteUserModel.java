package ru.dzen.friends.models;

/**
 * Модель данных о пользователе, которая в точно таком виде возвращается с сервера
 */
public class RemoteUserModel {
    private String id;
    private Long vkId;
    private String email;
    private String message;

    public String getId() {
        return id;
    }

    public Long getVkId() {
        return vkId;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String toString() {
        return "UserData{" +
                "id='" + id + '\'' +
                ", vkId=" + vkId +
                ", email='" + email + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
