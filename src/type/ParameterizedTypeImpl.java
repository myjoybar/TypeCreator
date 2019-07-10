package type;

import java.io.Serializable;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;

/**
 * Created by joybar on 2019/7/10.
 */
public class ParameterizedTypeImpl implements ParameterizedType, Serializable {
    private Type ownerType;
    private final Type rawType;
    private final Type[] typeArguments;

    public ParameterizedTypeImpl(Type rawType, Type[] typeArguments) {
        this(null, rawType, typeArguments);
    }

    public ParameterizedTypeImpl(Type ownerType, Type rawType, Type... typeArguments) {
        // require an owner type if the raw type needs it
        if (rawType instanceof Class<?>) {
            Class<?> rawTypeAsClass = (Class<?>) rawType;
            boolean isStaticOrTopLevelClass = Modifier.isStatic(rawTypeAsClass.getModifiers())
                    || rawTypeAsClass.getEnclosingClass() == null;
            TypeCanonicalize.checkArgument(ownerType != null || isStaticOrTopLevelClass);
        }

        this.ownerType = ownerType == null ? null : TypeCanonicalize.canonicalize(ownerType);
        this.rawType = TypeCanonicalize.canonicalize(rawType);
        this.typeArguments = typeArguments.clone();
        for (int t = 0, length = this.typeArguments.length; t < length; t++) {
            TypeCanonicalize.checkNotNull(this.typeArguments[t]);
            TypeCanonicalize.checkNotPrimitive(this.typeArguments[t]);
            this.typeArguments[t] = TypeCanonicalize.canonicalize(this.typeArguments[t]);
        }
    }

    public Type[] getActualTypeArguments() {
        return typeArguments.clone();
    }

    public Type getRawType() {
        return rawType;
    }

    public Type getOwnerType() {
        return ownerType;
    }

    @Override
    public boolean equals(Object other) {
        return other instanceof ParameterizedType
                && TypeCanonicalize.equals(this, (ParameterizedType) other);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(typeArguments)
                ^ rawType.hashCode()
                ^ TypeCanonicalize.hashCodeOrZero(ownerType);
    }

    @Override
    public String toString() {
        int length = typeArguments.length;
        if (length == 0) {
            return TypeCanonicalize.typeToString(rawType);
        }

        StringBuilder stringBuilder = new StringBuilder(30 * (length + 1));
        stringBuilder.append(TypeCanonicalize.typeToString(rawType)).append("<").append(TypeCanonicalize.typeToString(typeArguments[0]));
        for (int i = 1; i < length; i++) {
            stringBuilder.append(", ").append(TypeCanonicalize.typeToString(typeArguments[i]));
        }
        return stringBuilder.append(">").toString();
    }

    private static final long serialVersionUID = 0;


}
