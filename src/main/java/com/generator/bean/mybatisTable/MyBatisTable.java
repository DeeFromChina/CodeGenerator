package com.generator.bean.mybatisTable;


import com.dee.frame.springbootframe.util.common.BaseUtil;

import java.util.Map;
import java.util.Map.Entry;


/**
 * 用于mybatis操作的表属性
 */
public class MyBatisTable {

	public Integer id;

	//表名
	public String tableCode;

	//表字段
	public Map<String, MyBatisTableColumn> tableDatas;

	//操作数据类型（insert插入，update更新，delete删除，select查询）
	public String dataType;

	//有效值（大于0有效）
	public int effectValue = 0;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTableCode() {
		return tableCode;
	}

	public void setTableCode(String tableCode) {
		this.tableCode = tableCode;
	}

	public Map<String, MyBatisTableColumn> getTableDatas() {
		return tableDatas;
	}

	public void setTableDatas(Map<String, MyBatisTableColumn> tableDatas) {
		this.tableDatas = tableDatas;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public int getEffectValue() {
		return effectValue;
	}

	public void setEffectValue(int effectValue) {
		this.effectValue += effectValue;
	}

	public String toString() {
		StringBuffer sql = new StringBuffer();
		StringBuffer sql2 = new StringBuffer();
		//操作数据类型（insert插入，update更新，delete删除，select查询）
		if("insert".equals(dataType)){
			sql.append("insert into " + tableCode + " (");
			int i = 0;
			for(Entry<String, MyBatisTableColumn> entry : tableDatas.entrySet()){
				if(i > 0){
					sql.append(",");
					sql2.append(",");
				}
				sql.append(entry.getKey());
				String columnValue = BaseUtil.returnString(entry.getValue().getColumnValue());
				sql2.append("'" + columnValue + "'");
			}
			sql.append(")");
			sql.append(" values (");
			sql.append(sql2.toString());
			sql.append(");");
		}
		else if("update".equals(dataType)){
			for(Entry<String, MyBatisTableColumn> entry : tableDatas.entrySet()){
				sql.append("update " + tableCode + " set ");
				String columnValue = BaseUtil.returnString(entry.getValue().getColumnValue());
				String sqlCondition = BaseUtil.returnString(entry.getValue().getSqlCondition());
				sql.append(entry.getKey() + " = " + "'" + columnValue + "'");
				sql.append(" where " + sqlCondition);
				sql.append(";");
			}
		}
		else if("delete".equals(dataType)){

		}
		else if("select".equals(dataType)){

		}
		return sql.toString();
	}

}
