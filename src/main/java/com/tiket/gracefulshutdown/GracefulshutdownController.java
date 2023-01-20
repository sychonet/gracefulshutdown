package com.tiket.gracefulshutdown;

import org.springframework.web.bind.annotation.GetMapping;  
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;

@RestController  
public class GracefulshutdownController implements HealthIndicator{
    private boolean statusDown = false;
    
    @Value("${readinessprobe.duration}")
    private int pausePeriod;

    @GetMapping("/")  
    public String home()   
    {  
        try
        {
            Thread.sleep(10000); 
        }
        catch(InterruptedException ex)
        {
            Thread.currentThread().interrupt();
        }
        return "Graceful Shutdown Demo";    
    }  
    
    @GetMapping("/pause")  
    public String status()   
    {  
        this.statusDown = true;
        try
        {
            Thread.sleep(pausePeriod); 
        }
        catch(InterruptedException ex)
        {
            Thread.currentThread().interrupt();
        }
        return "Graceful Shutdown Starts";    
    }

    @Override
    public Health health() {
        if (this.statusDown) {
            return Health.down().withDetail("Termination begins", 15).build();
        }
        return Health.up().build();
    }
}
