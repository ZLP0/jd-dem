package com.example.jddemo.contracter;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Hashtable;
import java.util.List;

@Component
public class TerminateProtocol extends AbstractProtocol {


    public TerminateProtocol() {
        super(40);
    }

    @Override
    public void buildParams(BidContractDto param) {


        BidContractDto bidContract = new BidContractDto();

        bidContract.setMianContractCode(param.getMianContractCode());

        if (StringUtils.isEmpty(param.getContractName())) {
            System.out.println("协议名字为空");
            return;
        }
        bidContract.setContractName(param.getContractName());
        if (param.getSignType() == null) {
            System.out.println("签署方式为空");
            return;
        }
        bidContract.setSignType(param.getSignType());
        if (StringUtils.isEmpty(param.getOwner())) {
            System.out.println("合同管理员为空");
            return;
        }
        bidContract.setOwner(param.getOwner());
        if (StringUtils.isEmpty(param.getDescription())) {
            System.out.println("协议说明为空");
            return;
        }
        bidContract.setDescription(param.getDescription());
        if (param.getExpirationDate() == null) {
            System.out.println("协议截止日期为空");
            return;
        }
        bidContract.setExpirationDate(param.getExpirationDate());


        super.setAttachments(bidContract,"");

    }

    @Override
    public void preview() {
        super.preview();
    }
}
