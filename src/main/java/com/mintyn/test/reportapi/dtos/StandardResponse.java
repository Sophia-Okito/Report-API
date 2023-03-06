package com.mintyn.test.reportapi.dtos;

import com.mintyn.test.reportapi.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StandardResponse {

    private Status status;

    private String message;

    private Object data;

    public StandardResponse(Status status) {
        this.status = status;
    }

    public StandardResponse(Status status, String message) {
        this.status = status;
        this.message = message;
    }

    public StandardResponse(Status status, Object data) {
        this.status = status;
        this.data = data;
    }
}
