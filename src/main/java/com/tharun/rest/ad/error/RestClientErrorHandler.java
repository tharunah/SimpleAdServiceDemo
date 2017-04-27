package com.tharun.rest.ad.error;

import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResponseErrorHandler;

import java.io.IOException;

/**
 * Created by tharu on 4/27/2017.
 */
@Component
public class RestClientErrorHandler implements ResponseErrorHandler {


    public RestClientErrorHandler() {
        super();
    }

    @Override
    public boolean hasError(ClientHttpResponse clienthttpresponse) throws IOException {

        if (clienthttpresponse.getStatusCode() != HttpStatus.OK) {

            if (clienthttpresponse.getStatusCode() == HttpStatus.FORBIDDEN) {

                return true;
            }
        }
        return false;
    }

    @Override
    public void handleError(ClientHttpResponse response) throws IOException {
        if (response.getStatusCode() == HttpStatus.FORBIDDEN) {

        }

    }
}