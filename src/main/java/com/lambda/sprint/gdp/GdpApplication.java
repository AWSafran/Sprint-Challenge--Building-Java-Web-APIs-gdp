package com.lambda.sprint.gdp;

import com.lambda.sprint.gdp.model.GDPList;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableWebMvc
@SpringBootApplication
public class GdpApplication
{
    public static GDPList ourList = new GDPList();

    public static void main(String[] args)
    {
        
        ApplicationContext ctx = SpringApplication.run(GdpApplication.class, args);

    }

}
