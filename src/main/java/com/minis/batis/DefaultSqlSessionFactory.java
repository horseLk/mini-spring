package com.minis.batis;

import com.minis.beans.factory.annotation.Autowired;
import com.minis.jdbc.core.JdbcTemplate;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class DefaultSqlSessionFactory implements SqlSessionFactory {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private SqlParser sqlParser;

    private String mapperLocations;

    private Map<String, MapperNode> mapperNodeMap = new HashMap<>();

    public String getMapperLocations() {
        return mapperLocations;
    }

    public void setMapperLocations(String mapperLocations) {
        this.mapperLocations = mapperLocations;
    }

    public Map<String, MapperNode> getMapperNodeMap() {
        return mapperNodeMap;
    }

    public void setMapperNodeMap(Map<String, MapperNode> mapperNodeMap) {
        this.mapperNodeMap = mapperNodeMap;
    }

    public void init() {
        scanLocation(this.mapperLocations); 
    }

    private void scanLocation(String location) {
        String locationPath = this.getClass().getClassLoader().getResource("").getPath() + location;
        File dir = new File(locationPath);
        for (File file : dir.listFiles()) {
            if (file.isDirectory()) {
                scanLocation(location + "/" + file.getName());
            } else {
                buildMapperNodes(location + "/" + file.getName());
            }
        }
    }

    private Map<String, MapperNode> buildMapperNodes(String filePath) {
        SAXReader saxReader=new SAXReader();
        URL xmlPath=this.getClass().getClassLoader().getResource(filePath);

        Document document = null;
        try {
            document = saxReader.read(xmlPath);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        Element rootElement = document.getRootElement();

        String namespace = rootElement.attributeValue("namespace");
        Iterator<Element> nodes = rootElement.elementIterator();
        while (nodes.hasNext()) {
            Element node = nodes.next();
            String id = node.attributeValue("id");
            String parameterType = node.attributeValue("parameterType");
            String resultType = node.attributeValue("resultType");
            String sql = node.getText();

            MapperNode selectnode = new MapperNode();
            selectnode.setNamespace(namespace);
            selectnode.setId(id);
            selectnode.setParameterType(parameterType);
            selectnode.setResultType(resultType);
            selectnode.setSql(sql);
            selectnode.setParameter("");
            try {
                sqlParser.parseSql(selectnode);
            } catch (Exception e) {
                e.printStackTrace();
            }

            this.mapperNodeMap.put(namespace + "." + id, selectnode);
        }

        return this.mapperNodeMap;
    }

    @Override
    public SqlSession openSession() {
        SqlSession newSqlSession = new DefaultSqlSession();
        newSqlSession.setJdbcTemplate(jdbcTemplate);
        newSqlSession.setSqlParser(sqlParser);
        newSqlSession.setSqlSessionFactory(this);

        return newSqlSession;
    }

    @Override
    public MapperNode getMapperNode(String name) {
        return this.mapperNodeMap.get(name);
    }
}
