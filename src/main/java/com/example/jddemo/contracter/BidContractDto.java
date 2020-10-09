package com.example.jddemo.contracter;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class BidContractDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String carrierCode;

    private String carrierName;

    private String contractCode;

    private String contractName;

    private String mianContractCode;

    private String lccContractCode;

    private String ebsContractCode;

    private Integer contractType;

    private Integer contractStructure;

    private Integer contractSource;

    private Integer contractQuoteSource;

    private Integer quoteGenerateResult;

    private String quoteTemplateCode;

    private String quoteTemplateName;

    private String contractTemplateCode;

    private String contractTemplateName;

    private Integer contractStatus;

    private List<Integer> contractStatusList;

    private Integer verifyResult;

    private Integer supplyProtocolType;

    private String supplyContentType;

    private Date protocolEndTime;

    private String partyA;

    private String partyB;

    private Date effectiveDate;

    private Date expirationDate;

    private Integer signType;

    private Double oilPrice;

    private Integer oilPriceReact;

    private Double oilPriceRiseFrom;

    private Double oilPriceRiseTo;

    private String owner;

    private String description;

    private String protocolTerms;

    private String createUserCode;

    private String createUserName;

    private String updateUserCode;

    private String updateUserName;

    private Date createTime;

    private Date updateTime;

    private Integer yn;

    private Date ts;



    /**
     * 账户
     */
    private String jdAccount;

    /**
     * 主合同名称
     */
    private String mianContractName;

    //合同签署失败原因
    private String signFailReason;

    /**
     * 业务域：TNS(大网：运输标准网)，B2B
     */
    private String businessDomain;




    /**
     * 招采编码
     */
    private String bidCode;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCarrierCode() {
        return carrierCode;
    }

    public void setCarrierCode(String carrierCode) {
        this.carrierCode = carrierCode;
    }

    public String getCarrierName() {
        return carrierName;
    }

    public void setCarrierName(String carrierName) {
        this.carrierName = carrierName;
    }

    public String getContractCode() {
        return contractCode;
    }

    public void setContractCode(String contractCode) {
        this.contractCode = contractCode;
    }

    public String getContractName() {
        return contractName;
    }

    public void setContractName(String contractName) {
        this.contractName = contractName;
    }

    public String getMianContractCode() {
        return mianContractCode;
    }

    public void setMianContractCode(String mianContractCode) {
        this.mianContractCode = mianContractCode;
    }

    public String getMianContractName() {
        return mianContractName;
    }

    public void setMianContractName(String mianContractName) {
        this.mianContractName = mianContractName;
    }

    public String getLccContractCode() {
        return lccContractCode;
    }

    public void setLccContractCode(String lccContractCode) {
        this.lccContractCode = lccContractCode;
    }

    public String getEbsContractCode() {
        return ebsContractCode;
    }

    public void setEbsContractCode(String ebsContractCode) {
        this.ebsContractCode = ebsContractCode;
    }

    public Integer getContractType() {
        return contractType;
    }

    public void setContractType(Integer contractType) {
        this.contractType = contractType;
    }

    public Integer getContractStructure() {
        return contractStructure;
    }

    public void setContractStructure(Integer contractStructure) {
        this.contractStructure = contractStructure;
    }

    public Integer getContractSource() {
        return contractSource;
    }

    public void setContractSource(Integer contractSource) {
        this.contractSource = contractSource;
    }

    public Integer getContractQuoteSource() {
        return contractQuoteSource;
    }

    public void setContractQuoteSource(Integer contractQuoteSource) {
        this.contractQuoteSource = contractQuoteSource;
    }

    public Integer getQuoteGenerateResult() {
        return quoteGenerateResult;
    }

    public void setQuoteGenerateResult(Integer quoteGenerateResult) {
        this.quoteGenerateResult = quoteGenerateResult;
    }

    public String getQuoteTemplateCode() {
        return quoteTemplateCode;
    }

    public void setQuoteTemplateCode(String quoteTemplateCode) {
        this.quoteTemplateCode = quoteTemplateCode;
    }

    public String getQuoteTemplateName() {
        return quoteTemplateName;
    }

    public void setQuoteTemplateName(String quoteTemplateName) {
        this.quoteTemplateName = quoteTemplateName;
    }

    public String getContractTemplateCode() {
        return contractTemplateCode;
    }

    public void setContractTemplateCode(String contractTemplateCode) {
        this.contractTemplateCode = contractTemplateCode;
    }

    public String getContractTemplateName() {
        return contractTemplateName;
    }

    public void setContractTemplateName(String contractTemplateName) {
        this.contractTemplateName = contractTemplateName;
    }

    public Integer getContractStatus() {
        return contractStatus;
    }

    public void setContractStatus(Integer contractStatus) {
        this.contractStatus = contractStatus;
    }

    public List<Integer> getContractStatusList() {
        return contractStatusList;
    }

    public void setContractStatusList(List<Integer> contractStatusList) {
        this.contractStatusList = contractStatusList;
    }

    public Integer getVerifyResult() {
        return verifyResult;
    }

    public void setVerifyResult(Integer verifyResult) {
        this.verifyResult = verifyResult;
    }

    public Integer getSupplyProtocolType() {
        return supplyProtocolType;
    }

    public void setSupplyProtocolType(Integer supplyProtocolType) {
        this.supplyProtocolType = supplyProtocolType;
    }

    public String getSupplyContentType() {
        return supplyContentType;
    }

    public void setSupplyContentType(String supplyContentType) {
        this.supplyContentType = supplyContentType;
    }

    public Date getProtocolEndTime() {
        return protocolEndTime;
    }

    public void setProtocolEndTime(Date protocolEndTime) {
        this.protocolEndTime = protocolEndTime;
    }

    public String getPartyA() {
        return partyA;
    }

    public void setPartyA(String partyA) {
        this.partyA = partyA;
    }

    public String getPartyB() {
        return partyB;
    }

    public void setPartyB(String partyB) {
        this.partyB = partyB;
    }

    public Date getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(Date effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    public Integer getSignType() {
        return signType;
    }

    public void setSignType(Integer signType) {
        this.signType = signType;
    }

    public Double getOilPrice() {
        return oilPrice;
    }

    public void setOilPrice(Double oilPrice) {
        this.oilPrice = oilPrice;
    }

    public Integer getOilPriceReact() {
        return oilPriceReact;
    }

    public void setOilPriceReact(Integer oilPriceReact) {
        this.oilPriceReact = oilPriceReact;
    }

    public Double getOilPriceRiseFrom() {
        return oilPriceRiseFrom;
    }

    public void setOilPriceRiseFrom(Double oilPriceRiseFrom) {
        this.oilPriceRiseFrom = oilPriceRiseFrom;
    }

    public Double getOilPriceRiseTo() {
        return oilPriceRiseTo;
    }

    public void setOilPriceRiseTo(Double oilPriceRiseTo) {
        this.oilPriceRiseTo = oilPriceRiseTo;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getProtocolTerms() {
        return protocolTerms;
    }

    public void setProtocolTerms(String protocolTerms) {
        this.protocolTerms = protocolTerms;
    }

    public String getCreateUserCode() {
        return createUserCode;
    }

    public void setCreateUserCode(String createUserCode) {
        this.createUserCode = createUserCode;
    }

    public String getCreateUserName() {
        return createUserName;
    }

    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName;
    }

    public String getUpdateUserCode() {
        return updateUserCode;
    }

    public void setUpdateUserCode(String updateUserCode) {
        this.updateUserCode = updateUserCode;
    }

    public String getUpdateUserName() {
        return updateUserName;
    }

    public void setUpdateUserName(String updateUserName) {
        this.updateUserName = updateUserName;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getYn() {
        return yn;
    }

    public void setYn(Integer yn) {
        this.yn = yn;
    }

    public Date getTs() {
        return ts;
    }

    public void setTs(Date ts) {
        this.ts = ts;
    }

    public String getJdAccount() {
        return jdAccount;
    }

    public void setJdAccount(String jdAccount) {
        this.jdAccount = jdAccount;
    }


    public String getSignFailReason() {
        return signFailReason;
    }

    public void setSignFailReason(String signFailReason) {
        this.signFailReason = signFailReason;
    }


    public String getBusinessDomain() {
        return businessDomain;
    }

    public void setBusinessDomain(String businessDomain) {
        this.businessDomain = businessDomain;
    }

    public String getBidCode() {
        return bidCode;
    }

    public void setBidCode(String bidCode) {
        this.bidCode = bidCode;
    }
}