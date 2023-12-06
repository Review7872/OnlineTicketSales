package online.fadai.pojo.carInfo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Seat implements Serializable {
    private String name;
    private Integer total;
    private Integer money;
}
