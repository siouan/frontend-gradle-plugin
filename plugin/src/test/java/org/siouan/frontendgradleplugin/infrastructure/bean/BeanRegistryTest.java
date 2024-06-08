package org.siouan.frontendgradleplugin.infrastructure.bean;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import lombok.Getter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class BeanRegistryTest {

    @InjectMocks
    private BeanRegistry beanRegistry;

    @Test
    void should_register_the_registry_itself()
        throws BeanInstanciationException, TooManyCandidateBeansException, ZeroOrMultiplePublicConstructorsException {
        assertThat(beanRegistry.getBean(BeanRegistry.class)).isSameAs(beanRegistry);
    }

    @Test
    void should_not_replace_registry_when_registering_other_registry_class()
        throws BeanInstanciationException, TooManyCandidateBeansException, ZeroOrMultiplePublicConstructorsException {
        beanRegistry.registerBeanClass(BeanRegistry.class);

        assertThat(beanRegistry.getBean(BeanRegistry.class)).isSameAs(beanRegistry);
    }

    @Test
    void should_not_replace_registry_when_registering_other_registry_instance()
        throws BeanInstanciationException, TooManyCandidateBeansException, ZeroOrMultiplePublicConstructorsException {
        beanRegistry.registerBean(new BeanRegistry());

        assertThat(beanRegistry.getBean(BeanRegistry.class)).isSameAs(beanRegistry);
    }

    @Test
    void should_fail_getting_bean_with_interface() {
        assertThatThrownBy(() -> beanRegistry.getBean(BeanInterface.class)).isInstanceOf(
            IllegalArgumentException.class);
    }

    @Test
    void should_fail_getting_bean_with_enumeration() {
        assertThatThrownBy(() -> beanRegistry.getBean(BeanEnum.class)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void should_fail_getting_bean_with_abstract_class() {
        assertThatThrownBy(() -> beanRegistry.getBean(AbstractBeanWithPublicConstructor.class)).isInstanceOf(
            IllegalArgumentException.class);
    }

    @Test
    void should_fail_getting_bean_with_class_having_no_public_constructors() {
        assertThatThrownBy(() -> beanRegistry.getBean(NonPublicConstructorBean.class)).isInstanceOf(
            ZeroOrMultiplePublicConstructorsException.class);
    }

    @Test
    void should_fail_getting_bean_with_class_having_multiple_public_constructors() {
        assertThatThrownBy(() -> beanRegistry.getBean(MultiplePublicConstructorsBean.class)).isInstanceOf(
            ZeroOrMultiplePublicConstructorsException.class);
    }

    @Test
    void should_fail_getting_bean_with_constructor_having_an_invalid_parameter() {
        assertThatThrownBy(() -> beanRegistry.getBean(BeanWithNonPublicParameterConstructor.class)).isInstanceOf(
            ZeroOrMultiplePublicConstructorsException.class);
    }

    @Test
    void should_register_and_get_bean_with_constructor_parameter_instanciation()
        throws BeanInstanciationException, TooManyCandidateBeansException, ZeroOrMultiplePublicConstructorsException {
        final PublicConstructorWithValidParameterBean bean = beanRegistry.getBean(
            PublicConstructorWithValidParameterBean.class);
        assertThat(bean).isNotNull();

        final DefaultPublicConstructorBean parameterFromBean = bean.getParameter();
        final DefaultPublicConstructorBean parameter = beanRegistry.getBean(DefaultPublicConstructorBean.class);
        assertThat(parameterFromBean).isNotNull();
        assertThat(parameter).isSameAs(parameterFromBean);
    }

    @Test
    void should_register_and_get_bean_with_constructor_parameter_already_instanciated()
        throws BeanInstanciationException, TooManyCandidateBeansException, ZeroOrMultiplePublicConstructorsException {
        final DefaultPublicConstructorBean parameter = beanRegistry.getBean(DefaultPublicConstructorBean.class);
        assertThat(parameter).isNotNull();

        final PublicConstructorWithValidParameterBean bean = beanRegistry.getBean(
            PublicConstructorWithValidParameterBean.class);
        final DefaultPublicConstructorBean parameterFromBean = bean.getParameter();

        assertThat(bean).isNotNull();
        assertThat(parameterFromBean).isSameAs(parameter);
    }

    @Test
    void should_fail_getting_bean_when_constructor_throws_exception() {
        assertThatThrownBy(() -> beanRegistry.getBean(BeanWithConstructorException.class)).satisfies(e -> {
            assertThat(e).isInstanceOf(BeanInstanciationException.class);
            assertThat(((BeanInstanciationException) e).getBeanClass()).isEqualTo(BeanWithConstructorException.class);
        });
    }

    @Test
    void should_fail_getting_bean_when_parameter_constructor_throws_exception() {
        assertThatThrownBy(() -> beanRegistry.getBean(BeanWithParameterConstructorWithException.class)).satisfies(e -> {
            assertThat(e).isInstanceOf(BeanInstanciationException.class);
            assertThat(((BeanInstanciationException) e).getBeanClass()).isEqualTo(BeanWithConstructorException.class);
        });
    }

    @Test
    void should_get_same_instance_when_registering_bean_class_multiple_times()
        throws BeanInstanciationException, TooManyCandidateBeansException, ZeroOrMultiplePublicConstructorsException {
        // First call triggers internally bean registration and instanciation.
        final DefaultPublicConstructorBean bean1 = new DefaultPublicConstructorBean();
        beanRegistry.registerBean(bean1);
        // Second call shall return exactly the same bean.
        final DefaultPublicConstructorBean bean2 = beanRegistry.getBean(DefaultPublicConstructorBean.class);
        // Third call shall return exactly the same bean, even if we tried to register another instance.
        beanRegistry.registerBean(new DefaultPublicConstructorBean());
        final DefaultPublicConstructorBean bean3 = beanRegistry.getBean(DefaultPublicConstructorBean.class);

        assertThat(bean1).isNotNull();
        assertThat(bean3).isSameAs(bean2).isSameAs(bean1);
    }

    @Test
    void should_get_same_instance_when_registering_bean_instance_multiple_times()
        throws BeanInstanciationException, TooManyCandidateBeansException, ZeroOrMultiplePublicConstructorsException {
        // First call triggers internally bean registration and instanciation.
        beanRegistry.registerBeanClass(DefaultPublicConstructorBean.class);
        // Second call shall return exactly the same bean.
        final DefaultPublicConstructorBean bean1 = beanRegistry.getBean(DefaultPublicConstructorBean.class);
        // Third call shall return exactly the same bean, even if we tried to register another instance.
        beanRegistry.registerBeanClass(DefaultPublicConstructorBean.class);
        final DefaultPublicConstructorBean bean2 = beanRegistry.getBean(DefaultPublicConstructorBean.class);

        assertThat(bean1).isNotNull();
        assertThat(bean2).isSameAs(bean1);
    }

    @Test
    void should_register_child_bean_and_get_with_parent_bean()
        throws BeanInstanciationException, TooManyCandidateBeansException, ZeroOrMultiplePublicConstructorsException {
        final ChildA childBean = beanRegistry.getBean(ChildA.class);
        final Parent parentBean = beanRegistry.getBean(Parent.class);

        assertThat(childBean).isNotNull();
        assertThat(parentBean).isSameAs(childBean);
    }

    @Test
    void should_fail_getting_parent_bean_when_multiple_child_beans_are_registered()
        throws BeanInstanciationException, TooManyCandidateBeansException, ZeroOrMultiplePublicConstructorsException {
        assertThat(beanRegistry.getBean(ChildA.class)).isNotNull();
        assertThat(beanRegistry.getBean(ChildB.class)).isNotNull();
        assertThatThrownBy(() -> beanRegistry.getBean(Parent.class)).isInstanceOf(TooManyCandidateBeansException.class);
    }

    private enum BeanEnum {}

    private interface BeanInterface {}

    private static abstract class AbstractBeanWithPublicConstructor {

        public AbstractBeanWithPublicConstructor() {
        }
    }

    private static class NonPublicConstructorBean {}

    private static class MultiplePublicConstructorsBean {

        public MultiplePublicConstructorsBean() {
        }

        public MultiplePublicConstructorsBean(final String dummy) {
            throw new RuntimeException(dummy);
        }
    }

    private static class DefaultPublicConstructorBean {

        public DefaultPublicConstructorBean() {
        }
    }

    private static class BeanWithConstructorException {

        public BeanWithConstructorException() {
            throw new RuntimeException();
        }
    }

    private static class BeanWithNonPublicParameterConstructor {

        public BeanWithNonPublicParameterConstructor(final NonPublicConstructorBean innerBean) {
        }
    }

    private static class BeanWithParameterConstructorWithException {

        public BeanWithParameterConstructorWithException(final BeanWithConstructorException innerBean) {
        }
    }

    @Getter
    private static class PublicConstructorWithValidParameterBean {

        private final DefaultPublicConstructorBean parameter;

        public PublicConstructorWithValidParameterBean(final DefaultPublicConstructorBean parameter) {
            this.parameter = parameter;
        }
    }

    private static class Parent {}

    private static class ChildA extends Parent {

        public ChildA() {
        }
    }

    private static class ChildB extends Parent {

        public ChildB() {
        }
    }
}
