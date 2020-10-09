package com.example.jddemo.contracter;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

@Component
public class ChangeProtocol extends AbstractProtocol {


    public ChangeProtocol() {
        super(30);
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
        if (StringUtils.isEmpty(param.getPartyA())) {
            System.out.println("甲方为空");
            return;
        }
        if (StringUtils.isEmpty(param.getPartyB())) {
            System.out.println("乙方为空");
            return;
        }
        if (StringUtils.isEmpty("")) {
            System.out.println("签约时间");
            return;
        }


        super.setAttachments(bidContract,"");

    }


    @Override
    public void preview() {
        super.preview();
    }

    void initObj() {

    }
}
