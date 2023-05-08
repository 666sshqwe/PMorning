package com.onlinetea.prower.mapper.dto;

import lombok.Data;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;


@Data
@TableName(value = "resource_mapping")
public class ResourceMappingPO {


    @TableId(value = "id")
    private Integer id;

    private String resource;


    private String resourceDesc;


    private String field;


    private String fieldDesc;

    private String mappingResource;


    private String mappingResourceDesc;


    private String mappingField;


    private String mappingFieldDesc;


    private Boolean primaryKey;


    private String sendStatus;


    private String tenantId;


    private String createdTime;


    private String createdBy;

    private String updatedTime;


    private String updatedBy;


    private String oriType;


    private String origin;

}

