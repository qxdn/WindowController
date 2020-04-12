package com.sunbest.model;

/**
 * MQTT部分设置
 */
public class MqttSetting {

    /**
     * mqtt用户名随意设置
     */
    private String username;
    /**
     * mqtt密码随意设置
     */
    private String password;
    /**
     * 用户id唯一，使用设备id
     */
    private String clientId;


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }
}
