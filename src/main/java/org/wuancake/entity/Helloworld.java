package org.wuancake.entity;

/**
 * Created by Administrator on 2019/1/24.
 */
public class Helloworld {
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Helloworld(String message) {
        this.message = message;
    }

    public Helloworld() {
    }

    @Override
    public String toString() {
        return "Helloworld{" +
                "message='" + message + '\'' +
                '}';
    }
}
