package di;

import annotation.Inject;
import org.reflections.ReflectionUtils;

import java.lang.reflect.Constructor;
import java.util.Set;

public class BeamFactoryUtils {
    public static Constructor<?> getInjectedConstructor(Class<?> clazz) {
        // Inject 어노테이션이 붙어있는 모든 생성자만 리플렉션한다
        Set<Constructor> injectedConstructors = ReflectionUtils.getAllConstructors(clazz, ReflectionUtils.withAnnotation(Inject.class));
        if (injectedConstructors.isEmpty()) {
            return null;
        }
        // 간단하게 구현하기 위해 injectedConstructors의 첫번쨰 생성자만 반환한다
        return injectedConstructors.iterator().next();
    }
}
