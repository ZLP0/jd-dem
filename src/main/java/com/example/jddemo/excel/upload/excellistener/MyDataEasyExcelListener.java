package com.example.jddemo.excel.upload.excellistener;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Scope("prototype")// 开启多例   单例存在并发安全问题 [使用如下]
/**
 *     @Resource
 *     private ObjectFactory<MyDataEasyExcelListener> obj;
 *
 *     使用: AbstractEasyExcelDataListener listener=obj.getObject();
 */
public class MyDataEasyExcelListener extends AbstractEasyExcelDataListener {

    @Override
    protected void validator(List<Object> row, StringBuilder msg) {
        // 数据校验
        Object number = row.get(1);
        if (!isNumber(number.toString(), 2)) {
            // System.out.println(number + "不是数字");
            msg.append(number + "不是数字");

            getExcelHelper().setHaveError(true);
        }
        //校验 。。。。。。。。。
        if (true) {

        }
    }

    /**
     * 保存数据库
     *
     * @param excelHelper
     */
    @Override
    protected void save(ExcelHelper excelHelper) {
        if(excelHelper.isHaveError()){
            System.out.println("校验数据有错误信息  不执行数据库操作");
            return;
        }
        List<List<Object>> content = excelHelper.getContent();
        System.out.println("保存的数据:"+content);
    }

    /**
     * 数字类型  最多保留两位小数
     *
     * @param str   校验的字符串
     * @param scale 保留小数位
     * @return
     */
    public static boolean isNumber(String str, int scale) {
        String reg = "^(\\d{1,8})(\\.\\d{1," + scale + "})?$";
        return str.matches(reg);

    }
}
