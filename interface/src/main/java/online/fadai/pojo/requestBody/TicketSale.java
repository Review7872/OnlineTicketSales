package online.fadai.pojo.requestBody;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TicketSale implements Serializable {
    private String carId;
    private String beginRoute;
    private String endRoute;
    private String seatId;
    private String cardId;
    private String phone;
}
