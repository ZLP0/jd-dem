package com.example.jddemo.java8lambda;

import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @Classname App
 * @Description
 * @Author
 * @Date 2020/10/23
 * @Version 1.0
 **/
public class App {


    static  ArrayList<Company> companyList= new ArrayList<>();
    static {
        Company company = new Company(101,"阿里");
        Company company2 = new Company(101,"阿里2");
        Company company3 = new Company(102,"小米");
        companyList.add(company);
        companyList.add(company2);
        companyList.add(company3);

    }
    public static void main(String[] args) {
        companyList.forEach(obj->{
            System.out.println(obj.getName());
        });
        //code  分组
        Map<Integer, List<Company>> collect = companyList.stream().collect(Collectors.groupingBy(Company::getCode));
        //名字   分组
        Map<String, List<Company>> collect1 = companyList.stream().collect(Collectors.groupingBy(Company::getName));


        //多条件分组  方式1
        HashMap<String, List<Company>> mapList = companyList.stream().collect(Collectors.groupingBy(obj -> obj.getCode() + "_" + obj.getName(), HashMap::new, Collectors.toList()));

        //多条件分组  方式2   key  为对象方式
        HashMap<GroupByParamDto, List<Company>> mapList2 = companyList.stream().collect(Collectors.groupingBy(d -> new GroupByParamDto(d.getCode(), d.getName()), HashMap::new, Collectors.toList()));

        List<Company> companyListNew = companyList.stream().map(company -> {
            Company companyNew = new Company();
            BeanUtils.copyProperties(company,companyNew);
            return companyNew;
        }).collect(Collectors.toList());

        System.out.println(companyListNew);

    }
}
