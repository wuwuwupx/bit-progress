package com.bitprogress.quartz;

import com.bitprogress.model.quartzjob.pojo.envm.TriggerStateEnum;
import lombok.Data;

/**
 * @author wpx
 */
@Data
public class TriggerStateItem {

    private String name;

    private TriggerStateEnum triggerState;

}
