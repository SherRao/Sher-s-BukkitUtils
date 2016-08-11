package net.sher.bukkit.utils.command;

import org.bukkit.Utility;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.TabExecutor;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import net.sher.bukkit.utils.config.SherConfiguration;
import net.sher.bukkit.utils.logging.ColoredLogger;
import net.sher.bukkit.utils.plugin.SherPlugin;

public abstract class SherCommand implements TabExecutor {
    
    protected final List<String> EMPTY_LIST = Collections.emptyList();
    
    protected PluginCommand command;
    protected SherPlugin pl;
    protected ColoredLogger log;
    protected SherConfiguration config;
        
    public SherCommand( String command, SherPlugin pl ) {
        this( command, pl, pl.getColoredLogger(), pl.getCustomConfig() );
        
    }
    
    public SherCommand( String command, SherPlugin pl, ColoredLogger log ) {
        this( command, pl, log, pl.getCustomConfig() );
        
    }
    
    public SherCommand( String command, SherPlugin pl, SherConfiguration config ) {
        this( command, pl, pl.getColoredLogger(), config );
        
    }
    
    public SherCommand( String command, SherPlugin pl, ColoredLogger log, SherConfiguration config ) {
        this.pl = pl;
        this.log = log;
        this.config = config;

        log.fine( super.getClass().getSimpleName() + " registered successfully!" );
        
        if( command == null )    
            return;
        this.command = pl.getCommand( command );
        
    }
    
    @Override
    public boolean onCommand( CommandSender sender, Command command, String alias, String[] args ) {
        onExecute( new CommandBundle( sender, command, alias, args ) );

        return true;
        
    }
    
    @Override
    public List<String> onTabComplete( CommandSender sender, Command command, String alias, String[] args ) {
        if( this.command.getName().equalsIgnoreCase( command.getName() ) || this.command == null )
            return onTab( new CommandBundle( sender, command, alias, args) );
        
        return EMPTY_LIST;
            
    }
    
    @Utility
    protected <T> List<T> asList( @SuppressWarnings( "unchecked" ) T... a ) {
        return Arrays.asList( a );
        
    }
    
    protected abstract void onExecute( CommandBundle bundle );
    
    protected List<String> onTab( CommandBundle bundle ) {
        return EMPTY_LIST;
        
    }
    
    public Command toCommand() {
        return command;
        
    }
    
}