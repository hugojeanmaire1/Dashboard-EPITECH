package App.Model;

import java.util.ArrayList;

public class Server {

    private long current_time;
    private ArrayList<Services> services;

    public Server() {
        super();
        this.current_time = System.currentTimeMillis();
        this.services = new ArrayList<Services>();
    }

    public long getCurrent_time() {
        return current_time;
    }

    public void setCurrent_time(long current_time) {
        this.current_time = current_time;
    }

    public ArrayList<Services> getServices() {
        return services;
    }

    public void setServices(ArrayList<Services> services) {
        this.services = services;
    }

    @Override
    public String toString() {
        return "Server{" +
                "currentTime=" + current_time +
                ", services=" + services +
                '}';
    }
}
