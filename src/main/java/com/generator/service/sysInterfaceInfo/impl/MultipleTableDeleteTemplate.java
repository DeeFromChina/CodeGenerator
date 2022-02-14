package com.generator.service.sysInterfaceInfo.impl;

import com.dee.frame.springbootframe.util.UnderlineToCamelUtils;
import com.dee.frame.springbootframe.util.common.BaseUtil;
import com.dee.frame.springbootframe.util.excel.ExcelCell;
import com.dee.frame.springbootframe.util.excel.ExcelConfig;
import com.dee.frame.springbootframe.util.excel.ExcelUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MultipleTableDeleteTemplate {

    private static String createFilePath(String tableName) {
        String path = System.getProperty("user.dir");
        String filePath = path + File.separator + tableName + "_tables_del" + ".xlsx";
        return filePath;
    }

    private static List<String> createSheetNames() {
        List<String> sheetNames = new ArrayList<String>();
        sheetNames.add("页面参数处理");
        sheetNames.add("数据处理");
        return sheetNames;
    }

    public static void createTemplate(String tableName, String[] subTables, List<Map<String, Object>> mainTableColumnList, List<List<Map<String, Object>>> subTableList) throws Exception {
        List<List<ExcelCell>> list = new ArrayList<>();
        List<ExcelCell> cells1 = createSheet1Template(tableName, mainTableColumnList, subTableList);
        list.add(cells1);
        List<ExcelCell> cells2 = createSheet2Template(tableName, subTables, mainTableColumnList, subTableList);
        list.add(cells2);

        ExcelConfig excelConfig = new ExcelConfig();
        excelConfig.setFilePath(createFilePath(tableName));
        excelConfig.setSheetNames(createSheetNames());
        ExcelUtil.writeToExcelByExcelCell(list, excelConfig);
    }

    private static List<ExcelCell> createSheet1Template(String tableName, List<Map<String, Object>> mainTableColumnList, List<List<Map<String, Object>>> subTableList) throws Exception {
        List<ExcelCell> cells = new ArrayList<ExcelCell>();
        createSheet1TemplateHeader(tableName, cells);
        createSheet1TemplateContentTitle(cells);
        createSheet1TemplateContent(mainTableColumnList, subTableList, cells);
        return cells;
    }

    private static List<ExcelCell> createSheet2Template(String tableName, String[] subTables, List<Map<String, Object>> mainTableColumnList, List<List<Map<String, Object>>> subTableList) throws Exception {
        List<ExcelCell> cells = new ArrayList<ExcelCell>();
        createSheet2TemplateContentTitle(cells);
        createSheet2TemplateContent(tableName, subTables, mainTableColumnList, subTableList, cells);
        return cells;
    }

    /**
     * 创建模板头部
     * @throws Exception
     */
    private static void createSheet1TemplateHeader(String tableName, List<ExcelCell> cells) throws Exception {
        //第一列
        String[] firstRow = {"接口名称", "系统名", "模块名", "方法名", "类型"};
        //第二列
        String[] secondRow = {tableName, "", UnderlineToCamelUtils.underlineToCamel(tableName, true), "delBeans", "delete"};

        for(int i = 0; i < firstRow.length; i++){
            ExcelCell cellTitle = new ExcelCell();
            cellTitle.setRowNum(i);
            cellTitle.setColNum(0);
            cellTitle.setCellValue(firstRow[i]);
            cells.add(cellTitle);

            ExcelCell cell = new ExcelCell();
            cell.setRowNum(i);
            cell.setColNum(1);
            cell.setCellValue(secondRow[i]);
            cells.add(cell);
        }
    }

    private static void createSheet1TemplateContentTitle(List<ExcelCell> cells) throws Exception {
        String[] contentTitles = new String[]{"字段编码", "字段描述", "是否必填", "字段长度", "字段来源", "字段类型", "校验规则", "加工规则", "字段别名", "父级节点"};
        for(int i = 0; i < contentTitles.length; i++){
            ExcelCell cell = new ExcelCell();
            cell.setRowNum(6);
            cell.setColNum(i);
            cell.setCellValue(contentTitles[i]);
            cells.add(cell);
        }
    }

    private static void createSheet1TemplateContent(List<Map<String, Object>> mainTableColumnList, List<List<Map<String, Object>>> subTableList, List<ExcelCell> cells) throws Exception {
        int row = 7;
        createSheet1MainTable(mainTableColumnList, cells);
//        row += mainTableColumnList.size();
//        createSheet1SubTable(row, subTableList, cells);
    }

    private static void createSheet1MainTable(List<Map<String, Object>> mainTableColumnList, List<ExcelCell> cells) throws Exception {
        int row = 7;
        for(Map<String, Object> tableColumn : mainTableColumnList){
            if(!BaseUtil.returnString(tableColumn.get("columnName")).equals("id")){
                continue;
            }
            //页面字段编码
            ExcelCell pageColumnCode = new ExcelCell();
            pageColumnCode.setRowNum(row);
            pageColumnCode.setColNum(0);
            pageColumnCode.setCellValue(UnderlineToCamelUtils.underlineToCamel(BaseUtil.returnString(tableColumn.get("columnName")), true));
            cells.add(pageColumnCode);

            //页面字段描述
            ExcelCell pageColumnName = new ExcelCell();
            pageColumnName.setRowNum(row);
            pageColumnName.setColNum(1);
            pageColumnName.setCellValue(BaseUtil.returnString(tableColumn.get("columnDesc")));
            cells.add(pageColumnName);

            //是否必填
            ExcelCell isRequired = new ExcelCell();
            isRequired.setRowNum(row);
            isRequired.setColNum(2);
            if(!BaseUtil.returnString(tableColumn.get("isNull")).equals("")){
                isRequired.setCellValue("是");
            }else{
                isRequired.setCellValue("否");
            }
            cells.add(isRequired);

            //字段最大长度
            ExcelCell maxLength = new ExcelCell();
            maxLength.setRowNum(row);
            maxLength.setColNum(3);
            maxLength.setCellValue(BaseUtil.returnString(tableColumn.get("columnLength")));
            cells.add(maxLength);

            //字段来源
            ExcelCell columnSource = new ExcelCell();
            columnSource.setRowNum(row);
            columnSource.setColNum(4);
            columnSource.setCellValue("页面");
            cells.add(columnSource);

            //字段类型
            ExcelCell pageColumnType = new ExcelCell();
            pageColumnType.setRowNum(row);
            pageColumnType.setColNum(5);
            pageColumnType.setCellValue(BaseUtil.returnString(tableColumn.get("dataType")));
            cells.add(pageColumnType);

            //校验规则
            ExcelCell validateRule = new ExcelCell();
            validateRule.setRowNum(row);
            validateRule.setColNum(6);
            validateRule.setCellValue("");
            cells.add(validateRule);

            //加工规则
            ExcelCell processingRule = new ExcelCell();
            processingRule.setRowNum(row);
            processingRule.setColNum(7);
            processingRule.setCellValue("");
            cells.add(processingRule);

            //页面字段别名
            ExcelCell columnAliasCode = new ExcelCell();
            columnAliasCode.setRowNum(row);
            columnAliasCode.setColNum(8);
            columnAliasCode.setCellValue(UnderlineToCamelUtils.underlineToCamel(BaseUtil.returnString(tableColumn.get("columnName")), true));
            cells.add(columnAliasCode);

            //父级节点
            ExcelCell fatherNodeCode = new ExcelCell();
            fatherNodeCode.setRowNum(row);
            fatherNodeCode.setColNum(9);
            fatherNodeCode.setCellValue("");
            cells.add(fatherNodeCode);
            row++;
        }
    }

    private static void createSheet1SubTable(int row, List<List<Map<String, Object>>> subTableList, List<ExcelCell> cells) throws Exception {
        int i = 0;
        for(List<Map<String, Object>> subTableColumnList : subTableList){
            for(Map<String, Object> tableColumn : subTableColumnList){
                if(BaseUtil.returnString(tableColumn.get("columnName")).equals("id")){
                    continue;
                }
                //页面字段编码
                ExcelCell pageColumnCode = new ExcelCell();
                pageColumnCode.setRowNum(row);
                pageColumnCode.setColNum(0);
                pageColumnCode.setCellValue(UnderlineToCamelUtils.underlineToCamel(BaseUtil.returnString(tableColumn.get("columnName")), true));
                cells.add(pageColumnCode);

                //页面字段描述
                ExcelCell pageColumnName = new ExcelCell();
                pageColumnName.setRowNum(row);
                pageColumnName.setColNum(1);
                pageColumnName.setCellValue(BaseUtil.returnString(tableColumn.get("columnDesc")));
                cells.add(pageColumnName);

                //是否必填
                ExcelCell isRequired = new ExcelCell();
                isRequired.setRowNum(row);
                isRequired.setColNum(2);
                if(!BaseUtil.returnString(tableColumn.get("isNull")).equals("")){
                    isRequired.setCellValue("是");
                }else{
                    isRequired.setCellValue("否");
                }
                cells.add(isRequired);

                //字段最大长度
                ExcelCell maxLength = new ExcelCell();
                maxLength.setRowNum(row);
                maxLength.setColNum(3);
                maxLength.setCellValue(BaseUtil.returnString(tableColumn.get("columnLength")));
                cells.add(maxLength);

                //字段来源
                ExcelCell columnSource = new ExcelCell();
                columnSource.setRowNum(row);
                columnSource.setColNum(4);
                columnSource.setCellValue("页面");
                cells.add(columnSource);

                //字段类型
                ExcelCell pageColumnType = new ExcelCell();
                pageColumnType.setRowNum(row);
                pageColumnType.setColNum(5);
                pageColumnType.setCellValue(BaseUtil.returnString(tableColumn.get("dataType")));
                cells.add(pageColumnType);

                //校验规则
                ExcelCell validateRule = new ExcelCell();
                validateRule.setRowNum(row);
                validateRule.setColNum(6);
                validateRule.setCellValue("");
                cells.add(validateRule);

                //加工规则
                ExcelCell processingRule = new ExcelCell();
                processingRule.setRowNum(row);
                processingRule.setColNum(7);
                processingRule.setCellValue("");
                cells.add(processingRule);

                //页面字段别名
                ExcelCell columnAliasCode = new ExcelCell();
                columnAliasCode.setRowNum(row);
                columnAliasCode.setColNum(8);
                columnAliasCode.setCellValue(UnderlineToCamelUtils.underlineToCamel(BaseUtil.returnString(tableColumn.get("columnName")), true));
                cells.add(columnAliasCode);

                //父级节点
                ExcelCell fatherNodeCode = new ExcelCell();
                fatherNodeCode.setRowNum(row);
                fatherNodeCode.setColNum(9);
                String suffix = "";
                if(i > 0){
                    suffix = BaseUtil.returnString(i);
                }
                fatherNodeCode.setCellValue("dataTable" + suffix);
                cells.add(fatherNodeCode);
                row++;
            }
            i++;
        }
    }

    private static void createSheet2TemplateContentTitle(List<ExcelCell> cells) throws Exception {
        String[] contentTitles = new String[]{"条件序号", "前置条件", "表名", "表字段名", "字段别名", "父级节点", "操作类型", "操作条件（sql)"};
        for(int i = 0; i < contentTitles.length; i++){
            ExcelCell cell = new ExcelCell();
            cell.setRowNum(0);
            cell.setColNum(i);
            cell.setCellValue(contentTitles[i]);
            cells.add(cell);
        }
    }

    private static void createSheet2TemplateContent(String tableName, String[] subTables, List<Map<String, Object>> mainTableColumnList, List<List<Map<String, Object>>> subTableList, List<ExcelCell> cells) throws Exception {
        int row = 1;
        createSheet2MainTable(tableName, mainTableColumnList, cells);
        row++;
        createSheet2SubTable(row, tableName, subTables, subTableList, cells);
    }

    private static void createSheet2MainTable(String tableName, List<Map<String, Object>> mainTableColumnList, List<ExcelCell> cells) throws Exception {
        int row = 1;
        for(Map<String, Object> tableColumn : mainTableColumnList){
            if(!BaseUtil.returnString(tableColumn.get("columnName")).equals("id")){
                continue;
            }
            //条件序号
            ExcelCell sqlBatchId = new ExcelCell();
            sqlBatchId.setRowNum(row);
            sqlBatchId.setColNum(0);
            sqlBatchId.setCellValue("1");
            cells.add(sqlBatchId);

            //前置条件

            //表名
            ExcelCell tableCode = new ExcelCell();
            tableCode.setRowNum(row);
            tableCode.setColNum(2);
            tableCode.setCellValue(tableName);
            cells.add(tableCode);

            //表字段名

            // 字段别名

            // 父级节点

            // 操作类型
            ExcelCell sqlType = new ExcelCell();
            sqlType.setRowNum(row);
            sqlType.setColNum(6);
            sqlType.setCellValue("delete");
            cells.add(sqlType);

            // 操作条件（sql)
            ExcelCell sqlCondition = new ExcelCell();
            sqlCondition.setRowNum(row);
            sqlCondition.setColNum(7);
            sqlCondition.setCellValue("where id = #{id}");
            cells.add(sqlCondition);

            row++;
        }
    }

    private static void createSheet2SubTable(int row, String tableName, String[] subTables, List<List<Map<String, Object>>> subTableList, List<ExcelCell> cells) throws Exception {
        int sqlId = 2;
        for(int i = 0; i < subTableList.size(); i++){
            List<Map<String, Object>> subTable = subTableList.get(i);
            createSheet2SubTableDetele(row, sqlId, tableName, subTables[i], cells);
            sqlId++;
            row++;
        }
    }

    private static void createSheet2SubTableDetele(int row, int sqlId, String mainTableName, String subTableName, List<ExcelCell> cells) throws Exception {
        //条件序号
        ExcelCell sqlBatchId = new ExcelCell();
        sqlBatchId.setRowNum(row);
        sqlBatchId.setColNum(0);
        sqlBatchId.setCellValue(BaseUtil.returnString(sqlId));
        cells.add(sqlBatchId);

        //前置条件

        //表名
        ExcelCell tableCode = new ExcelCell();
        tableCode.setRowNum(row);
        tableCode.setColNum(2);
        tableCode.setCellValue(subTableName);
        cells.add(tableCode);

        //表字段名

        // 字段别名

        // 父级节点

        // 操作类型
        ExcelCell sqlType = new ExcelCell();
        sqlType.setRowNum(row);
        sqlType.setColNum(6);
        sqlType.setCellValue("delete");
        cells.add(sqlType);

        //操作条件
        ExcelCell sqlCondition = new ExcelCell();
        sqlCondition.setRowNum(row);
        sqlCondition.setColNum(7);
        String mainTableId = mainTableName + "_id";
        sqlCondition.setCellValue("where " + mainTableId + " = #{id}");
        cells.add(sqlCondition);
    }

}
