package br.com.infnet.tp1.controller;

import br.com.infnet.tp1.dto.OperationRequest;
import br.com.infnet.tp1.dto.OperationResponse;
import br.com.infnet.tp1.service.MathService;
import jakarta.validation.Valid;
import java.math.BigDecimal;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/math")
public class MathController {

    private final MathService mathService;

    public MathController(MathService mathService) {
        this.mathService = mathService;
    }

    @RequestMapping(path = "/add", method = RequestMethod.GET)
    public OperationResponse addGet(@RequestParam BigDecimal a, @RequestParam BigDecimal b) {
        return buildResponse("addition", a, b, mathService.add(a, b));
    }

    @RequestMapping(path = "/add", method = RequestMethod.POST)
    public OperationResponse addPost(@Valid @RequestBody OperationRequest request) {
        return buildResponse("addition", request.a(), request.b(), mathService.add(request.a(), request.b()));
    }

    @RequestMapping(path = "/subtract", method = RequestMethod.GET)
    public OperationResponse subtractGet(@RequestParam BigDecimal a, @RequestParam BigDecimal b) {
        return buildResponse("subtraction", a, b, mathService.subtract(a, b));
    }

    @RequestMapping(path = "/subtract", method = RequestMethod.POST)
    public OperationResponse subtractPost(@Valid @RequestBody OperationRequest request) {
        return buildResponse("subtraction", request.a(), request.b(), mathService.subtract(request.a(), request.b()));
    }

    @RequestMapping(path = "/multiply", method = RequestMethod.GET)
    public OperationResponse multiplyGet(@RequestParam BigDecimal a, @RequestParam BigDecimal b) {
        return buildResponse("multiplication", a, b, mathService.multiply(a, b));
    }

    @RequestMapping(path = "/multiply", method = RequestMethod.POST)
    public OperationResponse multiplyPost(@Valid @RequestBody OperationRequest request) {
        return buildResponse("multiplication", request.a(), request.b(), mathService.multiply(request.a(), request.b()));
    }

    @RequestMapping(path = "/divide", method = RequestMethod.GET)
    public OperationResponse divideGet(@RequestParam BigDecimal a, @RequestParam BigDecimal b) {
        return buildResponse("division", a, b, mathService.divide(a, b));
    }

    @RequestMapping(path = "/divide", method = RequestMethod.POST)
    public OperationResponse dividePost(@Valid @RequestBody OperationRequest request) {
        return buildResponse("division", request.a(), request.b(), mathService.divide(request.a(), request.b()));
    }

    @RequestMapping(path = "/power", method = RequestMethod.GET)
    public OperationResponse powerGet(@RequestParam BigDecimal a, @RequestParam BigDecimal b) {
        return buildResponse("power", a, b, mathService.power(a, b));
    }

    @RequestMapping(path = "/power", method = RequestMethod.POST)
    public OperationResponse powerPost(@Valid @RequestBody OperationRequest request) {
        return buildResponse("power", request.a(), request.b(), mathService.power(request.a(), request.b()));
    }

    private OperationResponse buildResponse(String operation, BigDecimal a, BigDecimal b, BigDecimal result) {
        return new OperationResponse(operation, a, b, result);
    }
}

