package thinkingInJava._14chapter.typeinfo;

import java.util.HashMap;
import java.util.Map;

/**
 * 使用Class.isAssignableFrom() 创建一个通用计数工具
 * <p>
 * public boolean isAssignableFrom(Class<?> cls)
     * 判定 传入的Class对象所表示的类或接口与（指定的 Class 参数所表示的类或接口是否相同，或是否是其超类或超接口）。
     * 如果是则返回 true；否则返回 false。
     * 如果该 Class 表示一个基本类型，且指定的 Class 参数正是该 Class 对象，则该方法返回 true；否则返回 false。
 * </p>
 */
public class TypeCounter extends HashMap<Class<?>, Integer> {
    /**
     * 传入要计数的类
     */
    private Class<?> baseType;
    public TypeCounter (Class<?> baseType) {
        this.baseType = baseType;
    }
    public void count(Object object) {
        Class<?> type = object.getClass();
        if(!baseType.isAssignableFrom(type)) {
            throw new RuntimeException(
                    object + " incorrect type" + type + " should be type or subtype of " + baseType);
        }
        countClass(type);
    }

    private void countClass(Class<?> type) {
        Integer quantity = get(type);
        put(type, quantity == null ? 1 : quantity + 1);
        Class<?> superClass = type.getSuperclass();
        if(superClass != null && baseType.isAssignableFrom(superClass)) {
            countClass(superClass);
        }
    }

    public String toString()  {
        StringBuilder builder = new StringBuilder("{");
        for(Map.Entry<Class<?>, Integer> entry : entrySet()) {
            builder.append(entry.getKey().getSimpleName());
            builder.append("=");
            builder.append(entry.getValue());
            builder.append(", ");
        }
        builder.delete(builder.length() - 2, builder.length());
        builder.append("}");
        return builder.toString();
    }

}
