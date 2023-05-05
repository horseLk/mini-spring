package com.minis.batis;

import com.minis.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class DefaultSqlParser implements SqlParser {
    @Override
    public void parseSql(MapperNode node) throws Exception  {
        processedReference(node);
    }

    private void processedReference(MapperNode node) throws Exception {
        List<String> attrs = new ArrayList<>();
        String sql = StringUtils.trimWhitespace(node.getSql());
        int[] existPos = StringUtils.indexBatisReference(sql);
        while (null != existPos) {
            String originAttr = sql.substring(existPos[0], existPos[1] + 1);
            String attr = sql.substring(existPos[0] + 2, existPos[1]);

            if (attr.length() == 0) {
                throw new Exception("reference attr can not be empty.");
            }
            attrs.add(attr);
            sql = sql.replace(originAttr, "?");
            existPos = StringUtils.indexBatisReference(sql);
        }
        node.setSql(sql);
        node.setAttrs(attrs.toArray(new String[0]));
    }
}
