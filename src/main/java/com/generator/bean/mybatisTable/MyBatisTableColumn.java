package com.generator.bean.mybatisTable;

public class MyBatisTableColumn {

	//表字段编码
	public String tableColumnCode;

	//处理后的字段别名
	public Object columnAliasCode;

	//字段值
	public Object columnValue;

	//行数编码
	public String countCode;

	//操作条件（sql）
	public String sqlCondition;

	public String getTableColumnCode() {
		return tableColumnCode;
	}

	public void setTableColumnCode(String tableColumnCode) {
		this.tableColumnCode = tableColumnCode;
	}

	public Object getColumnAliasCode() {
		return columnAliasCode;
	}

	public void setColumnAliasCode(Object columnAliasCode) {
		this.columnAliasCode = columnAliasCode;
	}

	public Object getColumnValue() {
		return columnValue;
	}

	public void setColumnValue(Object columnValue) {
		this.columnValue = columnValue;
	}

	public String getCountCode() {
		return countCode;
	}

	public void setCountCode(String countCode) {
		this.countCode = countCode;
	}

	public String getSqlCondition() {
		return sqlCondition;
	}

	public void setSqlCondition(String sqlCondition) {
		this.sqlCondition = sqlCondition;
	}

}
