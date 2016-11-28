package me.ryanhamshire.GPFlags;

class Flag
{
    FlagDefinition flagDefinition;
    String parameters;
    private String [] parametersArray;
    private boolean set = true;
    
    Flag(FlagDefinition definition, String parameters)
    {
        this.flagDefinition = definition;
        this.parameters = parameters;
        this.parameters = this.parameters.replace('$', (char)0x00A7);
        this.parameters = this.parameters.replace('&', (char)0x00A7);
    }
    
    protected String [] getParametersArray()
    {
        if(this.parametersArray != null) return this.parametersArray;
        this.parametersArray = this.parameters.split(" ");
        return this.parametersArray;
    }
    
    boolean getSet()
    {
        return this.set;
    }
    
    void setSet(boolean value)
    {
        this.set = value;
    }
}
