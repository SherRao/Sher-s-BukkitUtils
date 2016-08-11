package net.sher.bukkit.utils.plugin;

import static com.google.common.base.Strings.repeat;
import static java.lang.String.format;
import static org.bukkit.ChatColor.AQUA;
import static org.bukkit.ChatColor.DARK_RED;
import static org.bukkit.ChatColor.GOLD;
import static org.bukkit.ChatColor.GREEN;

import com.google.common.collect.Lists;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Utility;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.permissions.Permission;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import net.sher.bukkit.utils.command.SherCommand;
import net.sher.bukkit.utils.config.SherConfiguration;
import net.sher.bukkit.utils.logging.ColoredLogger;
import net.sher.utils.MapUtils;

public class SherPlugin extends JavaPlugin {
    
    protected ColoredLogger log;
    protected SherConfiguration config;
    
    protected HashMap<String, SherCommand> commands;
    protected HashMap<String, SherEventListener> eventListeners;

    protected boolean enabled, needConfig;
    protected String name, version;
    private long start;
    
    public SherPlugin() { 
        
    }
    
    @Override
    public void onLoad() {
        this.start = System.currentTimeMillis();
        
    }
    
    @Override
    public void onEnable() { 
        this.log = new ColoredLogger( this );
        this.needConfig = true;
        if( needConfig ) {
            if ( !new File( super.getDataFolder(), "config.yml" ).exists() ) 
            log.info( GOLD + "Loading configuration file for first time use!" );
            super.saveDefaultConfig();
            super.reloadConfig();
            this.config = new SherConfiguration( this );
        
        }
        
        this.commands = new HashMap< String, SherCommand >();
        this.eventListeners = new HashMap< String, SherEventListener >();        

        this.enabled = config.getBoolean( "enabled" );
        this.name = super.getName();
        this.version = super.getDescription().getVersion();
        
        logTitle();
        if( !enabled ) 
            super.getServer().getPluginManager().disablePlugin( this );

    }
    
    @Override
    public void onDisable() {
        log.info( GOLD + "Writing memory-configuration instance to configuration file (".concat( name ).concat( "/config.yml)..." ) );        
        log.info( GREEN + "Configuration file updated with memory instance!" );
        
    }
    
    @Override
    public final boolean onCommand( CommandSender sender, Command command, String alias, String[] args ) {
        return true;
    
    }
    
    @Override
    public final List<String> onTabComplete( CommandSender sender, Command command, String alias, String[] args) {
        return Lists.newArrayList();
        
    }
    
    @Utility
    @Deprecated
    public final void reload() {
        onDisable();
        onLoad();
        onEnable();
        
    }
    
    @Utility
    @Deprecated
    public final void injectPermission( String node ) {
        this.injectPermission( node, null );
        
    }
    
    @Utility
    @Deprecated
    public final void injectPermission( String node, String description ) {
        this.injectPermission( node, description, DefaultPermissionAccess.NOBODY );
        
    }
    
    @Utility
    @Deprecated
    public final void injectPermission( String node, String description, DefaultPermissionAccess access ) {
        this.injectPermission( node, description, access, Lists.newArrayList() );
        
    }
    
    @Utility
    @Deprecated
    public final void injectPermission( String node, String description, DefaultPermissionAccess access, 
            String... children ) {
        this.injectPermission( node, description, access, Arrays.asList( children ) );
        
    }
    
    @Utility
    @Deprecated
    public final void injectPermission( String node, String description, DefaultPermissionAccess access, 
            List<String> children  ) {
        if( node == null )
            log.severe( "Tried to inject permission node, returned null... Printing Stacktrace...", new IllegalArgumentException( "Permission node can't be null for injecting permissions" ) );
        
        List<String> keys = new ArrayList<String>();
        for( String str : children )
            keys.add( node.replace( "*", "" ) + str );
        
        List<Boolean> values = new ArrayList<Boolean>();
        for( int i = 0; i < children.size(); i++ )
            values.add( true );
        
        Permission perm;
        if( keys.isEmpty() || values.isEmpty() || keys == null || values == null )
            perm = new Permission( node, description, access.toPermissionDefault() );

        else
            perm = new Permission( node, description, access.toPermissionDefault(), MapUtils.newMap( keys, values ) );
                
        Bukkit.getPluginManager().addPermission( perm );
        log.info( new StringBuilder( "Injected Permission: ")
                .append( node )
                .append( " (" )
                .append( description )
                .append( ")" )
                .toString() );
        
    }
    
    @Utility
    public final void createFolder( String foldername ) {
        File file = new File( "//" + foldername + "//" );
        if( !file.exists() ) {
            log.info( ChatColor.GOLD + "Trying to create folder (Ranks/" + foldername + ") for firstime use" );
            file.mkdir();
            log.info( ChatColor.GREEN + "Ranks/" + foldername + " created successfully!" );
            
        } 
        
        log.info( "Folder created: " + foldername );
        
    }
    
    @Utility
    public final File createFile( String filename ) {
        File file = new File( super.getDataFolder(), filename );
        if( !file.exists() ) {
            try {
                log.info( ChatColor.GOLD + "Trying to create file (Ranks/" + filename + ") for firstime use" );
                file.createNewFile();
                log.info( ChatColor.GREEN + "Ranks/" + filename + " created successfully!" );
                
            } catch ( IOException e ) {
                log.severe( "The file Ranks/" + filename + "could not be created, printing stacktrace...", e );
                
            }
            
        } 
            
        log.info( "File created: " + filename );
        return file;
        
    }
    
    @Utility
    public final void registerCommand( String name, SherCommand command ) {
        super.getCommand( name ).setExecutor( command );
        super.getCommand( name ).setTabCompleter( command );
        commands.put( name, command );
        log.info( "Registered command " + name + " with the executor and tab-completer" );
        
    }

    @Utility
    public final void registerEventListener( SherEventListener listener ) {
        registerEventListener( listener.getClass().getSimpleName(), listener );
        
    }
    
    @Utility
    public final void registerEventListener( String name, SherEventListener listener ) {
        super.getServer().getPluginManager().registerEvents( listener, this );        
        eventListeners.put( name, listener );
        log.info( "Registered " + name + " with the Bukkit Plugin Manager" );
        
    } 
    
    @Utility
    public final void registerRepeatingTask( SherTask task, long secStartDelay, long secLoopDelay ) {
        task.runTaskTimer( this, secStartDelay * 20, secLoopDelay * 20 );
        log.info( "Repeating task scheduled with TaskID: " + task.getTaskId() );

    }
    
    @Utility
    public final void registerAsyncRepeatingTask( SherTask task, long secStartDelay, long secLoopDelay ) {
        task.runTaskTimerAsynchronously( this, secStartDelay * 20, secLoopDelay * 20 );
        log.info( "Repeating asynchronous task scheduled with TaskID: " + task.getTaskId() );

    }
    
    protected final void complete() {
        log.info( format( "%s%s fully loaded in %dms", GREEN, name, System.currentTimeMillis() - start ) );
        
    }
    
    private final void logTitle() {
        String info = format( "#      %s - Version %s      #", name, version ),
            header = format( "%s%s", DARK_RED, repeat( "#", info.length() ) ),
            blank = format( "%s#%s#", DARK_RED, repeat( " ", info.length() - 2 ) ),
            
            disabled = DARK_RED + "Plugin has been disabled through the config file, no features for this plugin will work", 
            devSher = AQUA + "Developed by SherRao",
            system = format( "%sRunning on %s%s %s", AQUA, ChatColor.RED, 
                    super.getServer().getName(), super.getServer().getBukkitVersion() );
        
        info = format( "%s#      %s%s - Version %s      %s#", DARK_RED, AQUA, name, version, DARK_RED );
        log.special( "" );
        log.special( header );
        log.special( blank );
        log.special( info );
        log.special( blank);
        log.special( header );
        log.special( "" );
        log.special( devSher );
        if( !enabled ) log.info( disabled );
        log.special( system );
        log.special( "" );
        
    }
    
    protected final void needConfig( boolean needConfig ) { this.needConfig = needConfig; }
    
    public final ColoredLogger getColoredLogger() { return log; }
    
    public final SherConfiguration getCustomConfig() { return config; }
    
    public final SherCommand asCommand( String name ) { return commands.get( name ); }
    
    public final SherEventListener asEventListener( String name ) { return eventListeners.get( name ); }

    public final HashMap<String, SherCommand> getRegisteredCommands() { return commands; }
    
    public final HashMap<String, SherEventListener> getRegisteredEventListeners() { return eventListeners; }
    
}