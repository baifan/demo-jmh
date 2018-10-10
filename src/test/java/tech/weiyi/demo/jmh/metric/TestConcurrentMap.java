package tech.weiyi.demo.jmh.metric;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class TestConcurrentMap {

    public static void main(String[] args) {
        ConcurrentMap<String,String> map = new ConcurrentHashMap<>();
        System.out.println(map.putIfAbsent("hello","1"));
        System.out.println(map.putIfAbsent("hello","2"));
        System.out.println(map.putIfAbsent("hello","3"));

    }
}
