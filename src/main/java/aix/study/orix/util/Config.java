
package aix.study.orix.util;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.configuration.reloading.FileChangedReloadingStrategy;

/**
 * @author Sujith T
 * 
 * <!In God We Trust>
 */
public class Config {
    
	private static final long RELOAD_DELAY = 30000;
	private static PropertiesConfiguration envConfigs = null;
    
    /**
	 * Load environments file
	 */
	private static void init(){
		
		String propertyFile = System.getenv("AIX_STUDY");
		if(propertyFile == null || propertyFile.trim().isEmpty()) {
			throw new IllegalStateException("Unable to locate the environments file, please set the file path in environment variable AIX_STUDY");
		}
		
		try {
			envConfigs = new PropertiesConfiguration(propertyFile);
			FileChangedReloadingStrategy fileChangedReloadingStrategy = new FileChangedReloadingStrategy();
			fileChangedReloadingStrategy.setRefreshDelay(RELOAD_DELAY);			
			
			envConfigs.setReloadingStrategy(fileChangedReloadingStrategy);
			
		} catch (ConfigurationException e) {
			throw new IllegalStateException("Unable to read file: " + propertyFile, e);
		}
	}
    
	/**
	 * Returns Environment Values
	 * 
	 * @param key
	 * @return String
	 * @throws IllegalArgumentException
	 * 
	 */	
	public static String get(String key) {
		
		if(key == null || key.trim().isEmpty()){
			throw new IllegalArgumentException("Key provided is invalid");
		}		
		
        if(envConfigs == null) {
            init();
        }
        
		return envConfigs.getString(key);
	}    
}
