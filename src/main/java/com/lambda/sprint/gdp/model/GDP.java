package com.lambda.sprint.gdp.model;

public class GDP
{
    private String country;
    private long gdp;
    
    public GDP(String country, String gdp)
    {
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
}
