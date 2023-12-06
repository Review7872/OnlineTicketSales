package online.fadai.ride.config;

import online.fadai.ride.utils.SnowflakeIdGenerator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class Config {
    @Value("${snow.beginTime}")
    private long beginTime;
    @Value("${snow.serverId}")
    private long serverId;
    @Value("${snow.hostId}")
    private long hostId;

    @Bean
    public SnowflakeIdGenerator getSnow() {
        return new SnowflakeIdGenerator(serverId, hostId, beginTime);
    }
}
