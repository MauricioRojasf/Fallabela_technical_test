package mauricioRojas.falabellatechtest.tech.api;

import java.util.concurrent.ExecutionException;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import io.swagger.annotations.ApiParam;
import mauricioRojas.falabellatechtest.tech.service.PiRandomService;

@Controller
public class PiRandomApiController implements PiRandomApi {

    @Autowired
    private PiRandomService PiRandomService;

    @Override
    public ResponseEntity<String> piRandom(@ApiParam(value = "Calcular un numero aletorio de decimales de pi , entre el parametro y su mitad", required=true) @Valid @RequestBody @RequestParam("random_limit") int random_limit  )
            throws InterruptedException, ExecutionException {
        return new ResponseEntity<String>(PiRandomService.piRandom(random_limit),HttpStatus.OK);
    }

}
