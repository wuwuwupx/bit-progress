package com.bitprogress.cloudserver.model;

import com.bitprogress.basemodel.Info;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class RoutePublishDataInfo extends Info {

    /**
     * 配置数据ID
     */
    private String dataId;

    /**
     * 配置分组
     */
    private String group;

}
