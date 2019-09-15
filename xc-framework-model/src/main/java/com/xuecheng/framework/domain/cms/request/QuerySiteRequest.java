package com.xuecheng.framework.domain.cms.request;

import com.xuecheng.framework.model.request.RequestData;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@Data
public class QuerySiteRequest extends RequestData {
    //站点id
    @ApiModelProperty("站点id")
    private String _Id;


    //页面名称
    @ApiModelProperty("站点名称")
    private String siteName;



}
