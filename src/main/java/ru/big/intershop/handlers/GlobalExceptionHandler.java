package ru.big.intershop.handlers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.reactive.result.view.Rendering;
import reactor.core.publisher.Mono;
import ru.big.intershop.enums.ViewName;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(exception = IllegalArgumentException.class)
    public Mono<Rendering> orderAdvice(Exception exception) {
        Rendering rendering = Rendering.view(ViewName.ERROR.getValue())
                .modelAttribute("error", exception.getMessage())
                .status(HttpStatus.NOT_FOUND)
                .build();

        return Mono.just(rendering);
    }
}
