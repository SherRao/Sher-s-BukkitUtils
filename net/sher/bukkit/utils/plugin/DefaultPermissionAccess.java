package net.sher.bukkit.utils.plugin;

import org.bukkit.permissions.PermissionDefault;

public enum DefaultPermissionAccess {
    
    OP( PermissionDefault.OP ), DEFAULT_PLAYER( PermissionDefault.TRUE ), NOBODY( PermissionDefault.FALSE );
    
    protected PermissionDefault permDefault;
    
    private DefaultPermissionAccess( PermissionDefault permDefault ) {
        this.permDefault = permDefault;
    
    }
    
    public PermissionDefault toPermissionDefault() {
        return permDefault;
        
    }
    
}
