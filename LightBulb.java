/**
 *
 * @author Yasmin Sakalla
 */

public class LightBulb {
    
    private int         UID;
    private RGBColor    color;
    private boolean     switchedOn;
    private final int   MIN_VAL = 0;
    private final int   MAX_VAL = 255;
    
    public LightBulb(int red, int green, int blue)
    {
        color = new RGBColor(red, green, blue) ;
        switchedOn = false;
        
        if((red < MIN_VAL) || (red > MAX_VAL) || (green < MIN_VAL) || (green > MAX_VAL) || (blue < MIN_VAL) || (blue > MAX_VAL))
        { 
            red = MIN_VAL;
            green = MIN_VAL;
            blue = MIN_VAL;
        }
    }
    
    public int getUID() {
        return UID;
    }
    
    public LightBulb(RGBColor color)
    {
        color = new RGBColor(color);
        switchedOn = false;
    }
    
    public LightBulb(LightBulb other)
    {
        color = new RGBColor(other.color);
        switchedOn = false;
    }
    
    public String toString()
    {
        String onOrOff;
        if(isSwitchedOn() == true)
            onOrOff = "On";
        else
            onOrOff = "Off";
        
        return ("(" + color.getRed() + "," + color.getGreen() + "," + color.getBlue() + ")" + ", " + onOrOff);
    }
    
    public RGBColor getColor()
    {
        return new RGBColor(color);
    }
    
    public void setColor(RGBColor color)
    {
        color = new RGBColor(color);
    }
    
    public boolean isSwitchedOn()
    {
        return switchedOn;
    }

    public void switchLight()
    {
        switchedOn = !switchedOn;
    }
  
    
}
