package com.brainrush.RestController;

import java.time.Instant;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ExceptionHandlerRestController {

    @ExceptionHandler(ResponseStatusException.class)
    public Object handleResponseStatusException(
            ResponseStatusException ex,
            HttpServletRequest request) {

        HttpStatus status = HttpStatus.valueOf(ex.getStatusCode().value());

        if (isApiRequest(request)) {
            Map<String, Object> body = buildBody(status.value(), status.getReasonPhrase(), ex.getReason(), request);
            return ResponseEntity.status(status).body(body);
        }

        return buildMav("errors/error", status, ex.getReason());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Object handleValidationException(
            MethodArgumentNotValidException ex,
            HttpServletRequest request) {

        if (isApiRequest(request)) {
            Map<String, String> fieldErrors = ex.getBindingResult()
                    .getFieldErrors()
                    .stream()
                    .collect(Collectors.toMap(
                            FieldError::getField,
                            f -> f.getDefaultMessage() != null ? f.getDefaultMessage() : "Valore non valido",
                            (first, second) -> first));

            Map<String, Object> body = buildBody(400, "Bad Request", "Errore di validazione", request);
            body.put("fieldErrors", fieldErrors);
            return ResponseEntity.badRequest().body(body);
        }

        return buildMav("errors/400", HttpStatus.BAD_REQUEST, "Errore di validazione");
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public Object handleNoResourceFound(
            NoResourceFoundException ex,
            HttpServletRequest request) {

        if (isApiRequest(request)) {
            Map<String, Object> body = buildBody(404, "Not Found", "Endpoint non trovato", request);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);
        }

        return buildMav("errors/404", HttpStatus.NOT_FOUND, "Pagina non trovata");
    }

    @ExceptionHandler(Exception.class)
    public Object handleGenericException(
            Exception ex,
            HttpServletRequest request) {

        if (isApiRequest(request)) {
            Map<String, Object> body = buildBody(500, "Internal Server Error", "Si è verificato un errore imprevisto", request);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(body);
        }

        return buildMav("errors/500", HttpStatus.INTERNAL_SERVER_ERROR, "Errore interno del server");
    }

    // Helpers

    private boolean isApiRequest(HttpServletRequest request) {
        return request.getRequestURI().startsWith("/api/");
    }

    private Map<String, Object> buildBody(int status, String error, String message, HttpServletRequest request) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", Instant.now());
        body.put("status", status);
        body.put("error", error);
        body.put("message", message);
        body.put("path", request.getRequestURI());
        return body;
    }

    private ModelAndView buildMav(String viewName, HttpStatus status, String message) {
        ModelAndView mav = new ModelAndView(viewName);
        mav.setStatus(status);
        mav.addObject("status", status.value());
        mav.addObject("error", status.getReasonPhrase());
        mav.addObject("message", message);
        return mav;
    }
}
