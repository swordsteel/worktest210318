package test.work.api.parks;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.context.request.async.DeferredResult;
import test.work.api.exceptions.ExceptionResponse;

import javax.validation.Valid;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;
import java.util.function.Supplier;

@Slf4j
@RestController
@RequestMapping("/parks")
@RequiredArgsConstructor
public class ParksController {

    private final ParkFacade parksFacade;

    @GetMapping
    public DeferredResult<ResponseEntity<?>> getParks(WebRequest request) {
        DeferredResult<ResponseEntity<?>> deferredResult = new DeferredResult<>();
        getApiData(
                parksFacade::getParks,
                (result, throwable) -> deferredResult.setResult(
                        throwable != null
                                ? makeExceptionResponseEntity(request, throwable, HttpStatus.BAD_REQUEST)
                                : ResponseEntity.ok(result)));
        return deferredResult;
    }

    @GetMapping("/{stateCode}")
    public DeferredResult<ResponseEntity<?>> getPark(@PathVariable("stateCode") String stateCode, WebRequest request) {
        DeferredResult<ResponseEntity<?>> deferredResult = new DeferredResult<>();
        getApiData(
                () -> parksFacade.getPark(stateCode),
                (result, throwable) -> deferredResult.setResult(
                        throwable != null
                                ? makeExceptionResponseEntity(request, throwable, HttpStatus.NOT_FOUND)
                                : ResponseEntity.ok(result)));
        return deferredResult;
    }

    @PostMapping
    public DeferredResult<ResponseEntity<?>> addParks(@Valid @RequestBody ParkAddRequest parkAddRequest, WebRequest request) {
        DeferredResult<ResponseEntity<?>> deferredResult = new DeferredResult<>();
        getApiData(
                () -> parksFacade.addParks(parkAddRequest),
                (result, throwable) -> deferredResult.setResult(
                        throwable != null
                                ? makeExceptionResponseEntity(request, throwable, HttpStatus.BAD_REQUEST)
                                : new ResponseEntity<>(result, HttpStatus.CREATED)));
        return deferredResult;
    }

    @PutMapping("/{stateCode}")
    public DeferredResult<ResponseEntity<?>> updateParks(
            @PathVariable("stateCode") String stateCode,
            @Valid @RequestBody ParkUpdateRequest parkUpdateRequest,
            WebRequest request
    ) {
        DeferredResult<ResponseEntity<?>> deferredResult = new DeferredResult<>();
        getApiData(
                () -> parksFacade.updateParks(stateCode, parkUpdateRequest),
                (result, throwable) -> deferredResult.setResult(
                        throwable != null
                                ? makeExceptionResponseEntity(request, throwable, HttpStatus.NOT_FOUND)
                                : new ResponseEntity<>(result, HttpStatus.CREATED)));
        return deferredResult;
    }

    private ResponseEntity<ExceptionResponse> makeExceptionResponseEntity(
            WebRequest request,
            Throwable throwable,
            HttpStatus httpStatus
    ) {
        return new ResponseEntity<>(new ExceptionResponse(throwable.getCause().getMessage(), request), httpStatus);
    }

    private void getApiData(Supplier<Object> supplier, BiConsumer<Object, Throwable> stringThrowableBiConsumer) {
        log.info("Request received");
        CompletableFuture.supplyAsync(supplier)
                .whenCompleteAsync(stringThrowableBiConsumer);
        log.info("Servlet thread released");
    }

}
