package com.lambda.sprint.gdp.controller;

import com.lambda.sprint.gdp.GdpApplication;
import com.lambda.sprint.gdp.model.GDPList;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GDPController
{
    //localhost:2019/names
    @GetMapping(value="/names", produces = {"application/json"})
    public ResponseEntity<?> getAllNames()
    {
        GDPList allNames = new GDPList();
        
//        allNames = GdpApplication.ourList.gdpList.sort((g1, g2) -> g1.getCountry().compareToIgnoreCase(g2.getCountry()));
    
        System.out.println("accessed endpoint");
        
        return new ResponseEntity<>("hello", HttpStatus.OK);
    }
}
