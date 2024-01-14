package di;

import annotation.Controller;
import annotation.Service;
import controller.UserController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.reflections.Reflections;

import java.lang.annotation.Annotation;
import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class BeamFactoryTest {

    private Reflections reflections;
    private BeamFactory beamFactory;

    // 테스트 메서드가 호출되기 전 미리 호출되는 메서드
    @BeforeEach
    void setUp() {
        // 전체 클래스를 대상으로 리플렉션
        reflections = new Reflections("");
        // UserController와 UserService가 리턴된다
        Set<Class<?>> preInstantiatedClazz = getTypesAnnotatedWith(Controller.class, Service.class);
        beamFactory = new BeamFactory(preInstantiatedClazz);
    }

    // ,,, : 해당 인자가 여러 개 들어올 수 있다는 의미
    private Set<Class<?>> getTypesAnnotatedWith(Class<? extends Annotation>... annotations) {
        Set<Class<?>> beans = new HashSet<>();
        for (Class<? extends Annotation> annotation : annotations) {
            beans.addAll(reflections.getTypesAnnotatedWith(annotation));
        }
        return beans;
    }

    @Test
    void diTest() {
        UserController userController = beamFactory.getBean(UserController.class);

        assertThat(userController).isNotNull();
        assertThat(userController.getUserService()).isNotNull();
    }
}