package com.generator;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.sun.javafx.PlatformUtil;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class CodeGeneratorChange {

    /**
     * modular 名字
     */
    public static final String MODULAR_NAME = "";

    /**
     * 基本路径
     */
    public static final String SRC_MAIN_JAVA = "src/main/java/";

    /**
     * 作者
     */
    public static final String AUTHOR = "CodeGenerator";

    /**
     * 是否是 rest 接口
     */
    private static final boolean REST_CONTROLLER_STYLE = true;

    public static final String JDBC_MYSQL_URL = "jdbc:mysql://127.0.0.1:3306/test";

    public static final String JDBC_DRIVER_NAME = "com.mysql.jdbc.Driver";

    public static final String JDBC_USERNAME = "root";

    public static final String JDBC_PASSWORD = "945319791";

    /**
     * 项目根目录
     */
    public static final String projectPath = "D:\\sunline\\auto_workspace\\auto-project";
    /**
     * 代码生成位置
     */
    public static final String PARENT_NAME = "com.project";

    public static void main(String[] args) {
        String moduleName = scanner("模块名");
        String tableName = scanner("表名");
        String tablePrefix = scanner("表前缀(无前缀输入#)").replaceAll("#", "");
        autoGenerator(moduleName, tableName, tablePrefix);
    }

    private static String scanner(String tip) {
        Scanner scanner = new Scanner(System.in);
        StringBuilder sb = new StringBuilder();
        sb.append("please input " + tip + " : ");
        System.out.println(sb.toString());
        if (scanner.hasNext()) {
            String ipt = scanner.next();
            if (StringUtils.isNotEmpty(ipt)) {
                return ipt;
            }
        }
        throw new MybatisPlusException("please input the correct " + tip + ". ");
    }

    public static void autoGenerator(String moduleName, String tableName, String tablePrefix) {
        new AutoGenerator()
                //1.全局变量配置
                .setGlobalConfig(getGlobalConfig())
                //2.数据源配置
                .setDataSource(getDataSourceConfig())
                //3.策略配置
                .setStrategy(getStrategyConfig(tableName, tablePrefix))
                //4.包名策略配置（生成文件路径）
                .setPackageInfo(getPackageConfig(moduleName))
                //5.输出指定路径文件
                .setCfg(getInjectionConfig(moduleName))
                //6.使用模板
                .setTemplate(getTemplateConfig())
                .execute();
    }

    //1.全局变量配置
    private static GlobalConfig getGlobalConfig() {
        String filePath = projectPath + "/" + MODULAR_NAME + SRC_MAIN_JAVA;
        if (PlatformUtil.isWindows()) {
            filePath = filePath.replaceAll("/+|\\\\+", "\\\\");
        } else {
            filePath = filePath.replaceAll("/+|\\\\+", "/");
        }
        return new GlobalConfig()
                .setOutputDir(filePath)
                .setDateType(DateType.ONLY_DATE)
                .setIdType(IdType.UUID)
                .setAuthor(AUTHOR)
                .setServiceName("%sService")//去Service的I前缀
                .setBaseColumnList(true)
                .setSwagger2(true)
                .setEnableCache(false)
                .setBaseResultMap(true)
                .setOpen(false);
    }

    //2.数据源配置
    private static DataSourceConfig getDataSourceConfig() {
        return new DataSourceConfig()
                .setUrl(JDBC_MYSQL_URL)
                .setDriverName(JDBC_DRIVER_NAME)
                .setUsername(JDBC_USERNAME)
                .setPassword(JDBC_PASSWORD);
    }

    //3.策略配置
    private static StrategyConfig getStrategyConfig(String tableName, String tablePrefix) {
        return new StrategyConfig()
                .setNaming(NamingStrategy.underline_to_camel)
                .setColumnNaming(NamingStrategy.underline_to_camel)
                .setInclude(tableName)
                .setRestControllerStyle(REST_CONTROLLER_STYLE)
                .setEntityBuilderModel(true)
                .setControllerMappingHyphenStyle(true)
                .setEntityTableFieldAnnotationEnable(true)
                .setSuperControllerClass("BaseController")
                .setTablePrefix(tablePrefix + "_");
    }

    //4.包名策略配置（生成文件路径）
    private static PackageConfig getPackageConfig(String moduleName) {
        return new PackageConfig()
                .setParent(PARENT_NAME)
//                .setModuleName(moduleName)
                .setService("service."+moduleName)
                .setServiceImpl("service."+moduleName+".impl")
                .setController("controller."+moduleName)
                .setEntity("entity."+moduleName);
    }

    //5.输出指定路径文件
    private static InjectionConfig getInjectionConfig(final String moduleName) {
        return new InjectionConfig() {
            @Override
            public void initMap() {
                //在模板中用${cfg.xx}去接收
                Map map = new HashMap();
                map.put("dateTime", getDateTime());
                map.put("test", "123321");
                setMap(map);
                List<FileOutConfig> fileOutConfigList = new ArrayList<FileOutConfig>();
                // 自定义配置会被优先输出
                fileOutConfigList.add(new FileOutConfig("/temp/mapper.xml.vm") {
                    @Override
                    public String outputFile(TableInfo tableInfo) {
                        // 自定义输出文件名，如果entity设置了前后缀，此次注意xml的名称也会跟着发生变化
                        return projectPath + "/src/main/resources/mybatis/" +
                                moduleName + "/" + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
                    }
                });
                setFileOutConfigList(fileOutConfigList);
            }
        };
    }

    //6.使用模板
    private static TemplateConfig getTemplateConfig() {
//        return new TemplateConfig()
//                .setController("/templates-generator/controller.java.vm")
//                .setService("/templates-generator/service.java.vm")
//                .setServiceImpl("/templates-generator/serviceImpl.java.vm")
//                .setEntity("/templates-generator/entity.java.vm")
//                .setMapper("/templates-generator/mapper.java.vm")
//                .setXml("/templates-generator/mapper.xml.vm");
        return new TemplateConfig()
                .setController("/temp/controller.java.vm")
                .setService("/templates-generator/service.java.vm")
                .setServiceImpl("/templates-generator/serviceImpl.java.vm")
                .setEntity("/templates-generator/entity.java.vm")
                .setMapper("/templates-generator/mapper.java.vm")
                .setXml("/temp/mapper.xml.vm");
    }

    private static String getDateTime() {
        LocalDateTime localDate = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return localDate.format(formatter);
    }

}

