package org.apiproject.boot.demo;

import java.util.ArrayList;
import java.util.List;
//import java.text.Format;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DataController {

    private String template = "Hello %s" ;
    private AtomicLong counter  = new AtomicLong();
    private List<Data>  datas = new ArrayList<>();

    public DataController(){
        datas.add(new Data(1,"Parichaya"));
        datas.add(new Data(2,"Chanakarn"));
        datas.add(new Data(3,"Bam"));
    }


    @GetMapping("/data")
    public Data data(@RequestParam(value = "name",defaultValue = "World") String name){
        return new Data(counter.incrementAndGet(), String.format(template,name));
    }

    @GetMapping("/universities")
    public List<Data> getData(){
        return datas;
    }
    
    
}
