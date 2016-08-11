package net.sher.bukkit.utils;

import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import net.minecraft.server.v1_8_R3.IChatBaseComponent.ChatSerializer;
import net.sher.utils.StringMultiJoiner;
import net.minecraft.server.v1_8_R3.PacketPlayOutChat;

public class JsonBuilder {
     
    public enum ClickAction {
        RUN_COMMAND, 
        SUGGEST_COMMAND, 
        OPEN_URL;
               
    }
        
    public enum HoverAction {
        SHOW_TEXT
        
    }
     
    protected List<String> extras;
     
    public JsonBuilder( String... text ) {
        this.extras = new ArrayList<String>();
        for( String extra : text )
            this.parse( extra );
       
    }
     
    public JsonBuilder parse( String text ) {
        String regex = "[&§]{1}([a-fA-Fl-oL-O0-9]){1}";
        text = text.replaceAll( regex, "§$1" );
        if( !Pattern.compile( regex ).matcher( text ).find() ) {
            text( text );
            return this;
               
        }
               
        String[] words = text.split( regex );
        int index = words[0].length();
        for( String word : words ) {
            if( index != words[0].length() )
                text( word ).hoverEvent( "§" + text.charAt( index - 1 ) );
                   
            index += word.length() + 2;
              
        }
               
        return this;
    
    }
     
    public JsonBuilder text( String text ) {
        extras.add( "{text:\"" + text + "\"}" );
        return this;
    
    }
     
    public JsonBuilder color( ChatColor color ) {
        String c = color.name().toLowerCase();
        addSegment( color.isColor() ? "color:" + c : c + ":true" );
        return this;
    
    }
     
    public JsonBuilder clickEvent( ClickAction action, String value ) {
        addSegment( "clickEvent:{action:" + action.toString().toLowerCase() + ",value:\"" + value + "\"}" );
        return this;
     
    }

    public JsonBuilder hoverEvent( String color ) {
        while( color.length() != 1 ) 
            color = color.substring(1).trim();
            
        color( ChatColor.getByChar( color ) );
        return this;
       
    }

    public JsonBuilder multiHoverEvent( HoverAction action, String... values ) {
        StringMultiJoiner smj = new StringMultiJoiner( "\n" );
        for( String str : values )
            smj.add( str );
        
        hoverEvent( action, smj.toString() );
        return this;
        
    }
    
    public JsonBuilder hoverEvent( HoverAction action, String value ) {
        value = ChatColor.translateAlternateColorCodes( '&', value );
        addSegment( "hoverEvent:{action:" + action.toString().toLowerCase() + ",value:\"" + value + "\"}" );
        return this;
        
    }
    
    public JsonBuilder empty() {
        extras.clear();
        return this;
        
    }
     
    public void toPlayer( Player... players ) {
        for( Player p : players )
            this.toPlayer( p );
        
    }
    
    public void toPlayer( List<Player> players ) {
        for( Player p : players )
            this.toPlayer( p );
        
    }
    
    public void toPlayer( Player player ) {
        ( (CraftPlayer) player ).getHandle().playerConnection.sendPacket(
                new PacketPlayOutChat( ChatSerializer.a( toString() ) ) );
        
    }
        
    @Override
    public String toString() {
        if( extras.size() <= 1 ) 
            return extras.size() == 0 ? "{text:\"\"}" : extras.get(0);
            
        String text = extras.get(0).substring( 0, extras.get(0).length() - 1 ) + ",extra:[";
        extras.remove(0);;
        for ( String extra : extras )
            text = text + extra + ",";
            
        text = text.substring( 0, text.length() - 1 ) + "]}";
        return text;
            
    }
    
    private void addSegment( String segment ) {
        String lastText = extras.get( extras.size() - 1 );
        lastText = lastText.substring( 0, lastText.length() - 1 ) + "," + segment + "}";
        extras.remove( extras.size() - 1 );
        extras.add( lastText );
        
    }
    
}