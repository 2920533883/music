package com.zhang.handler;
import com.alibaba.fastjson.JSON;
import com.zhang.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;
import org.apache.ibatis.type.TypeHandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * 具体类型的TypeHandler
 */
@Slf4j
@MappedJdbcTypes(JdbcType.VARCHAR)  // 数据库类型
@MappedTypes({List.class})          // java数据类型
public class List2VarcharHandler implements TypeHandler<List<String>> {

    @Override
    public void setParameter(PreparedStatement ps, int i, List<String> parameter, JdbcType jdbcType) throws SQLException {
        String text = dealListToOneStr(parameter);
        ps.setString(i, text);
    }

    /**
     * 集合拼接字符串
     * @param parameter
     * @return
     */
    private String dealListToOneStr(List<String> parameter) {
        if (parameter == null || parameter.size() <= 0)
            return null;
        else {
            System.out.println(parameter.toString());
            return parameter.toString().replace(" ", "");
        }
    }

    @Override
    public List<String> getResult(ResultSet rs, String columnName) throws SQLException {
        if (StringUtil.isBlank(rs.getString(columnName))) {
            return new ArrayList<>();
        }
        return Arrays.asList(rs.getString(columnName).replace("[", "").replace("]", "").replace("\"", "").replace(" ", "").split(","));
    }

    @Override
    public List<String> getResult(ResultSet rs, int columnIndex) throws SQLException {
        if (StringUtil.isBlank(rs.getString(columnIndex))) {
            return new ArrayList<>();
        }
        return Arrays.asList(rs.getRowId(columnIndex).toString().replace("[", "").replace("]", "").replace("\"", "").replace(" ", "").split(","));
    }


    @Override
    public List<String> getResult(CallableStatement cs, int columnIndex) throws SQLException {
        String text = cs.getString(columnIndex);
        if (StringUtil.isBlank(text)) {
            return new ArrayList<>();
        }
        return Arrays.asList(cs.getRowId(columnIndex).toString().replace("[", "").replace("]", "").replace("\"", "").replace(" ", "").split(","));
    }
}
