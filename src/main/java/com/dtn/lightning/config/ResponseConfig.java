package com.dtn.lightning.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ResponseConfig {

    @Getter
    private final Integer successCode;
    @Getter
    private final String successMessage;
    @Getter
    private final String successDetails;

    @Getter
    private final Integer failedCode;
    @Getter
    private final String failedMessage;
    @Getter
    private final String failedDetails;

    public ResponseConfig(
            @Value("${response.success.code}") final Integer successCode,
            @Value("${response.success.message}") final String successMessage,
            @Value("${response.success.details}") final String successDetails,
            @Value("${response.failed.code}") final Integer failedCode,
            @Value("${response.failed.message}") final String failedMessage,
            @Value("${response.failed.details}") final String failedDetails
    ) {
        this.successCode = successCode;
        this.successMessage = successMessage;
        this.successDetails = successDetails;

        this.failedCode = failedCode;
        this.failedMessage = failedMessage;
        this.failedDetails = failedDetails;
    }

}
