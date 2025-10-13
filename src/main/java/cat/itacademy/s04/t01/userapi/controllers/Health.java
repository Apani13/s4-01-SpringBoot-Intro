package cat.itacademy.s04.t01.userapi.controllers;

public class Health {

    private String status;

    public Health(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
