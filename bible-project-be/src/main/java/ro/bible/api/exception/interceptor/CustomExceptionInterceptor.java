package ro.bible.api.exception.interceptor;

import io.quarkus.logging.Log;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import ro.bible.api.exception.CustomException;

@Provider
public class CustomExceptionInterceptor implements ExceptionMapper<CustomException> {

    @Override
    public Response toResponse(CustomException exception) {
        Log.errorf("Custom exception: %s", exception.getMessage());

        return Response
                .status(exception.getCode())
                .entity(exception.toErrorMessage())
                .build();
    }
}
