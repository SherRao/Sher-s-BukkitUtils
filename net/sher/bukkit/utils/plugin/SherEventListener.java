package net.sher.bukkit.utils.plugin;

import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import net.sher.bukkit.utils.config.SherConfiguration;
import net.sher.bukkit.utils.logging.ColoredLogger;

public class SherEventListener implements Listener {

    public static final EventPriority 
        HIGHEST = EventPriority.HIGHEST,
        HIGH = EventPriority.HIGH,
        NORMAL = EventPriority.NORMAL, 
        LOW = EventPriority.LOW, 
        LOWER = EventPriority.LOWEST,
        LOWEST = EventPriority.MONITOR;
    
    protected SherPlugin pl;
    protected ColoredLogger log;
    protected SherConfiguration config;
    
    public SherEventListener( SherPlugin pl ) {
        this( pl, pl.getColoredLogger(), pl.getCustomConfig() );
        
    }
    
    public SherEventListener( SherPlugin pl, ColoredLogger log ) {
        this( pl, log, pl.getCustomConfig() );
        
    }
    
    public SherEventListener( SherPlugin pl, SherConfiguration config ) {
        this( pl, pl.getColoredLogger(), config );
        
    }
    
    public SherEventListener( SherPlugin pl, ColoredLogger log, SherConfiguration config ) {
        this.pl = pl;
        this.log = log;
        this.config = config;
        
    }
    
    protected SherEventListener() {
        
    }
    
}