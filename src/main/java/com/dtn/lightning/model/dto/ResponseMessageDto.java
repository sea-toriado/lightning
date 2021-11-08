package com.dtn.lightning.model.dto;

import com.dtn.lightning.config.ResponseConfig;
import lombok.Data;

import java.util.Date;

@Data
public class ResponseMessageDto {

    private Integer code;
    private String message;
    private String details;
    private Date timestamp;

    public ResponseMessageDto(
            ResponseConfig responseConfig,
            Boolean isSuccess
    ) {
        if(isSuccess.booleanValue()) {
            this.code = responseConfig.getSuccessCode();
            this.message = responseConfig.getSuccessMessage();
            this.details = responseConfig.getSuccessDetails();
        } else {
            this.code = responseConfig.getFailedCode();
            this.message = responseConfig.getFailedMessage();
            this.details = responseConfig.getFailedDetails();
        }
        this.timestamp = new Date();
    }

}
