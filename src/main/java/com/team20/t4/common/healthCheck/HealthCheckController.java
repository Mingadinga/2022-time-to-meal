package com.team20.t4.common.healthCheck;

import com.team20.t4.common.responseFormat.OnlyResponseString;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class HealthCheckController {
    @GetMapping("/ping")
    public OnlyResponseString healthCheck(){
        return new OnlyResponseString("pong");
    }
}
