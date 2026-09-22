package cl.duoc.vidasalud.bff.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/api/appointments")
public class AppointmentBffController {

    private final RestTemplate restTemplate;

    @Value("${appointments.service.url}")
    private String appointmentsServiceUrl;

    public AppointmentBffController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getById(@PathVariable Long id) {
        Object body = restTemplate.getForObject(
            appointmentsServiceUrl + "/api/appointments/{id}", Object.class, id);
        return ResponseEntity.ok(body);
    }

    @GetMapping
    public ResponseEntity<Object> list(@RequestParam(required = false) String status) {
        String url = appointmentsServiceUrl + "/api/appointments"
            + (status != null ? "?status=" + status : "");
        Object body = restTemplate.getForObject(url, Object.class);
        return ResponseEntity.ok(body);
    }

    @PostMapping
    public ResponseEntity<Object> create(@RequestBody Object request) {
        Object body = restTemplate.postForObject(
            appointmentsServiceUrl + "/api/appointments", request, Object.class);
        return ResponseEntity.ok(body);
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<Object> changeStatus(@PathVariable Long id, @RequestBody Object request) {
        HttpEntity<Object> entity = new HttpEntity<>(request);
        ResponseEntity<Object> response = restTemplate.exchange(
            appointmentsServiceUrl + "/api/appointments/{id}/status",
            HttpMethod.PUT,
            entity,
            Object.class,
            id
        );
        return ResponseEntity.ok(response.getBody());
    }
}