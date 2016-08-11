package net.sher.bukkit.utils;


public enum TimeUnits {
    
    MILLIS( 1L ),
    SECOND( MILLIS.ms() * 1000 ), 
    MINUTE( SECOND.ms() * 60 ),
    HOUR( MINUTE.ms() * 60 ),
    DAY( HOUR.ms() * 24 ),
    WEEK( DAY.ms() * 7 );
    
    protected long value;
    
    TimeUnits( long value ) {
        this.value = value;
        
    }
    
    
    public long ms() {
        return value;
        
    }
    
}
