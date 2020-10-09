package com.example.jddemo.contracter;


import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
@Component
public class SupplementProtocol extends AbstractProtocol {

    public SupplementProtocol() {
        super(10);
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

        if (StringUtils.isEmpty(param.getSupplyContentType())) {
            System.out.println("补充内容为空");
            return;
        }

        String[] supplyContentType = param.getSupplyContentType().split(",");

        for (int i = 0; i < supplyContentType.length; i++) {

            //校验条款
            Integer contractStructure = param.getContractStructure();
            bidContract.setContractStructure(contractStructure);
            if (contractStructure == 1) {
                bidContract.setContractTemplateCode(param.getContractTemplateCode());

            } else {
                bidContract.setProtocolTerms(param.getProtocolTerms());
            }

            //校验 报价

            //校验 油价

            //校验时间
        }

        bidContract.setMianContractCode(param.getMianContractCode());
        bidContract.setContractName(param.getContractName());

        bidContract.setSupplyProtocolType(param.getSupplyProtocolType());
        bidContract.setSupplyContentType(param.getSupplyContentType());

        bidContract.setSignType(param.getSignType());
        bidContract.setOwner(param.getOwner());
        bidContract.setDescription(param.getDescription());

        if(1==bidContract.getContractStructure()) //标准结构
        {
            super.setVariableModels(bidContract,"");
        }
        super.setAttachments(bidContract,"");

    }

    @Override
    public void preview() {
        super.preview();
    }



}
