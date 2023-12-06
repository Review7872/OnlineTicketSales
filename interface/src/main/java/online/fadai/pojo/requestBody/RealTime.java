package online.fadai.pojo.requestBody;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RealTime implements Serializable {
    private String carId;
    private String route;
    private String time;
}
