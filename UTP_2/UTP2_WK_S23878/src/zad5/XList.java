package zad5;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class XList <T> extends ArrayList<T> {

    private XList() {
    }

    public XList(Object... objects) {
        this.addAll(XList.of(objects));
    }

    public static <T> XList<T> of(Object... objects) {
        XList xList = new XList<>();

        boolean combine = false;
        if (objects.length > 1) {
            combine = true;

            for (Object object : objects) {
                if (!(object instanceof Collection || object.getClass().isArray())) {
                    combine = false;
                    break;
                }
            }
        }

        for (Object object : objects) {
            if (object instanceof Collection && !combine) {
                ((Collection) object).forEach(o -> xList.addAll(XList.of(o)));
            } else if (object.getClass().isArray() && !combine) {
                Arrays.stream(((Object[]) object)).forEach(o -> xList.addAll(XList.of(o)));
            } else {
                if (combine) {
                    xList.add(XList.of(object));
                } else {
                    xList.add(object);
                }
            }
        }

        return xList;
    }

    public static XList<String> charsOf(String string) {
        List<String> charList = new ArrayList<>();
        for (char c : string.toCharArray()) {
            charList.add(String.valueOf(c));
        }
        return XList.of(charList);
    }

    public static XList<String> tokensOf(String string) {
        return XList.tokensOf(string, "\\s+");
    }

    public static XList<String> tokensOf(String string, String separator) {
        return XList.of(string.split(separator));
    }

    public XList<Integer> union(Object... objects) {
        XList xList = new XList(this);
        xList.addAll(XList.of(objects));
        return xList;
    }

    public XList diff(Object... objects) {
        XList xList = new XList(this);
        xList.removeAll(XList.of(objects));
        return xList;
    }

    public XList<T> unique() {
        XList xList = new XList();

        this.forEach(o -> {
            if (!xList.contains(o)) {
                xList.add(o);
            }
        });

        return xList;
    }

    public XList<XList<T>> combine() {
        XList<XList<T>> xList = combine(0, (XList<XList<T>>) this);
        xList.forEach(subList -> Collections.reverse(subList));
        return xList;
    }

    private static <T> XList<XList<T>> combine(int index, XList<XList<T>> sets) {
        XList<XList<T>> ret = new XList<>();

        if (index == sets.size()) {
            ret.add(new XList<>());
        } else {
            for (Object obj : sets.get(index)) {
                for (XList<T> set : combine(index + 1, sets)) {
                    set.add((T) obj);
                    ret.add(set);
                }
            }
        }

        return ret;
    }


    public String join(String separator) {
        return this.stream()
                .map(Object::toString)
                .collect(Collectors.joining(separator));
    }

    public String join() {
        return join("");
    }

    public <K> XList<String> collect(Function<XList<K>, String> function
    ) {
        XList<String> result = new XList<>();

        for (T sublist : this) {
            result.add(function.apply((XList<K>) sublist));
        }

        return result;
    }
}




