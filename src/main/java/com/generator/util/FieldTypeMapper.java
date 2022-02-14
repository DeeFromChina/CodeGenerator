package com.generator.util;


public class FieldTypeMapper {

    //具体查看https://www.runoob.com/mysql/mysql-data-types.html
    private static final String DT_tinyint = "tinyint";
    private static final String DT_smailint = "smailint";
    private static final String DT_mediumint = "mediumint";
    private static final String DT_int = "int";
    private static final String DT_integer = "integer";
    private static final String DT_bigint = "bigint";
    private static final String DT_bit = "bit";
    private static final String DT_real = "real";
    private static final String DT_double = "double";
    private static final String DT_float = "float";
    private static final String DT_decimal = "decimal";
    private static final String DT_numeric = "numeric";
    private static final String DT_char = "char";
    private static final String DT_varchar = "varchar";
    private static final String DT_date = "date";
    private static final String DT_time = "time";
    private static final String DT_year = "year";
    private static final String DT_timestamp = "timestamp";
    private static final String DT_datetime = "datetime";
    private static final String DT_tinyblob = "tinyblob";
    private static final String DT_blob = "blob";
    private static final String DT_mediumblob = "mediumblob";
    private static final String DT_longblob = "longblob";
    private static final String DT_tinytext = "tinytext";
    private static final String DT_text = "text";
    private static final String DT_mediumtext = "mediumtext";
    private static final String DT_longtext = "longtext";
    private static final String DT_enum = "enum";
    private static final String DT_set = "set";
    private static final String DT_binary = "binary";
    private static final String DT_varbinary = "varbinary";
    private static final String DT_point = "point";
    private static final String DT_linestring = "linestring";
    private static final String DT_polygon = "polygon";
    private static final String DT_geometry = "geometry";
    private static final String DT_multipoint = "multipoint";
    private static final String DT_multilinestring = "multilinestring";
    private static final String DT_multipolygon = "multipolygon";
    private static final String DT_geometrycollection = "geometrycollection";

    private static final String fieldType_String = "String";
    private static final String fieldType_int = "int";
    private static final String fieldType_Integer = "Integer";
    private static final String fieldType_double = "double";
    private static final String fieldType_Double = "Double";
    private static final String fieldType_float = "float";
    private static final String fieldType_Float = "Float";
    private static final String fieldType_long = "long";
    private static final String fieldType_Long = "Long";
    private static final String fieldType_char = "char";
    private static final String fieldType_byte = "byte";
    private static final String fieldType_short = "short";
    private static final String fieldType_boolean = "boolean";
    private static final String fieldType_Date = "Date";

    public static String transformType(String data) {
        switch (data) {
            case DT_tinyint:
                return fieldType_Integer;
            case DT_smailint:
                return fieldType_Integer;
            case DT_mediumint:
                return fieldType_Integer;
            case DT_int:
                return fieldType_Integer;
            case DT_integer:
                return fieldType_Integer;
            case DT_bigint:
                return fieldType_Integer;
            case DT_bit:
                return fieldType_byte;
            case DT_real:
                return fieldType_Double;
            case DT_double:
                return fieldType_Double;
            case DT_float:
                return fieldType_Float;
            case DT_decimal:
                return fieldType_Double;
            case DT_numeric:
                return fieldType_Double;
            case DT_char:
                return fieldType_String;
            case DT_varchar:
                return fieldType_String;
            case DT_date:
                return fieldType_Date;
            case DT_time:
                return fieldType_Date;
            case DT_year:
                return fieldType_Integer;
            case DT_timestamp:
                return fieldType_Integer;
            case DT_datetime:
                return fieldType_Date;
            case DT_tinyblob:
                return fieldType_String;
            case DT_blob:
                return fieldType_String;
            case DT_mediumblob:
                return fieldType_String;
            case DT_longblob:
                return fieldType_String;
            case DT_tinytext:
                return fieldType_String;
            case DT_text:
                return fieldType_String;
            case DT_mediumtext:
                return fieldType_String;
            case DT_longtext:
                return fieldType_String;
            case DT_enum:
                return fieldType_String;
            case DT_set:
                return fieldType_String;
            case DT_binary:
                return fieldType_String;
            case DT_varbinary:
                return fieldType_String;
            case DT_point:
                return fieldType_String;
            case DT_linestring:
                return fieldType_String;
            case DT_polygon:
                return fieldType_String;
            case DT_geometry:
                return fieldType_String;
            case DT_multipoint:
                return fieldType_String;
            case DT_multilinestring:
                return fieldType_String;
            case DT_multipolygon:
                return fieldType_String;
            case DT_geometrycollection:
                return fieldType_String;
            default:
                return fieldType_String;
        }
    }
}
