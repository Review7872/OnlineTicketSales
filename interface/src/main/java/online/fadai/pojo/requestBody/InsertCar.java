package online.fadai.pojo.requestBody;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import online.fadai.pojo.carInfo.Seat;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InsertCar implements Serializable {
    private List<Map<String, String>> routeAndTime;
    private Map<String, Seat> seats;
    private String carNum;
    private int open;
    private String openTime;
}
