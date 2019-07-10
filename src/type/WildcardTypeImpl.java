package type;


import java.io.Serializable;
import java.lang.reflect.Type;
import java.lang.reflect.WildcardType;

/**
 * Created by joybar on 2019/7/10.
 */
public final class WildcardTypeImpl implements WildcardType, Serializable {
    private final Type upperBound;
    private final Type lowerBound;
    static final Type[] EMPTY_TYPE_ARRAY = new Type[]{};

    public WildcardTypeImpl(Type[] upperBounds, Type[] lowerBounds) {
        TypeCanonicalize.checkArgument(lowerBounds.length <= 1);
        TypeCanonicalize.checkArgument(upperBounds.length == 1);

        if (lowerBounds.length == 1) {
            TypeCanonicalize.checkNotNull(lowerBounds[0]);
            TypeCanonicalize.checkNotPrimitive(lowerBounds[0]);
            TypeCanonicalize.checkArgument(upperBounds[0] == Object.class);
            this.lowerBound = TypeCanonicalize.canonicalize(lowerBounds[0]);
            this.upperBound = Object.class;

        } else {
            TypeCanonicalize.checkNotNull(upperBounds[0]);
            TypeCanonicalize.checkNotPrimitive(upperBounds[0]);
            this.lowerBound = null;
            this.upperBound = TypeCanonicalize.canonicalize(upperBounds[0]);
        }
    }

    public Type[] getUpperBounds() {
        return new Type[]{upperBound};
    }

    public Type[] getLowerBounds() {
        return lowerBound != null ? new Type[]{lowerBound} : EMPTY_TYPE_ARRAY;
    }

    @Override
    public boolean equals(Object other) {
        return other instanceof WildcardType
                && TypeCanonicalize.equals(this, (WildcardType) other);
    }

    @Override
    public int hashCode() {
        // this equals Arrays.hashCode(getLowerBounds()) ^ Arrays.hashCode(getUpperBounds());
        return (lowerBound != null ? 31 + lowerBound.hashCode() : 1)
                ^ (31 + upperBound.hashCode());
    }

    @Override
    public String toString() {
        if (lowerBound != null) {
            return "? super " + TypeCanonicalize.typeToString(lowerBound);
        } else if (upperBound == Object.class) {
            return "?";
        } else {
            return "? extends " + TypeCanonicalize.typeToString(upperBound);
        }
    }

    private static final long serialVersionUID = 0;
}
