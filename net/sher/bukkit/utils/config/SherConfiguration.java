package net.sher.bukkit.utils.config;

import static org.bukkit.ChatColor.translateAlternateColorCodes;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import net.sher.bukkit.utils.plugin.SherPlugin;

public class SherConfiguration extends FileConfiguration {
    
    protected SherPlugin pl;
    protected FileConfiguration config;
        
    public SherConfiguration( SherPlugin pl ) {
        super();
        this.pl = pl;
        this.config = pl.getConfig();        
        super.setDefaults( config );

    }
    
    public SherConfiguration( SherPlugin pl, File file ) {
        super();        
        this.pl = pl;
        this.config = YamlConfiguration.loadConfiguration( file );
        super.setDefaults( config );
        
    }

    @Override
    public Object get( String path, Object def ) {
        if( !config.contains( path ) )
            throw new IllegalArgumentException( "The path " + path + " in the confguration file " + config.getName() + " doesn't exist!");
        
        return super.get( path, def );
    
    }
    
    @Override
    public String getString( String path ) {
        return translateAlternateColorCodes( '&', super.getString( path ) );
        
    }
    
    @Override
    public String getString( String path, String def ) {
        return translateAlternateColorCodes( '&', super.getString( path, def ) );
        
    }
    
    @Override
    public List< String > getStringList( String path ) {
        List< String > list = new ArrayList< String >();
        for( String str : config.getStringList( path ) )
            list.add( translateAlternateColorCodes( '&', str ) );

        return list;
        
    }

    @Override
    public String saveToString() {
        return config.saveToString();
    
    }


    @Override
    public void loadFromString( String contents ) 
            throws InvalidConfigurationException {
        config.loadFromString( contents );
    
    }

    @Override
    protected String buildHeader() {
        return null;
    
    }
    
    public Material getMaterial( String path ) {
        String str = super.getString( path );
        Material m = Material.matchMaterial( str );
        if( m == null )
            m = Material.valueOf( str );
        
        if( m == null ) 
            m = Material.getMaterial( str );
        
        return m;
        
    }
    
    public Sound getSound( String path ) {
        return Sound.valueOf( super.getString( path ) );
        
    }
    
}