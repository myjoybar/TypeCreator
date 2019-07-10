package type;


import java.io.Serializable;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.Type;

/**
 * Created by joybar on 2019/7/10.
 */
public final class GenericArrayTypeImpl implements GenericArrayType, Serializable {
    private final Type componentType;

    public GenericArrayTypeImpl(Type componentType) {
        this.componentType = TypeCanonicalize.canonicalize(componentType);
    }

    public Type getGenericComponentType() {
        return componentType;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof GenericArrayType
                && TypeCanonicalize.equals(this, (GenericArrayType) o);
    }

    @Override
    public int hashCode() {
        return componentType.hashCode();
    }

    @Override
    public String toString() {
        return TypeCanonicalize.typeToString(componentType) + "[]";
    }

    private static final long serialVersionUID = 0;
}