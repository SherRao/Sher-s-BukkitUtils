package net.sher.bukkit.utils.command;

import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandBundle {
    
    protected CommandSender sender;
    protected Command command;
    protected String alias;
    protected String[] args;
    
    public CommandBundle( CommandSender sender, Command command, String alias, String[] args ) {
        this.sender = sender;
        this.command = command;
        this.alias = alias;
        this.args = args;
    
    }
    
    public void sendMessage( String msg ) { 
        sender.sendMessage( msg ); 
        
    }
    
    public void playSound( Sound sound ) { 
        playSound( sound, 1F ); 
        
    }
   
    public void playSound( Sound sound, float volume ) { 
        playSound( sound, volume, 1F ); 
        
    }
    
    public void playSound( Sound sound, float volume, float pitch ) {
        player().playSound( player().getLocation(), sound, volume, pitch );
        
    }
    
    public boolean isSenderPlayer() {
        return sender instanceof Player;
        
    }
    
    public boolean hasPermission( String perm ) {
        return sender.hasPermission( perm );
        
    }
    
    public boolean isCommand( String command ) {
        return this.command.getName().equalsIgnoreCase( command );
        
    }
    
    public boolean isAlias( String alias ) {
        return this.alias.equalsIgnoreCase( alias );
        
    }
    
    public boolean hasArgs() {
        return args.length > 0;
        
    }
    
    public boolean isArgsLength( int length ) {
        return args.length == length;
        
    }
    
    public boolean isArgsMoreThan( int moreThan ) {
        return args.length > moreThan;
        
    }
    
    public boolean isArgsLessThan( int lessThan ) {
        return args.length < lessThan;
        
    }
    
    public String argAt( int at ) {
        if( at > args.length )
            throw new IllegalArgumentException( "The index of " + at + " is larger than the argument array!" );
        
        if( at < 0 )
            throw new IllegalArgumentException( "The index cannot be negated!" );
        
        return args[at];
        
    }
    
    public CommandSender sender() { return sender; }
    
    public Player player() { return (Player) sender; }
    
    public Command command() { return command; }
    
    public String alias() { return alias; }
    
    public String[] args() { return args; }

}