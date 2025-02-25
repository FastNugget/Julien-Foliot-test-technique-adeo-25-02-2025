package com.onetranslate.configuration;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.MongoTransactionManager;

import java.util.List;

@Configuration
@Slf4j(topic = "MongoDbConfiguration")
public class MongoDbConfiguration {

    // -- VARS
    @Value("${spring.profiles.active}") private String stringProfilActive;

    @Value("${spring.data.mongodb.uri}") private String stringMongoDbUri;
    @Value("${spring.data.mongodb.uriEnd}") private String stringMongoDbUriEnd;
    @Value("${spring.data.mongodb.database}") private String stringMongoDatabase;
    @Value("${spring.data.mongodb.username}") private String stringMongoDbUsername;
    @Value("${spring.data.mongodb.password}") private String stringMongoDbPassword="";

    private final List<String> listStringMongoPrefixToFind = List.of("mongodb://","mongodb+srv://");


    // -- INITIALISATOR ------------------------------------------------------------------------------------------------

    @PostConstruct
    public void init(){}


    // -- IMPL ---------------------------------------------------------------------------------------------------------

    public @Bean MongoClient mongoClient() {

        // -- Init connection string
        StringBuilder stringBuilderMongoDbUri = new StringBuilder();
        String stringPrefixToFind
            = this.listStringMongoPrefixToFind.stream()
                .filter(stringPrefix -> this.stringMongoDbUri.startsWith(stringPrefix))
                .findFirst()
                    .orElse(null);

        // -- Check
        if(this.stringProfilActive != null
            && (this.stringProfilActive.equals("dev") == false)
            && (this.stringProfilActive.equals("dev-docker") == false)
            && (this.stringProfilActive.equals("dev-test") == false)){

            // -- Extract
            String stringFirstPart = this.stringMongoDbUri.substring(0, stringPrefixToFind.length());
            String stringSecondPart = this.stringMongoDbUri.substring(stringPrefixToFind.length());

            // -- Remove the % trailing character, it is a bug in stack 3S that add a % at the end of the uri
            if (stringSecondPart.endsWith("%")){

                // -- Set
                stringSecondPart = stringSecondPart.substring(stringSecondPart.length()-1);

            }

            // -- Build
            stringBuilderMongoDbUri.append(stringFirstPart);
            stringBuilderMongoDbUri.append(this.stringMongoDbUsername).append(":").append(this.stringMongoDbPassword).append("@");
            stringBuilderMongoDbUri.append(stringSecondPart);
            stringBuilderMongoDbUri.append("/").append(this.stringMongoDatabase);
            stringBuilderMongoDbUri.append(this.stringMongoDbUriEnd.isEmpty()?"":"?").append(this.stringMongoDbUriEnd);

        }else{

            // -- Set
            stringBuilderMongoDbUri.append(this.stringMongoDbUri);

        }

        // -- Build and commit
        return MongoClients.create(stringBuilderMongoDbUri.toString());

    }

    public @Bean MongoTransactionManager transactionManager(MongoDatabaseFactory mongoDatabaseFactory) {

        // -- Build & Commit
        return new MongoTransactionManager(mongoDatabaseFactory);

    }

}
