package ssd;

import java.util.ArrayList;
import java.util.List;

public class TimeMonitor {
    private List<Monitor> monitorList=new ArrayList();
    private TimeMonitor(){

    }


    public static TimeMonitor start(){
        TimeMonitor timeMonitor=new TimeMonitor();

        return timeMonitor;
    }

    static class Monitor{
        private String name;

        private Long begin;


        private Long end;

    }
}
