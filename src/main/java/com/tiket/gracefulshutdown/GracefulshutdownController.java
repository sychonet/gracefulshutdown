package com.tiket.gracefulshutdown;

import org.springframework.web.bind.annotation.GetMapping;  
import org.springframework.web.bind.annotation.RestController; 
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;

@RestController  
public class GracefulshutdownController implements HealthIndicator{
    private boolean statusDown = false;
    
    @GetMapping("/")  
    public String home()   
    {  
        try
        {
            Thread.sleep(1000); 
        }
        catch(InterruptedException ex)
        {
            Thread.currentThread().interrupt();
        }
        return "Graceful Shutdown Demo";    
    }  
    
    @GetMapping("/shutdown")  
    public String status()   
    {  
        this.statusDown = true;
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
