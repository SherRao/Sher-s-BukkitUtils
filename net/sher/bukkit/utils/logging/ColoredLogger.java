package net.sher.bukkit.utils.logging;

import org.apache.commons.lang.Validate;
import org.bukkit.ChatColor;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public class ColoredLogger {
        
    protected JavaPlugin pl;    
    protected ConsoleCommandSender console;
    protected Logger log;
    protected String prefix;
    
    public ColoredLogger( JavaPlugin pl ) {
        this.pl = pl;
        this.console = pl.getServer().getConsoleSender();
        this.log = pl.getLogger();
        this.prefix = new StringBuilder()
                .append( "[" )
                .append( pl.getName() )
                .append( "] " )
                .toString();
        
    }
    
    public void log( LogLevel level, String msg ) {
        doLog( level, msg );
        
    }
    
    public void special( String msg ) {
        doLog( LogLevel.SPECIAL, msg );
        
    }
    
    public void finest( String msg ) {
        doLog( LogLevel.FINEST, msg );
        
    }
    
    public void finer( String msg ) {
        doLog( LogLevel.FINER, msg );
        
    }
    
    public void fine( String msg ) {
        doLog( LogLevel.FINE, msg );
        
    }

    public void config( String msg ) {
        doLog( LogLevel.CONFIG, msg );
        
    }

    public void info( String msg ) {
        if( msg.equals( ChatColor.stripColor( msg ) ))
            msg = ChatColor.GOLD + msg;
        
        doLog( LogLevel.INFO, msg );
        
    }

    public void warning( String msg, Exception... exceptions ) {
        if( msg.equals( ChatColor.stripColor( msg ) ))
            msg = ChatColor.RED + msg;
        
        doLog( LogLevel.WARNING, msg, exceptions );

    }

    public void severe( String msg, Exception... exceptions ) {
        if( msg.equals( ChatColor.stripColor( msg ) ))
            msg = ChatColor.DARK_RED + msg;
        
        doLog( LogLevel.SEVERE, msg, exceptions );

    } 
    
    private void doLog( LogLevel level, String msg, Exception... exceptions ) {
        Validate.notNull( level, "The logger has to have a level!" );
        Validate.notNull( msg, "The message sent to the logger can't be empty!" );
        msg += ChatColor.RESET;
        
        if( level == null || msg == null ) 
            return;
        
        else if ( level == LogLevel.SPECIAL )
            console.sendMessage( msg );
            
        else if( level == LogLevel.INFO )
            console.sendMessage( prefix + msg );
            
        else 
            log.log( level.toLogLevel(), msg, exceptions.length == 0 ? null : exceptions[0] );
                
    }
    
}