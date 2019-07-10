# TypeCreator
为Gson反序列化创建Type

使用Gson反序列化时，非常方便的生成type。

前不久一个同事在使用Gson反序列化解析map时，使用了这段代码

```java
  public static <T> Map<String, T> GsonToMaps(String gsonString) {
        Map<String, T> map = null;
        if (gson != null) {
            map = gson.fromJson(gsonString, new TypeToken<Map<String, T>>() {
            }.getType());
        }
        return map;
    }

```
结果报错如下：

```java
	
java.lang.ClassCastException: com.google.gson.internal.LinkedTreeMap cannot be cast to xxxentity
	
```

排查原因，发现是因为new TypeToken<Map<String, T>>() { }.getType()并没有返回的实际运行的类的Type。
为了能够方便的生成Type（因为我不喜欢一长串TypeToken的生成方式），所以有了这篇~

举例说明：

##### 1. 解析JOPO ` StudentsBean` 

```
  	Type type = TypeGenerator
                .newInstance(StudentsBean.class)
                .build();

    StudentsBean obj = GsonUtil.parseJsonStrToBean(jsonStr, type);

```


##### 2. 解析` List<StudentsBean>` 

```
	Type type = TypeGenerator
                .newInstance(ArrayList.class)
                .addTypeArgument(StudentsBean.class)
                .build();

    List<StudentsBean> obj = GsonUtil.parseJsonStrToBean(jsonStr, type);

```


##### 3. 解析` Map<String, StudentsBean>` 


```
    Type type = TypeGenerator
            .newInstance(HashMap.class)
            .addTypeArgument(String.class)
            .addTypeArgument(StudentsBean.class)
            .build();

    Map<String, StudentsBean> obj = GsonUtil.parseJsonStrToBean(jsonStr, type);

    
```
##### 4. 来个复杂一点的` List<Map<String, Map<String, List<StudentsBean>>>>`

               
```
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
    
```

