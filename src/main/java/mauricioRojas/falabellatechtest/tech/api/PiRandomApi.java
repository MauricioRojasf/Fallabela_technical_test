package mauricioRojas.falabellatechtest.tech.api;

import java.util.concurrent.ExecutionException;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2020-11-09T17:36:06.090-04:00")

@Api(value = "PiRandom", description = "The random Pi Digits Generator")
public interface PiRandomApi {

    @ApiOperation(value = "Generar los Decimales de pi dado un parametro y su mitad", nickname = "piRandom", notes = "", tags = {
            "piRandom ", })
    @ApiResponses(value = { @ApiResponse(code = 405, message = "Invalid input") })
    @RequestMapping(value= "/pi/",
    params = "random_limit" ,
    method = RequestMethod.GET)
    ResponseEntity<String> piRandom(@ApiParam(value = "Calcular un numero aletorio de decimales de pi , entre el parametro y su mitad", required=true) @Valid @RequestBody @RequestParam("random_limit") int random_limit  )
            throws InterruptedException, ExecutionException;

}