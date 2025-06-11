package call.processing.app.model;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

import org.json.JSONObject;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@ToString
public class ChannelEvent {

    private Long createdTime;
    private String callId;
    private String accountId;
    private String accountName;
    private String interactionId;
    private String originationCallId;
    private String presenceId;
    private String presenceIdDb;
    private String bridgeId;
    private String otherLegCallId;
    private String otherLegDirection;
    private String otherLegDestinationNumber;
    private String callerIdName;
    private String callerIdNumber;
    private String from;
    private String fromUri;
    private String to;
    private String dialedNumber;
    private String request;
    private String requestNumber;
    private String calleeIdNumber;
    private String toUri;
    private String direction;
    private Long channelCreatedTimeMs;
    private String ownerId;
    private String channelLoopbackLeg;
    private String channelLoopbackOtherLegID;
    private String resourceType;
    private JSONObject cavs; // Custom audio/video variables
    private JSONObject cshs; // Custom channel hangup status
    private String zone;
    private String zoneWithAcdc;
    private String node;
    private String freeswitch;
    private String ivrCallId;
    private String ivrCallRequestNumber;
    private String sipHangupDisposition;
    private String hangupCode;
    private String hangupCause;
    private String queueId;
    private String overflowId;
    private String memberCallId;
    private String vcGroup;
    private String vcNumber;
    private String customerLevel;
    private String serviceVipCode;
    private boolean expertFlag;
    private String expertGroup;
    private boolean outboundFlags;
    private boolean smartRouting;
    private boolean smartRoutingExit;
    private State state;
    private String ipccCallId;
    private Long answeredTime;
    private Long bridgedTime;
    private Long unbridgedTime;
    private Long destroyedTime;
    private Long lastHoldTime;
    private Long lastUnholdTime;
    private boolean holding = false;
    private int holdCount = 0;
    private Integer totalDuration = 0;
    private Integer ringingDuration = 0;
    private Integer talkingDuration = 0;
    private Long holdDuration = 0L;
    private Long timestamp;
    private boolean channelTransferor = false;
    private boolean channelTransferee = false;
    private String referredBy;
    private String referredTo;
    private String mediaNames;
    private String mediaRecordings;
    private String firstQueueId;
    private Long firstQueuedTime;
    private boolean wasQueued = false;
    private String blockResult = "";




    public enum State {
        CREATED, ANSWERED, BRIDGED, UNBRIDGED, HELD, UNHELD, QUEUED, DESTROYED
    }

       public ChannelEvent with(CallEvent kzCall) {
        long eventTimeMs = kzCall.getChannelCreatedTimeMs();
        CustomChannelVars ccv = kzCall.getCcvs();
        if (ccv == null) {
            return this;
        }

        createdTime = createdTime != null ? createdTime : kzCall.getChannelCreatedTimeMs();
        callId = callId == null ? kzCall.getCallID() : callId;
        accountId = accountId == null ? ccv.getAccountID() : accountId;
        accountName = accountName == null ? ccv.getAccountName() : accountName;
        interactionId = interactionId == null ? ccv.getCallInteractionID() : interactionId;
        originationCallId = originationCallId == null ? kzCall.getOriginationCallID() : originationCallId;

        presenceId = presenceId != null ? presenceId : (kzCall.getPresenceID() != null ? kzCall.getPresenceID() : ccv.getPresenceID());
        presenceIdDb = presenceId != null ? presenceId.split("@")[0] : null;

        bridgeId = ccv.getBridgeID();
        otherLegCallId = kzCall.getOtherLegCallID();
        otherLegDirection = kzCall.getOtherLegDirection();
        otherLegDestinationNumber = kzCall.getOtherLegDestinationNumber();

        callerIdName = Optional.ofNullable(callerIdName).orElse(kzCall.getCallerIDName());
        callerIdNumber = Optional.ofNullable(callerIdNumber).orElse(kzCall.getCallerIDNumber());

        from = kzCall.getFrom();
        fromUri = kzCall.getFromUri();
        to = (to == null || to.equals("") || to.contains("nouser")) ? kzCall.getTo() : to;
        dialedNumber = to != null ? to.split("@")[0] : Optional.ofNullable(calleeIdNumber).orElse(kzCall.getCalleeIDNumber());
        request = kzCall.getRequest();
        requestNumber = request != null ? request.split("@")[0] : null;
        calleeIdNumber = requestNumber;
        toUri = kzCall.getToUri();
        direction = direction == null ? kzCall.getCallDirection() : direction;

        ownerId = ownerId == null ? ccv.getOwnerID() : ownerId;

        resourceType = ccv.getResourceType();
        channelLoopbackLeg = kzCall.getChannelLoopbackLeg();
        channelLoopbackOtherLegID = kzCall.getChannelLoopbackOtherLegID();

        if (ccv.getOwnerID() != null) { // channel belong to agent
            queueId = ccv.getQueueID();
            overflowId = ccv.getOverflowID();
            memberCallId = ccv.getMemberCallID();
            vcGroup = ccv.getVcGroup();
            vcNumber = ccv.getVcNumber();
        }

        this.cavs = kzCall.getCavs();
        this.cshs = kzCall.getCshs();

        this.zone = ccv.getApplicationZone();
        this.zoneWithAcdc = ccv.getZoneWithAcdc();
        this.node = kzCall.getNode();
        this.freeswitch = kzCall.getSwitchNodename();

        this.customerLevel = ccv.getCustomerLevel();
        this.serviceVipCode = ccv.getServiceVipCode();

        ivrCallId = Optional.ofNullable(ivrCallId).orElse(kzCall.getIVRCallId());
        ivrCallRequestNumber = Optional.ofNullable(ivrCallRequestNumber).orElse(kzCall.getIVRCallRequestNumber());

        expertFlag = ccv.getExpertFlag() != null ? ccv.getExpertFlag() : false;
        expertGroup = ccv.getExpertGroup() != null ? ccv.getExpertGroup() : null;
        outboundFlags = ccv.getOutboundFlags() != null ? ccv.getOutboundFlags() : false;
        smartRouting = kzCall.isSmartRouting();
        smartRoutingExit = kzCall.isSmartRoutingExit() ;

        if (kzCall.isCreateEvent()) {
            createdTime = createdTime != null ? createdTime : eventTimeMs;
            state = State.CREATED;
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
            String createDateStr = dateFormat.format(new Date(createdTime));
            resourceType = "OFFNET_ORIGINATION";
            if ("inbound".equals(direction.toLowerCase())) {
                if ("OFFNET_TERMINATION".toLowerCase().equals(resourceType.toLowerCase())) {
                    String callIdNumber = calleeIdNumber.length() > 5 ? calleeIdNumber.substring(0, 5) : calleeIdNumber;
                    ipccCallId = createDateStr + "-OUTBOUND-" + freeswitch + "-" + ccv.getUsername() + "-" + presenceIdDb + "-" + callIdNumber;
                } else {
                    String callIdNumber = callerIdNumber.length() > 5 ? callerIdNumber.substring(0, 5) : callerIdNumber;
                    ipccCallId = createDateStr + "-" + callId.substring(0, 5) + "-" + callIdNumber;
                }
            }
        }

        if (kzCall.isAnswerEvent()) {
            answeredTime = eventTimeMs;
            state = State.ANSWERED;
            /*if (state == State.QUEUED) {
                //neu dang QUEUED thi khong set state ve ANSWERED va can giu nguyen timestamp
                eventTimeMs = this.timestamp;
            } else {
                state = State.ANSWERED;
            }*/
        }

        if (kzCall.isBridgeEvent()) {
            bridgedTime = eventTimeMs;
            state = State.BRIDGED;
        }

        if (kzCall.isUnbridgeEvent()) {
            unbridgedTime = eventTimeMs;
            state = State.UNBRIDGED;
        }

        if (kzCall.isHoldEvent()) {
            if (!holding) {
                holdCount++;
            }
            holding = true;
            lastHoldTime = eventTimeMs;
            lastUnholdTime = null;
            state = State.HELD;
        }

        if (kzCall.isUnholdEvent()) {
            lastUnholdTime = eventTimeMs;
            if (holding && lastHoldTime != null && lastUnholdTime - lastHoldTime > 0) {
                holdDuration += (lastUnholdTime - lastHoldTime) / 1000;
            }
            holding = false;
            state = State.UNHELD;
        }

        if (kzCall.isChannelQueueEvent()) {
            wasQueued = true;
            state = State.QUEUED;

            if (firstQueueId == null) {
                if(kzCall.getIcvs() != null) {
                    firstQueueId = (String) kzCall.getIcvs().getString("CHANNEL_QUEUE");
                    firstQueuedTime = eventTimeMs;
                }
                
            }
        }

        if (kzCall.isTransferorEvent()) {
            channelTransferor = true;
        }

        if (kzCall.isTransfereeEvent()) {
            channelTransferee = true;
            referredBy = ccv.getReferredBy();
            referredTo = ccv.getReferredTo();
        }

        if (kzCall.isDestroyEvent()) {
            destroyedTime = eventTimeMs;
            mediaNames = ccv.getMediaNames();
            mediaRecordings = ccv.getMediaRecordings();
            sipHangupDisposition = kzCall.getSipHangupDisposition();
            hangupCode = kzCall.getHangupCode();
            hangupCause = kzCall.getHangupCause();
            state = State.DESTROYED;
        }

        this.totalDuration = createdTime != null && destroyedTime != null ? Long.valueOf(destroyedTime / 1000).intValue() - Long.valueOf(createdTime / 1000).intValue() : null;
        this.ringingDuration = createdTime != null && answeredTime != null ? Long.valueOf(answeredTime / 1000).intValue() - Long.valueOf(createdTime / 1000).intValue() : null;
        this.talkingDuration = answeredTime != null && destroyedTime != null ? Long.valueOf(destroyedTime / 1000).intValue() - Long.valueOf(answeredTime / 1000).intValue() : null;
        this.timestamp = eventTimeMs;
        return this;
    }
    
}
