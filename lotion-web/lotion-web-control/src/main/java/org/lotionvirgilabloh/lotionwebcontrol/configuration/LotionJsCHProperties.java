package org.lotionvirgilabloh.lotionwebcontrol.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Lotion JsCH properties.
 */
@Configuration
@ConfigurationProperties(prefix = "lotion.jsch")
public class LotionJsCHProperties {

    /**
     * Whether to enable JsCH or not.
     */
    private Boolean enabled;
    /**
     * Whether using builtin properties or properties form database.
     */
    private Boolean builtin;
    /**
     * The path where remote server stores all shells.
     */
    private String storeShPath;
    /**
     * The URL where JsCH to connect.
     */
    private String url;
    /**
     * The username JsCH uses to connect.
     */
    private String username;
    /**
     * The password JsCH uses to connect.
     */
    private String password;


    public LotionJsCHProperties() {
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public Boolean getBuiltin() {
        return builtin;
    }

    public void setBuiltin(Boolean builtin) {
        this.builtin = builtin;
    }

    public String getStoreShPath() {
        return storeShPath;
    }

    public void setStoreShPath(String storeShPath) {
        this.storeShPath = storeShPath;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "LotionJsCHProperties{" +
                "enabled=" + enabled +
                ", builtin=" + builtin +
                ", storeShPath='" + storeShPath + '\'' +
                ", url='" + url + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
