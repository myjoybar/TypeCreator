package test;

import test.data.StudentsBean;
import type.TypeGenerator;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by joybar on 2019/7/10.
 */
public class Test {

    public static void main(String[] args) {
        StudentsBean studentsBean1 = new StudentsBean("Tom", "21");
        StudentsBean studentsBean2 = new StudentsBean("Jose", "22");
        List<StudentsBean> list = new ArrayList<>();
        list.add(studentsBean1);
        list.add(studentsBean2);
        Map<String, StudentsBean> map1 = new HashMap();
        map1.put("tom", studentsBean1);
        map1.put("jose", studentsBean2);


        List<Map<String, Map<String, List<StudentsBean>>>> list2 = new ArrayList<>();

        Map<String, Map<String, List<StudentsBean>>> mapMap = new HashMap<>();

        Map<String, List<StudentsBean>> map = new HashMap<>();
        map.put("key1", list);
        mapMap.put("key2", map);
        list2.add(mapMap);
        list2.add(mapMap);


        parseObj(studentsBean1);
        parseList(list);
        parseMap(map1);
        parseComplexListMap(list2);

    }

    /**
     * 解析class type
     *
     * @param studentsBean
     */
    public static void parseObj(StudentsBean studentsBean) {
        String jsonStr = GsonUtil.parseBeanToStr(studentsBean);
        Type type = TypeGenerator
                .newInstance(StudentsBean.class)
                .build();

        StudentsBean obj = GsonUtil.parseJsonStrToBean(jsonStr, type);
        System.out.println(GsonUtil.parseBeanToStr(obj.toString()));

    }

    /**
     * 解析ParameterizedType
     *
     * @param list
     */
    public static void parseList(List<StudentsBean> list) {
        String jsonStr = GsonUtil.parseBeanToStr(list);
        Type type = TypeGenerator
                .newInstance(ArrayList.class)
                .addTypeArgument(StudentsBean.class)
                .build();

        List<StudentsBean> obj = GsonUtil.parseJsonStrToBean(jsonStr, type);
        System.out.println(GsonUtil.parseBeanToStr(obj.toString()));

    }

    /**
     * 解析ParameterizedType
     *
     * @param map
     */
    public static void parseMap(Map<String, StudentsBean> map) {
        String jsonStr = GsonUtil.parseBeanToStr(map);
        Type type = TypeGenerator
                .newInstance(HashMap.class)
                .addTypeArgument(String.class)
                .addTypeArgument(StudentsBean.class)
                .build();

        Map<String, StudentsBean> obj = GsonUtil.parseJsonStrToBean(jsonStr, type);
        System.out.println(GsonUtil.parseBeanToStr(obj.toString()));

    }

    public static void parseComplexListMap(List<Map<String, Map<String, List<StudentsBean>>>> map) {
        String jsonStr = GsonUtil.parseBeanToStr(map);
        Type type = TypeGenerator
                .newInstance(ArrayList.class)
                .addChildTypeGenerator(
                        TypeGenerator
                        .newInstance(HashMap.class)
                        .addTypeArgument(String.class)
                        . addChildTypeGenerator(
                                TypeGenerator
                                .newInstance(HashMap.class)
                                .addTypeArgument(String.class)
                                .addChildTypeGenerator(
                                        TypeGenerator
                                        .newInstance(ArrayList.class)
                                        .addTypeArgument(StudentsBean.class))))
                .build();

        List<Map<String, Map<String, List<StudentsBean>>>> obj = GsonUtil.parseJsonStrToBean(jsonStr, type);
        System.out.println(GsonUtil.parseBeanToStr(obj.toString()));

    }
}
