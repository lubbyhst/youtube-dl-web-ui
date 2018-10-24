package de.hst.lubby.youtubedlwebui.service;

import de.hst.lubby.youtubedlwebui.YoutubeDlWebUiApplication;
import de.hst.lubby.youtubedlwebui.enums.PropertyKeys;
import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.FileBasedConfiguration;
import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.builder.ConfigurationBuilderEvent;
import org.apache.commons.configuration2.builder.FileBasedConfigurationBuilder;
import org.apache.commons.configuration2.builder.ReloadingFileBasedConfigurationBuilder;
import org.apache.commons.configuration2.builder.fluent.Parameters;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.apache.commons.configuration2.ex.ConversionException;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class PropertiesService {
    private Logger logger = Logger.getLogger(PropertiesService.class.getName());

    @EventListener(ApplicationReadyEvent.class)
    public void checkForExternalConfigurationFile() {
        logger.info("Checking for external properties.");
        initialize("config.properties");
    }

    private static final String propertiesFilename = "config.properties";

    private FileBasedConfigurationBuilder<FileBasedConfiguration> defaultConfigurationBuilder = null;

    private ReloadingFileBasedConfigurationBuilder<FileBasedConfiguration> externalConfigurationBuilder = null;

    private File externalConfigurationFile = null;

    public PropertiesService() {
        final File defaultPropertiesFile = new File(this.getClass().getClassLoader().getResource(PropertiesService
                .propertiesFilename).getFile());
        this.defaultConfigurationBuilder = new FileBasedConfigurationBuilder<FileBasedConfiguration>
                (PropertiesConfiguration.class)
                .configure(new Parameters().fileBased()
                        .setFile(defaultPropertiesFile));
    }

    private static void writeExternalPropertiesFile(final InputStream source, final File dest) throws IOException {
        try (
                final InputStream is = source;
                final OutputStream os = new FileOutputStream(dest)
        ) {
            final byte[] buffer = new byte[1024];
            int length;
            while ((length = is.read(buffer)) > 0) {
                os.write(buffer, 0, length);
            }
        }
    }

    /**
     * Initialize the external configuration of the configuration manager instance.
     *
     * @param configurationFilePath the configuration file path
     */
    public void initialize(final String configurationFilePath) {
        this.externalConfigurationFile = new File(configurationFilePath);
        // If configuration file does not exits. Try to create a new properties file from source.
        if (!this.externalConfigurationFile.exists()) {
            try {
                PropertiesService.writeExternalPropertiesFile(this.getClass().getClassLoader().getResourceAsStream
                        (PropertiesService.propertiesFilename), this.externalConfigurationFile);
                logger.info(String.format("External configuration file does " +
                        "not exists. Writing file to: %s", configurationFilePath));
            } catch (final IOException e) {
                logger.log(Level.SEVERE,e.getMessage(),e);
            }
        }
        // create external configuration builder with event listener for configuration changes.
        this.externalConfigurationBuilder =
                new ReloadingFileBasedConfigurationBuilder<FileBasedConfiguration>(PropertiesConfiguration.class)
                        .configure(new Parameters().fileBased().setFile(this.externalConfigurationFile));
        this.externalConfigurationBuilder.setAutoSave(true);
        this.externalConfigurationBuilder.addEventListener(ConfigurationBuilderEvent.CONFIGURATION_REQUEST,
                eventListener -> PropertiesService.this.externalConfigurationBuilder.getReloadingController()
                        .checkForReloading(null));
    }

    /**
     * Reads the value of the given key from properties.
     *
     * @param propertyKey
     * @return value as String or null if the property does not exists.
     */
    public String getStringValue(final PropertyKeys propertyKey) {
        return getStringValue(propertyKey, null);
    }

    /**
     * Reads the value of the given key from properties.
     *
     * @param propertyKey property key to use
     * @param defaultValue   default value for return value
     * @return value as String or defaultValue as String or null if an exception occurs.
     */
    public String getStringValue(final PropertyKeys propertyKey, final String defaultValue) {
        if (propertyKey != null) {
            return getStringValue(propertyKey.getKey(), defaultValue);
        }
        return defaultValue;
    }

    /**
     * Reads the value of the given key from properties.
     *
     * @param key
     * @param defaultValue
     * @return value as String or defaultValue as String or null if an exception occurs.
     */
    public String getStringValue(final String key, final String defaultValue) {
        try {
            if (this.externalConfigurationBuilder != null) {
                final Configuration externalConfiguration = this.externalConfigurationBuilder.getConfiguration();
                if (externalConfiguration.getProperty(key) != null) {
                    return externalConfiguration.getString(key);
                }
            }
            return this.defaultConfigurationBuilder.getConfiguration().getString(key, defaultValue);
        } catch (ConversionException | ConfigurationException e) {
            return null;
        }
    }

    /**
     * Sets a value of the given key from properties.
     *
     * @param propertyKey key to reference
     * @param value          value to write to key
     */
    public void writeStringValue(final PropertyKeys propertyKey, final String value) {
        try {
            final Configuration externalConfiguration = this.externalConfigurationBuilder.getConfiguration();
            final String keyvalue = externalConfiguration.getString(propertyKey.getKey());
            if (keyvalue != null && value != null && !keyvalue.equals(value)) {
                externalConfiguration.setProperty(propertyKey.getKey(), value);
            }
        } catch (final ConfigurationException e) {
            logger.log(Level.SEVERE,e.getMessage(), e);
        }
    }
}
