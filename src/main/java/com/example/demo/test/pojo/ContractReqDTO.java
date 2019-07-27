package com.example.demo.test.pojo;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
* @Description:    收款转让服务合同
* @Author:         gengzeqing
* @CreateDate:     2019/7/16 16:28
* @Version:        1.0
*/
@Getter
@Setter
public class ContractReqDTO implements Serializable {

    /**
     * 甲 方（转让方） 公司名称
     */
    private String nailEnterpriseName;

    /**
     * 甲 方 法定代表人或负责人
     */
    private String nailPersonName;

    /**
     * 乙 方（受让方） 公司名称
     */
    private String assigneeEnterpriseName;

    /**
     * 乙 方 法定代表人或负责人
     */
    private String assigneePersonName;

    /**
     *  丙 方（信息平台方）
     */
    private String informationPlatformEnterpriseName;

    /**
     * 承兑人全称
     */
    private String acceptorFullName;

    /**
     * 票面金额
     */
    private String principalAmount;

    /**
     * 票据号码
     */
    private String billNumber;

    /**
     * 汇票到期日
     */
    private String moneyOrderEndTime;

    /**
     * 背书人信息（转让方）
     */
 //   private String endorserMsg;

    /**
     * 背书人户名：A有限公司
     */
    private String endorserEnterpriseName;

    /**
     * 被背书人信息（受让方）
     */
//    private String passiveEndorserMsg;

    /**
     * 被背书人户名：B有限公司
     */
    private String passiveEndorserEnterpriseName;

    /**
     * 成交价格
     */
    private String transferPrice;

    /**
     * 转让服务费（甲方）
     */
    private String poundage;

    /**
     * 成交金额
     */
    private String transactionAmount;


}
