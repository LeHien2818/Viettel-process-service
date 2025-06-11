package call.processing.app.model;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class CshsModel {
    private String X_AUTH_PORT;
    private String X_AUTH_IP;
    private String P_Early_Media;
    private String P_Charging_Vector;
    private String X_KAZOO_INVITE_FORMAT;
    private String X_KAZOO_AOR;
    private String MYCC_VIDEO;
    private String MYCC_QUEUE;
    private String MYCC_MEMBER_CALL_ID;
    private String MYCC_HIDE_CALLER_ID;
}
