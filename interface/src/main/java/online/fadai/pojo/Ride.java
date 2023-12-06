package online.fadai.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Ride implements Serializable {
    private Long rideId;
    private Long carId;
    private String seatId;
    private String beginRoute;
    private String endRoute;
    private Integer rideStat;
}
