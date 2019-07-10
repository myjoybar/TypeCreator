package type;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by joybar on 2019/7/9.
 */
public class TypeGenerator {
    private Type ownerType;
    private final Type rawType;
    private List<Type> typeArguments;
    private TypeGenerator childTypeGenerator;


    public TypeGenerator(Type rawType, List<Type> typeArguments) {
        this.rawType = rawType;
        this.typeArguments = typeArguments;
    }

    public static TypeGenerator newInstance(Type rawType) {
        return new TypeGenerator(rawType, null);
    }

    public TypeGenerator addTypeArgument(Type rawType) {
        if (null == typeArguments) {
            typeArguments = new ArrayList<>(10);
        }
        this.typeArguments.add(rawType);
        return this;
    }


    public TypeGenerator addChildTypeGenerator(TypeGenerator childTypeGenerator) {
        this.childTypeGenerator = childTypeGenerator;
        Type childType = childTypeGenerator.build();
        if (null == typeArguments) {
            typeArguments = new ArrayList<>(10);
        }
        this.typeArguments.add(childType);
        return this;
    }

    public Type build() {
        Type[] types;
        if (null == typeArguments) {
            types = new Type[]{};
        } else {
            types = typeArguments.toArray(new Type[typeArguments.size()]);
        }
        return new ParameterizedTypeImpl(null, rawType, types);
    }

}
