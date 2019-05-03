package com.lambda.sprint.gdp.controller;

import com.lambda.sprint.gdp.GdpApplication;
import com.lambda.sprint.gdp.model.GDP;
import com.lambda.sprint.gdp.model.GDPList;
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
    //localhost:2019/names
    @GetMapping(value="/names", produces = {"application/json"})
    public ResponseEntity<?> getAllNames()
    {
        
        ArrayList<GDP> allNames = GdpApplication.ourList.gdpList;
        allNames.sort((g1, g2) -> g1.getCountry().compareToIgnoreCase(g2.getCountry()));
        System.out.println("accessed endpoint");
        
        return new ResponseEntity<>(allNames, HttpStatus.OK);
    }
    
    //localhost:2019/economy
    @GetMapping(value ="/economy", produces = {"application/json"})
    public ResponseEntity<?> getEconomy()
    {
        ArrayList<GDP> allEconomies = GdpApplication.ourList.gdpList;
        allEconomies.sort((g1, g2) -> (int)(g2.getGdp() - g1.getGdp()));
        return new ResponseEntity<>(allEconomies, HttpStatus.OK);
    }
    
    //localhost:2019/gdp/{country}
    @GetMapping(value="/gdp/{country}", produces = {"application/json"})
    public ResponseEntity<?> getCountry(@PathVariable String country)
    {
        GDP selectedCountry = GdpApplication.ourList.findCountry(c -> c.getCountry().equalsIgnoreCase(country));
        
        if(selectedCountry == null)
        {
            // delete return and throw error here
            return new ResponseEntity<>("Oopsie woopsie!", HttpStatus.OK);
        } else
        {
            return new ResponseEntity<>(selectedCountry, HttpStatus.OK);
        }
        
    }
    
    @GetMapping(value="/country/{id}", produces = {"application/json"})
    public ResponseEntity<?> getCountryById(@PathVariable long id)
    {
        GDP selectedCountry = GdpApplication.ourList.findCountry(c -> c.getId() == id);
        if(selectedCountry == null)
        {
            // delete return and throw error here
            return new ResponseEntity<>("Oopsie woopsie!", HttpStatus.OK);
        } else
        {
            return new ResponseEntity<>(selectedCountry, HttpStatus.OK);
        }
    }
    
    //localhost:2019/country/stats/median
    @GetMapping(value="/country/stats/median", produces={"application/json"})
    public ResponseEntity<?> getMedianGdp()
    {
        ArrayList<GDP> orderedCountries = GdpApplication.ourList.gdpList;
        orderedCountries.sort((c1, c2) -> (int)(c2.getGdp() - c1.getGdp()));
        
        return new ResponseEntity<>(orderedCountries.get(orderedCountries.size() /2), HttpStatus.OK);
    }
    
    //localhost:2019/economy/greates/{gdp}
    @GetMapping(value="/economy/greatest/{gdp}")
    public ModelAndView displayGdpTable(@PathVariable long gdp)
    {
        ArrayList<GDP> highGdp = GdpApplication.ourList.findCountries(c -> c.getGdp() >= gdp);
        
        if(highGdp != null)
        {
            //sorting array if it is not empty
            highGdp.sort((g1, g2) -> (int)(g2.getGdp() - g1.getGdp()));
        }
        
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
}
