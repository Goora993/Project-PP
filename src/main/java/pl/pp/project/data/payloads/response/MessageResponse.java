package pl.pp.project.data.payloads.response;

import lombok.Data;

public @Data
class MessageResponse {

    private String message;

    public MessageResponse(String message){
        this.message = message;
    }
}
