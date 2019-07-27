package com.example.demo.test;

import com.example.demo.pdf.PdfExportUtil;
import com.example.demo.templat.FtlStringBuilder;
import com.example.demo.test.pojo.ContractReqDTO;
import com.example.demo.utils.MapUtils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

/**
 * @author rudolf.
 * @date 2019/7/26.
 */
public class Demo {
    public static void main(String[] args) {
        // 合同模板需要组装的数据
        ContractReqDTO contractReqDTO = new ContractReqDTO();
        // 开始组装合同模板需要的数据
        // 甲方法人
        contractReqDTO.setNailPersonName("中心");
        // 乙方法人
        contractReqDTO.setAssigneePersonName("许昌");
        // 甲 方（转让方）
        contractReqDTO.setNailEnterpriseName("为我");
        // 乙 方（受让方）
        contractReqDTO.setAssigneeEnterpriseName("地方");
        // 承兑人全称
        contractReqDTO.setAcceptorFullName("大幅度发");
        // 票面金额
        contractReqDTO.setPrincipalAmount("1214578");
        // 票据号码
        contractReqDTO.setBillNumber("1245788956");
        // 汇票到期日
        contractReqDTO.setMoneyOrderEndTime("2015-12-03");
        // 背书人户名
        contractReqDTO.setEndorserEnterpriseName("大幅度发");
        // 被背书人户名
        contractReqDTO.setPassiveEndorserEnterpriseName("发给对方");
        // 成交价格    服务转让费为（乙方）时，成交价格=成交金额+服务费
        // 成交金额
        // 服务费
        // 计算后的成交价格
        contractReqDTO.setTransferPrice("1024");
        // 成交金额     服务转让费为（甲方）时，成交金额=成交价格-服务费
        contractReqDTO.setTransactionAmount("12458");
        contractReqDTO.setPoundage("96325");

        // 生成合同
        String template = FtlStringBuilder.processTextTemplateFromObject(template(), MapUtils.object2Map(contractReqDTO), "呵呵");
        System.out.println(template);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        PdfExportUtil.exportToFileOutputStream(bos, template);
        InputStream byteArrayInputStream = new ByteArrayInputStream(bos.toByteArray());
        try {
            File directory = new File(".");
            //获取当前路径
            File pdf = new File(directory.getCanonicalPath() + "\\contract\\" + UUID.randomUUID() + ".pdf");
            if (!pdf.exists()) {
                pdf.createNewFile();
            }
            writeToLocal(pdf.getPath(), byteArrayInputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * 模板文件
     * @return
     */
    public static String template() {
        String str = "<div>"
                + "    <p style='text-align: center;'><span style='font-size: 18pt;'><strong>票据应收款转让服务合同</strong></span></p>"
                + "    <p>甲 方（转让方）：${nailEnterpriseName!}</p>"
                + "    <p>法定代表人或负责人：${nailPersonName!}</p>"
                + "    <p>（甲方公章）</p>"
                + "    <p>乙 方（受让方）：${assigneeEnterpriseName!}</p>"
                + "    <p>法定代表人或负责人：${assigneePersonName!}</p>"
                + "    <p>（乙方公章）</p>"
                + "    <p>丙 方（信息平台方）：中投票据服务（深圳）有限公司</p>"
                + "    <p>（丙方公章）</p>"
                + "    <p class='X1 X5'><span class='X1 X5'>1、</span><span class='X1 X5'>会员</span></p>"
                + "    <p>根据《中华人民共和国合同法》、《中华人民共和国票据法》及相关法律法规的规定，甲乙丙三方在平等：</p>"
                + "    <p class='X1 X5'><span class='X1 X5'>2、</span><span class='X1 X5'>第一条 释义</span></p>"
                + "</div>";
        return str;
    }

    /**
     * 将InputStream写入本地文件
     *
     * @param destination 写入本地目录
     * @param input       输入流
     * @throws IOException IOException
     */
    public static void writeToLocal(String destination, InputStream input)
            throws IOException {
        int index;
        byte[] bytes = new byte[1024];
        FileOutputStream downloadFile = new FileOutputStream(destination);
        while ((index = input.read(bytes)) != -1) {
            downloadFile.write(bytes, 0, index);
            downloadFile.flush();
        }
        input.close();
        downloadFile.close();

    }

}
