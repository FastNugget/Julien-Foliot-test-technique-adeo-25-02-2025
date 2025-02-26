package utils;

import org.springframework.util.ResourceUtils;
import uk.org.webcompere.systemstubs.environment.EnvironmentVariables;

import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class TestUtils {


    // -- CALLBACKS ----------------------------------------------------------------------------------------------------

    public static void setEnv(EnvironmentVariables environmentVariables) throws IOException {

        // -- Env
        Properties properties = new Properties();

        // -- Try find file in resource (case test in local environment (you laptop))
        try{

            // -- Trigger
            properties.load(Files.newInputStream(ResourceUtils.getFile("classpath:.env").toPath()));

        }catch (Exception exception){

            /**
             * If the file doesn't exist also, then the exception is not captured to make the test fail
             */

            // -- Try find file in file system (case test in github action)
            properties.load(Files.newInputStream(ResourceUtils.getFile("file:.env-dev-local").toPath()));

        }

        // -- Init
        Map<String,String> mapPropertiesFormatted = new HashMap<>();
        properties.forEach((key,value) -> {

            // -- Init
            String stringKeyFormatted = (String)key;
            String stringOldValue = (String)value;
            //String valueFormatted = stringOldValue.substring(1,stringOldValue.length()-1);
            String valueFormatted = stringOldValue;
            
            // -- Set
            mapPropertiesFormatted.put(stringKeyFormatted, valueFormatted);

        });

        // -- Set env
        mapPropertiesFormatted.forEach(environmentVariables::set);

    }

}