package call.processing.app.processor;

import java.util.Arrays;
import java.util.List;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.Grouped;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KTable;
import org.apache.kafka.streams.kstream.Materialized;
import org.apache.kafka.streams.kstream.Produced;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.support.serializer.JsonSerde;
import org.springframework.stereotype.Component;

import call.processing.app.config.GsonSerde;
import call.processing.app.model.CallEvent;
import call.processing.app.model.ChannelEvent;


@Component
public class ChannelEventAggregator {
    @Autowired
    public void process(StreamsBuilder streamBuilder) {

        KStream<String, CallEvent> kzCallEventStream = streamBuilder.stream(Processor.INPUT_STREAM, Consumed.with(Serdes.String(), new GsonSerde<>(CallEvent.class)));
        kzCallEventStream.foreach((key, value)->{
            // System.out.println("ChannelEventAggregator(check): Key: " + key + ", Value: " + value);
            // System.out.println("---------------------------------------------------------------");
        });
        System.out.println();
        KTable<String, ChannelEvent> kzCallTable = kzCallEventStream
                //.filter((key, value) -> value.getCustomChannelVars() != null && value.getCustomChannelVars().getAccountID() != null)
                .flatMapValues(value -> flatMapForChannelDestroyEvent(value))
                .groupByKey(Grouped.with(Serdes.String(), new GsonSerde<>(CallEvent.class)))
                .aggregate(ChannelEvent::new,
                        (key, callEvent, aggregated) -> aggregate(callEvent, aggregated),
                        Materialized.as("ss").withValueSerde(new GsonSerde(ChannelEvent.class)).withCachingDisabled());

                        // Filter out null values and those without an accountId
        System.out.println("ChannelEventAggregator: " + kzCallTable.toString());
        KStream<String, ChannelEvent> outputStream = kzCallTable.toStream()
                .filter((key, value) -> value != null && value.getAccountId() != null);

        outputStream.to(Processor.OUTPUT_STREAM, Produced.with(Serdes.String(), new GsonSerde<>(ChannelEvent.class)));
        System.out.println("ChannelEventAggregator: Output stream sent to channel-events topic");
    }

    private ChannelEvent aggregate(CallEvent callEvt, ChannelEvent aggregated) {
        if (callEvt.getCallID() == null) return null; //tombstone
        return aggregated.with(callEvt);
    }

    private List<CallEvent> flatMapForChannelDestroyEvent(CallEvent value) {
        return value.isDestroyEvent() ? Arrays.asList(value, new CallEvent()) : Arrays.asList(value);
    }

    interface Processor {
        String INPUT_STREAM = "input-call-topic";
        String OUTPUT_STREAM = "output-call-topic";
    }
}
