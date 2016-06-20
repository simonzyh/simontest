package javassisttest;

/**
 * Created by yehua.zyh on 2016/6/17.
 */
public   class Converter {
    private String sourceName;
    private String readMethodName;
    private Class<?> targetType;
    private static <T> T notNull(T obj, String message) {
        if (obj == null) {
            throw new NullPointerException(message);
        }
        return obj;
    }

    private static <T> T notNull(T obj) {
        return notNull(obj, null);
    }
    public Converter(Class<?> targetType, String sourceName, String readMethodName) {
        this.targetType = notNull(targetType);
        this.sourceName = notNull(sourceName);
        this.readMethodName = notNull(readMethodName);
    }

    public String convert() {
        if (targetType.isPrimitive()) {
            return getterSource() + "." + getPrimitiveFormat();
        } else if (Generator.isWrapClass(targetType)) {
            return getWrapperFormat() + "(" + getterSource() + ")";
        } else {
            return null;
        }
    }

    private String getterSource() {
        return sourceName + "." + readMethodName + "()";
    }

    private String getPrimitiveFormat() {
        return targetType.getName() + "Value()";
    }

    private String getWrapperFormat() {
        return targetType.getSimpleName() + ".valueOf";
    }
}
