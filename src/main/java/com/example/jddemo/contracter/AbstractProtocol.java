package com.example.jddemo.contracter;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.PostConstruct;

public abstract class AbstractProtocol {

    private int type;

    public AbstractProtocol(int type) {
        this.type = type;
    }

    public void buildParams(BidContractDto param) {
    }

    public void preview() {

    }


    protected void setAttachments(BidContractDto param, String attachments)  {

        if (StringUtils.isNotBlank(attachments)) {


        }
    }

    protected void setVariableModels(BidContractDto param, String variableModels)  {

        if (StringUtils.isNotBlank(variableModels)) {
          /*  BidContractVariableDto[] models = JacksonUtil.parseJson(variableModels, BidContractVariableDto[].class);
            if (models.length > 0) {
                param.setWlContractVariableModels(Arrays.asList(models));
            }*/
        }
    }





    @PostConstruct
    public void initInstance() {

        ProtocolManager.getInstance().register(type, this);
    }
}
