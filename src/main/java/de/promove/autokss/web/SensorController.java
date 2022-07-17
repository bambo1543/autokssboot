package de.promove.autokss.web;

import de.promove.autokss.model.Distance;
import de.promove.autokss.service.SensorService;
import jakarta.faces.event.ActionEvent;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;

@RestController
@RequestMapping("sensor")
@ResponseStatus(HttpStatus.OK)
public class SensorController {

    @Autowired
    private SensorService sensorService;

    @Getter
    private String distance;

    @GetMapping(value = "/connect")
    public String connect(HttpServletRequest request) {
        String remoteAddr = request.getRemoteAddr();
//        new SimpleDateFormat("dd.MM.yyyy").parse("01.01.1970")
        StringBuilder sb = new StringBuilder();
        String mac = request.getParameterMap().get("mac")[0];
        sb.append("mac: ").append(mac);
        String ip = request.getParameterMap().get("ip")[0];
        sb.append("ip: ").append(ip);
        sensorService.getMacToIpMap().put(mac, ip);
        long now = new Date().getTime();
        return String.valueOf((now / 1000) + (TimeZone.getDefault().getOffset(now) / 1000)) ;
    }

    @GetMapping(value = "/distance")
    public String distance(@RequestParam String mac, @RequestParam String ip,
                           @RequestParam String distance) {
        Distance d = new Distance(new Date(), Float.valueOf(distance));
        if(!sensorService.getDistances().containsKey(mac)) {
            sensorService.getDistances().put(mac, new ArrayList<>());
        }
        sensorService.getDistances().get(mac).add(d);

        return HttpStatus.OK.toString();
    }

    public void loadDistance(ActionEvent event) {
        HttpClient httpClient = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_1_1)
                .followRedirects(HttpClient.Redirect.NORMAL)
                .connectTimeout(Duration.ofSeconds(5))
                .build();
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create("http://192.168.1.116/distance"))
                .setHeader("User-Agent", "Java 11 HttpClient Bot") // add request header
                .build();
        try {
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            distance = response.body();
            System.out.println(distance);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
