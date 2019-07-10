# TypeCreator

[Chinese README](README_zh.md)



For the deserialization of Gson to create Type While using the deserialisation of Gson, it will generate Type conveniently.



Not long age, one of my colleague used this code while using Gson to deserialise the Map.

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
The reported error/s as follow:

```java
	
java.lang.ClassCastException: com.google.gson.internal.LinkedTreeMap cannot be cast to xxxentity
	
```

While checking for the reason of the error/s, I found that because the new TypeToken<Map<String, T>>() { }.getType() did not returns the correct type of the runtime class. In order to generate Type conveniently (because I do not like to write a long list of codes TypeToken), that is why you are reading this blog . 

### For instance：

##### 1. To parse JOPO ` StudentsBean` 

```
  	Type type = TypeGenerator
                .newInstance(StudentsBean.class)
                .build();

    StudentsBean obj = GsonUtil.parseJsonStrToBean(jsonStr, type);

```


##### 2. To parse ` List<StudentsBean>` 

```
	Type type = TypeGenerator
                .newInstance(ArrayList.class)
                .addTypeArgument(StudentsBean.class)
                .build();

    List<StudentsBean> obj = GsonUtil.parseJsonStrToBean(jsonStr, type);

```


##### 3. To parse ` Map<String, StudentsBean>` 


```
    Type type = TypeGenerator
            .newInstance(HashMap.class)
            .addTypeArgument(String.class)
            .addTypeArgument(StudentsBean.class)
            .build();

    Map<String, StudentsBean> obj = GsonUtil.parseJsonStrToBean(jsonStr, type);

    
```
##### 4. A more complicated version ` List<Map<String, Map<String, List<StudentsBean>>>>`

               
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

