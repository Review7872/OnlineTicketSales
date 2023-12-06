package online.fadai.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order implements Serializable {
    private Long orderId;
    private Long cardId;
    private Long rideId;
    private Long payId;
    private String orderTime;
    private String phone;
}
