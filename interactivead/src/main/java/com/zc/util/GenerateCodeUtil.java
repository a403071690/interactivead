package com.zc.util;

import org.solar.generator.Generator;

public class GenerateCodeUtil {
//        static String dataBaseName="dsp_server";
    static String dataBaseName = "hudong";
    public static String jdbcUrl = "jdbc:mysql://rm-2zeq7f09ka2n496k2o.mysql.rds.aliyuncs.com:3306/" + dataBaseName + "?user=yuanweiwangluo&password=Beijing2017";
    public static String pojectPath = "D:\\mywork\\interactivead\\interactivead\\";//"/Users/xianchuanwu/Documents/众橙/codes/ssp/";
    public static String classPath = "D:\\mywork\\interactivead\\interactivead\\src/main/java/";//"/Users/xianchuanwu/Documents/众橙/codes/ssp/src/main/java/";
    static String packagePrefix = "com.gen.";
    public static String templateRootPath = "D:\\mywork\\interactivead\\interactivead\\src/main/resources/template/";//"/Users/xianchuanwu/Documents/众橙/codes/ssp/src/main/resources/template/";
    static String vueHtmlRootPath = "D:\\mywork\\interactivead\\interactivead\\src/main/webapp/static/html/template_gen/";//"/Users/xianchuanwu/Documents/众橙/codes/ssp/src/main/webapp/static/html/template_bak/";
    static String apiHtmlDocPath = pojectPath + "src/main/webapp/static/html/api_gen.html";

    public static void main(String[] args) throws Exception {
        Generator generator = new Generator(jdbcUrl, classPath, packagePrefix);

        //配置
        //generator.templateRootPath="/templatePath/";//自定义模版

/*       generator.vueHtmlRootPath = vueHtmlRootPath;
      generator.overWriteFile=true;
//        //生成//根据数据库生成实体文件
        generator.generatePackageAndBaseDaoAndBaseService();
        generator.generateBean();
        generator.generateDaoServiceControllerCode();
        generator.mapperXMLGenerator.fullTextSearchContainDate=true;
        generator.generateMybatisMapperXML();
//        //生成VUE的CRUD界面
          generator.generateVueWithElementUICode();
//        //生成api 文档
     generator.apiHtmlDocPath = apiHtmlDocPath;
      generator.apiHtmlDocHost = "localhost:8080/";
       generator.generateApiHtmlDoc();

        //生成数据库字段与备注的properties
        generator.dataBaseFieldDictPath = pojectPath + "src/main/resources/dataBaseFieldDict.properties";
       generator.generatorDataBaseFieldDict();*/


    }
}
