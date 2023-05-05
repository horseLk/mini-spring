package org.example;

import com.minis.util.StringUtils;
import org.junit.Test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UtilsTest {
    @Test
    public void testIndexBatisReference() {
        String sql = "\n     update t_user set name=#{name} where id=#{id}\n";
        sql = StringUtils.trimWhitespace(sql);
        List<String> attrs = new ArrayList<>();
        int[] existPos = StringUtils.indexBatisReference(sql);
        while (null != existPos) {
            String originAttr = sql.substring(existPos[0], existPos[1] + 1);
            String attr = sql.substring(existPos[0] + 2, existPos[1]);

            if (attr.length() == 0) {
                System.out.println("zero attr");
            }
            attrs.add(attr);
            sql = sql.replace(originAttr, "?");
            existPos = StringUtils.indexBatisReference(sql);
        }
        System.out.println(sql);
        System.out.println(attrs);
    }
}
