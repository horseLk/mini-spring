package com.minis.core;

import java.util.Iterator;

// Resource 的作用是方便拓展，后续如果有其他的途径的配置也可以读取
// XML只是其中的一种
public interface Resource extends Iterator<Object> {
}
