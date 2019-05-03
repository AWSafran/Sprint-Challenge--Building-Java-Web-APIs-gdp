package com.lambda.sprint.gdp.model;

public class TotalGdp
{
    private String name="Total";
    private long total;
    
    public TotalGdp(long total)
    {
        this.total = total;
    }
    
    public String getName()
    {
        return name;
    }
    
    public void setName(String name)
    {
        this.name = name;
    }
    
    public long getTotal()
    {
        return total;
    }
    
    public void setTotal(long total)
    {
        this.total = total;
    }
}
