package com.lambda.sprint.gdp.model;

import java.util.concurrent.atomic.AtomicLong;

public class GDP
{
    private static AtomicLong counter = new AtomicLong();
    private long id;
    private String country;
    private long gdp;
    
    public GDP(String country, String gdp)
    {
        id = counter.incrementAndGet();
        this.country = country;
        this.gdp = Long.parseLong(gdp);
    }
    
    public String getCountry()
    {
        return country;
    }
    
    public void setCountry(String country)
    {
        this.country = country;
    }
    
    public long getGdp()
    {
        return gdp;
    }
    
    public void setGdp(long gdp)
    {
        this.gdp = gdp;
    }
    
    public long getId()
    {
        return id;
    }
}
