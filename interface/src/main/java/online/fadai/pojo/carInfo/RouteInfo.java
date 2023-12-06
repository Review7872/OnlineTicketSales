package online.fadai.pojo.carInfo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class RouteInfo implements Serializable {
    private String route;
    private String predictedTime;
    private String realTime;
    private HashMap<String, Map<String, String>> seatInfo;
}
