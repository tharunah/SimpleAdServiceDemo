package com.tharun.rest.ad.error;

import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResponseErrorHandler;

import java.io.IOException;

/**
 * Custom error handler for REST client calls.
 * Handles HTTP errors from RestTemplate requests.
 *
 * @since 1.0
 */
@Component
public class RestClientErrorHandler implements ResponseErrorHandler {

    public RestClientErrorHandler() {
        super();
    }

    @Override
    public boolean hasError(ClientHttpResponse response) throws IOException {
        return !response.getStatusCode().is2xxSuccessful();
    }

    @Override
    public void handleError(ClientHttpResponse response) throws IOException {
        if (response.getStatusCode() == HttpStatus.FORBIDDEN) {
            throw new IllegalAccessError("Access forbidden: " + response.getStatusText());
        } else if (!response.getStatusCode().is2xxSuccessful()) {
            throw new RuntimeException("HTTP Error: " + response.getStatusCode() + " - " + response.getStatusText());
        }

    }
}