package com.lambda.sprint.gdp.controller;

import com.google.gson.JsonObject;
import com.lambda.sprint.gdp.GdpApplication;
import com.lambda.sprint.gdp.exception.ResourceNotFoundException;
import com.lambda.sprint.gdp.model.GDP;
import com.lambda.sprint.gdp.model.GDPList;
import com.lambda.sprint.gdp.model.TotalGdp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;

@RestController
public class GDPController
{
    private static final Logger logger = LoggerFactory.getLogger(GDPController.class);
    
    //localhost:2019/names
    @GetMapping(value="/names", produces = {"application/json"})
    public ResponseEntity<?> getAllNames()
    {
        logger.info("/names has been accessed");
        ArrayList<GDP> allNames = GdpApplication.ourList.gdpList;
        allNames.sort((g1, g2) -> g1.getCountry().compareToIgnoreCase(g2.getCountry()));
        
        return new ResponseEntity<>(allNames, HttpStatus.OK);
    }
    
    //localhost:2019/economy
    @GetMapping(value ="/economy", produces = {"application/json"})
    public ResponseEntity<?> getEconomy()
    {
        logger.info("/economy has been accessed");
        ArrayList<GDP> allEconomies = GdpApplication.ourList.gdpList;
        allEconomies.sort((g1, g2) -> (int)(g2.getGdp() - g1.getGdp()));
        return new ResponseEntity<>(allEconomies, HttpStatus.OK);
    }
    
    //localhost:2019/gdp/{country}
    @GetMapping(value="/gdp/{country}", produces = {"application/json"})
    public ResponseEntity<?> getCountry(@PathVariable String country)
    {
        logger.info("/gdp/{country} has been accessed with parameter: " + country);
        GDP selectedCountry = GdpApplication.ourList.findCountry(c -> c.getCountry().equalsIgnoreCase(country));
        
        if(selectedCountry == null)
        {
            throw new ResourceNotFoundException("Country: " + country + " not found");
        } else
        {
            return new ResponseEntity<>(selectedCountry, HttpStatus.OK);
        }
        
    }
    
    @GetMapping(value="/country/{id}", produces = {"application/json"})
    public ResponseEntity<?> getCountryById(@PathVariable long id)
    {
        logger.info("/country/{id} has been accessed with parameter: " + id);
        GDP selectedCountry = GdpApplication.ourList.findCountry(c -> c.getId() == id);
        if(selectedCountry == null)
        {
            throw new ResourceNotFoundException("Country with ID: " + id + " not found");
        } else
        {
            return new ResponseEntity<>(selectedCountry, HttpStatus.OK);
        }
    }
    
    //localhost:2019/country/stats/median
    @GetMapping(value="/country/stats/median", produces={"application/json"})
    public ResponseEntity<?> getMedianGdp()
    {
        logger.info("/country/stats/median has been accessed");
        ArrayList<GDP> orderedCountries = GdpApplication.ourList.gdpList;
        orderedCountries.sort((c1, c2) -> (int)(c2.getGdp() - c1.getGdp()));
        
        return new ResponseEntity<>(orderedCountries.get(orderedCountries.size() /2), HttpStatus.OK);
    }
    
    //localhost:2019/economy/greatest/{gdp}
    @GetMapping(value="/economy/greatest/{gdp}")
    public ModelAndView displayGdpTable(@PathVariable long gdp)
    {
        ArrayList<GDP> highGdp = GdpApplication.ourList.findCountries(c -> c.getGdp() >= gdp);
        
        if(highGdp.size() == 0)
        {
            throw new ResourceNotFoundException("No countries with GDP above " + gdp + " found");
        }
    
        highGdp.sort((g1, g2) -> (int)(g2.getGdp() - g1.getGdp()));
        
        ModelAndView mav = new ModelAndView();
        mav.setViewName("tables");
        mav.addObject("gdpList", highGdp);
        return mav;
    }
    
    @GetMapping(value="/economy/table")
    public ModelAndView displayFullTable()
    {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("tables");
        mav.addObject("gdpList", GdpApplication.ourList.gdpList);
        return mav;
    }
    
    //STRETCH GOALS HERE
    
    //localhost:2019/total
    @GetMapping(value="/total", produces={"application/json"})
    public ResponseEntity<?> getTotal()
    {
        logger.info("/total has been accessed");
        long total = 0;
        
        for(GDP g : GdpApplication.ourList.gdpList)
        {
            total += g.getGdp();
        }
    
        TotalGdp totalGdp = new TotalGdp(total);
        
        return new ResponseEntity<>(totalGdp, HttpStatus.OK);
    }
    
    //localhost:2019/gdp/list/{startgdp}/{endgdp}
    @GetMapping(value="/gdp/list/{lowGdp}/{highGdp}")
    public ModelAndView gdpRange(@PathVariable long lowGdp, @PathVariable long highGdp)
    {
        
        logger.info("/gdp/list/{startgdp}/{endgdp} has been accesssed with parameters: " + lowGdp + ", " + highGdp);
        ArrayList<GDP> inRange = GdpApplication.ourList.findCountries(c -> (c.getGdp() >= lowGdp && c.getGdp() <= highGdp));
        
        if (inRange.size() == 0)
        {
            throw new ResourceNotFoundException("There are no countries within range: " + lowGdp + " - " + highGdp);
        }
        
        inRange.sort((c1, c2) -> (int)(c1.getGdp() - c2.getGdp()));
        ModelAndView mav = new ModelAndView();
        mav.setViewName("tables");
        mav.addObject("gdpList", inRange);
        return mav;
        
    }
}
