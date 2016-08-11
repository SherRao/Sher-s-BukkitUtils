package net.sher.bukkit.utils.plugin;

import org.bukkit.scheduler.BukkitRunnable;

import net.sher.bukkit.utils.config.SherConfiguration;
import net.sher.bukkit.utils.logging.ColoredLogger;

public abstract class SherTask extends BukkitRunnable {
    
    protected SherPlugin pl;
    protected ColoredLogger log;
    protected SherConfiguration config;
    
    protected int loops;
    
    public SherTask( SherPlugin pl ) {
        this( pl, pl.getColoredLogger(), pl.getCustomConfig() );
        
        
    }
    
    public SherTask( SherPlugin pl, ColoredLogger log ) {
        this( pl, log, pl.getCustomConfig() );
        
    }
    
    public SherTask( SherPlugin pl, SherConfiguration config ) {
        this( pl, pl.getColoredLogger(), config );
        
    }
    
    public SherTask( SherPlugin pl, ColoredLogger log, SherConfiguration config ) {
        super();
        
        this.pl = pl;
        this.log = log;
        this.config = config;
    
    }
    
    @Override
    public final void run() {  
        fire();
        loops++;
        
    }
    
    public abstract void fire();
    
    public final int getCurrentLoop() { return loops; }
    
    public final boolean isFirstLoop() { return loops == 0; }
    
}