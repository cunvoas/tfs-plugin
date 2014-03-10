package hudson.plugins.tfs.commands;

import java.io.IOException;

/**
 * Server config interface.
 * @author Nicolas De loof
 * @author Christophe Unvoas
 */
public interface ServerConfigurationProvider {

    /**
     *  Client TFS TEE-CLC-10 Constant. 
     */
    static final String TFS_TEE_CLC_10="TEE_CLC_10";
    
    /**
     * Client TFS TEE-CLC-11 Constant. 
     */
    static final String TFS_TEE_CLC_11="TEE_CLC_11";
    
    static final String VERSION_LATEST = "T";
    static final String VERSION_LABEL = "L";
    static final String VERSION_DATE = "D";
    static final String VERSION_COMMIT = "C";
    static final String VERSION_WORKSPACE = "W";
    
    /**
     * Version on TFS Server.
     * Check TEE Client for TFS Server compatibility.
     * @return 
     */
    public String getVersion();
    
    public String getUrl();

    public String getUserName();

    public String getUserPassword();
    
    public String getLocalHostname() throws IOException, InterruptedException;
    
    public String getTypeVersion();
    public String getLabelName();
}
