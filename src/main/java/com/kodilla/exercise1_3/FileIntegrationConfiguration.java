package com.kodilla.exercise1_3;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.Pollers;
import org.springframework.integration.file.DefaultFileNameGenerator;
import org.springframework.integration.file.FileReadingMessageSource;
import org.springframework.integration.file.FileWritingMessageHandler;
import org.springframework.integration.file.support.FileExistsMode;

import java.io.File;

@Configuration
public class FileIntegrationConfiguration {

    @Bean
    IntegrationFlow fileIntegrationFlow(
            FileReadingMessageSource fileAdapter,
            FileWritingMessageHandler outputFileHandler) {

        return IntegrationFlow.from(fileAdapter, config -> config.poller(Pollers.fixedDelay(1000)))
                .<File, String>transform(file -> file.getName() + System.lineSeparator())
                .handle(outputFileHandler)
                .get();
    }

    @Bean
    FileReadingMessageSource fileAdapter() {
        FileReadingMessageSource fileSource = new FileReadingMessageSource();
        fileSource.setDirectory(new File("data/input"));
        return fileSource;
    }

    @Bean
    FileWritingMessageHandler outputFileAdapter() {
        File directory = new File("data/output");
        FileWritingMessageHandler handler = new FileWritingMessageHandler(directory);
        handler.setExpectReply(false);
        handler.setFileNameGenerator(defaultFileNameGenerator());
        handler.setFileExistsMode(FileExistsMode.APPEND);

        return handler;
    }

    @Bean
    public DefaultFileNameGenerator defaultFileNameGenerator() {
        DefaultFileNameGenerator defaultFileNameGenerator = new DefaultFileNameGenerator();
        defaultFileNameGenerator.setExpression("'my-file-list.txt'");
        return defaultFileNameGenerator;
    }

}