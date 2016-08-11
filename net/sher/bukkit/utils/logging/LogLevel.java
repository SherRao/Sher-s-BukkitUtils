package net.sher.bukkit.utils.logging;

import java.util.logging.Level;

public enum LogLevel {
    
    FINEST( "finest", 0 ), FINER( "finer", 1 ), FINE( "fine", 2 ), CONFIG( "config", 3 ), 
    INFO( "info", 4 ), SPECIAL( "special", 4 ), WARNING( "warning", 5 ), SEVERE( "severe", 6 );
    
    private String name;
    private int strength;
    
    LogLevel( String name, int strength ) {
        this.name = name;
        this.strength = strength;
        
    }
    
    @Override
    public String toString() {
        return name;
        
    }
    
    public Level toLogLevel() {
        switch( strength ) {
            case 0:
                return Level.FINEST;
          
            case 1:
                return Level.FINER;
                 
            case 2:
                return Level.FINE;
                
            case 3:
                return Level.CONFIG;
                
            case 4: 
                return Level.INFO;
                
            case 5:
                return Level.WARNING;
                
            case 6:
                return Level.SEVERE;
                        
        }
        
        return Level.INFO;
        
    }
    
    public String getPrefix() {
        return "[" + name.toUpperCase() + "]";
        
    }
    
    public int getStrength() {
        return strength;
        
    }
    
}