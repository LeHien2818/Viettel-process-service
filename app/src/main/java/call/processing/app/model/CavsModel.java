package call.processing.app.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class CavsModel {
    private String voiceChannelNumber;
    private String cf_route_id;
    private String cf_route_request;
    private String distribute;
    private String channel;
    private String IPCCBLACKLIST_1068;
    private String member_customer_level;
    private String originalVoiceChannelNumber;
}
