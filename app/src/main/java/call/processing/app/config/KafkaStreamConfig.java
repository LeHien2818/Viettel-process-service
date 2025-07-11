package call.processing.app.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafkaStreams;
import org.springframework.kafka.config.KafkaStreamsConfiguration;

@Configuration
@EnableKafkaStreams
public class KafkaStreamConfig {
    @Value("${kafka.broker.address}")
    private String kafkaAddress;

    @Bean
    public KafkaStreamsConfiguration defaultKafkaStreamsConfig() {
        String kafkaConnectionServer = "localhost:9092,localhost:9093";
        if(kafkaAddress != null){
            if(!kafkaAddress.equals(kafkaConnectionServer) &&
                !kafkaAddress.equals("default")){
                kafkaConnectionServer = kafkaAddress;
            }
        }
        
        final Map<String, Object> props = new HashMap<>();
        props.put(StreamsConfig.APPLICATION_ID_CONFIG, "call-app");
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaConnectionServer);
        props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, GsonSerde.class);

        return new KafkaStreamsConfiguration(props);
    }

}