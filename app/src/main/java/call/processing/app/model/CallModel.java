package call.processing.app.model;


import java.util.ArrayList;

import org.json.JSONObject;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Getter
@Setter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CallModel {
    private String callId;

    private String accountId;
    private String accountName;

    private String interactionId;
    private String bridgeId;

    private String from;
    private String fromUri;
    private String to;
    private String toUri;

    private String callerIdName;
    private String callerIdNumber;

    private String request;
    private String requestNumber;
    private String dialedNumber;
    private String calleeIdNumber;

    private String direction;

    private String ownerId;
    private String queueId;
    private String overflowId;
    private String memberCallId;

    private String vcGroup;
    private String vcNumber;

    private Long createdTime;
    private Long answeredTime;
    private Long bridgedTime;
    private Long unbridgedTime;
    private Long destroyedTime;
    private Long totalDuration;
    private Long ringingDuration;
    private Long talkingDuration;

    private String presenceId;
    private String otherLegCallId;
    private String otherLegDirection;
    private String otherLegDestinationNumber;

    private ArrayList<String> mediaNames;
    private ArrayList<String> mediaRecordings;

    private String sipHangupDisposition;
    private String hangupCode;
    private String hangupCause;

    private boolean channelTransferor = false;
    private boolean channelTransferee = false; 

    private String state;

    private Long timestamp;

    private String node;

    private String freeswitch;

    private JSONObject cavs;
    private JSONObject cshs;

    private String zone;
    private String zoneWithAcdc;
    private String customerLevel;

    private boolean smartRouting = false;
    private String ipccCallId;
    private String blockResult = "";

    private String resourceType;

    private long holdCount = 0;
    private long holdDuration = 0;
    private boolean holding =false;
    private long muteCount = 0;
    private long muteDuration = 0;

    private boolean muting = false;

    private boolean wasQueued;
    private String firstQueueId;
    private Long firstQueuedTime;

}
