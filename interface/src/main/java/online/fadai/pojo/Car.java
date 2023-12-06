package online.fadai.pojo;

import com.alibaba.fastjson2.JSON;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import online.fadai.pojo.carInfo.Seat;

import java.io.Serializable;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Car implements Serializable {
    private Long carId;
    private String route;
    private String carNum;
    private Integer open;
    private String openTime;
    private Map<String, Seat> seatInfo;

    public void setSeatInfo(String seatInfo) {
        this.seatInfo = JSON.parseObject(seatInfo, Map.class);
    }
}
