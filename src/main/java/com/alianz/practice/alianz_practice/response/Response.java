package com.alianz.practice.alianz_practice.response;

import java.util.Map;
import java.util.TreeMap;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class Response {
    private static Map<String, Object> response = new TreeMap<>();

    private static final String STATE = "STATE";
    private static final String STATUS = "STATUS";
    private static final String TEXT = "PAYLOAD";
    private static final String SUCCESS = "SUCCESS";
    private static final String ERROR = "ERROR";

    public Map<String, Object> buildResponse(Object payLoad, HttpStatus status) {
        if(HttpStatus.OK.equals(status)){
            response.put(STATE, SUCCESS);
        } else{
            response.put(STATE, ERROR);
        }
        response.put(STATUS, status);
        response.put(TEXT, payLoad);
        return response;
    }
}
