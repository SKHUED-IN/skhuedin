package com.skhuedin.skhuedin.controller.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CommonResponse<T> extends BasicResponse {

    private T data;
    private int count;

    public CommonResponse(T data) {
        this.data = data;
        if (data instanceof List) {
            this.count = ((List<?>) data).size();
        } else {
            this.count = 1;
        }
    }
}