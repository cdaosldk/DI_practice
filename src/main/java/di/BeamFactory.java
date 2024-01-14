package di;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

public class BeamFactory {

    // 왜 인스턴스 변수로 담는지?
    private final Set<Class<?>> preInstantiatedClazz;
    private final Map<Class<?>, Object> beans = new HashMap<>();

    public BeamFactory(Set<Class<?>> preInstantiatedClazz) {
        this.preInstantiatedClazz = preInstantiatedClazz;
        initialize();
    }

    private void initialize() {
        for (Class<?> clazz : preInstantiatedClazz) {
            Object instance = createInstance(clazz);
            beans.put(clazz, instance);
        }
    }

    private Object createInstance(Class<?> clazz) {
        // 생성자
        Constructor<?> constructor = findConstructor(clazz);

        // 파리미터
        List<Object> parameters = new ArrayList<>();
        for(Class<?> typeClass : constructor.getParameterTypes()) {
            parameters.add(getParameterByClass(typeClass));
        }

        // 인스턴스 생성
        try {
            return constructor.newInstance(parameters.toArray());
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    private Constructor<?> findConstructor(Class<?> clazz) {
        Constructor<?> constructor = BeamFactoryUtils.getInjectedConstructor(clazz);

        if (Objects.nonNull(constructor)) {
            return constructor;
        }

        return clazz.getConstructors()[0];
    }

    private Object getParameterByClass(Class<?> typeClass) {
        Object instanceBean = getBean(typeClass);

    }


    // class 타입 객체를 키로 가진 beans map에서 해당 키로 value인 인스턴스를 찾는다
    public <T> T getBean(Class<T> requiredType) {
        return (T) beans.get(requiredType);
    }
}

