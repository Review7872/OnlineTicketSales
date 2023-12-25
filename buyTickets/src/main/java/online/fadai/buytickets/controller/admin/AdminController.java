package online.fadai.buytickets.controller.admin;

import online.fadai.pojo.requestBody.InsertCar;
import online.fadai.service.CarService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("admin")
@CrossOrigin(origins = "*")
public class AdminController {
    @DubboReference
    private CarService carService;

    @PostMapping("createCar")
    public Object createCar(@RequestHeader(value = "Authorization",defaultValue = "NotJWT") String jwt,
                            @RequestBody InsertCar insertCar) {
        return carService.insertCar(insertCar.getRouteAndTime(), insertCar.getSeats(),
                insertCar.getCarNum(), insertCar.getOpen(), insertCar.getOpenTime());
    }
}
