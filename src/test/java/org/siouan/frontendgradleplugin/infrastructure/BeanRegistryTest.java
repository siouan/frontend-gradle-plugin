package org.siouan.frontendgradleplugin.infrastructure;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import javax.annotation.Nonnull;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class BeanRegistryTest {

    @InjectMocks
    private BeanRegistry beanRegistry;

    @Test
    void shouldFailGettingBeanWithInterface() {
        assertThatThrownBy(() -> beanRegistry.getBean(BeanInterface.class)).isInstanceOf(
            IllegalArgumentException.class);
    }

    @Test
    void shouldFailGettingBeanWithEnumeration() {
        assertThatThrownBy(() -> beanRegistry.getBean(BeanEnum.class)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void shouldFailGettingBeanWithAbstractClass() {
        assertThatThrownBy(() -> beanRegistry.getBean(AbstractBeanWithPublicConstructor.class)).isInstanceOf(
            IllegalArgumentException.class);
    }

    @Test
    void shouldFailGettingBeanWithClassHavingNoPublicConstructors() {
        assertThatThrownBy(() -> beanRegistry.getBean(NonPublicConstructorBean.class)).isInstanceOf(
            ZeroOrMultiplePublicConstructorsException.class);
    }

    @Test
    void shouldFailGettingBeanWithClassHavingMultiplePublicConstructors() {
        assertThatThrownBy(() -> beanRegistry.getBean(MultiplePublicConstructorsBean.class)).isInstanceOf(
            ZeroOrMultiplePublicConstructorsException.class);
    }

    @Test
    void shouldFailGettingBeanWithConstructorHavingAnInvalidParameter() {
        assertThatThrownBy(() -> beanRegistry.getBean(BeanWithNonPublicParameterConstructor.class)).isInstanceOf(
            ZeroOrMultiplePublicConstructorsException.class);
    }

    @Test
    void shouldRegisterAndGetBeanAndConstructorParameter()
        throws BeanInstanciationException, TooManyCandidateBeansException, ZeroOrMultiplePublicConstructorsException {
        final PublicConstructorWithValidParameterBean bean = beanRegistry.getBean(
            PublicConstructorWithValidParameterBean.class);
        final DefaultPublicConstructorBean parameterFromBean = bean.getParameter();
        final DefaultPublicConstructorBean parameter = beanRegistry.getBean(DefaultPublicConstructorBean.class);

        assertThat(bean).isNotNull();
        assertThat(parameterFromBean).isNotNull();
        assertThat(parameter).isSameAs(parameterFromBean);
    }

    @Test
    void shouldRegisterAndGetBeanAndReuseConstructorParameter()
        throws BeanInstanciationException, TooManyCandidateBeansException, ZeroOrMultiplePublicConstructorsException {
        final DefaultPublicConstructorBean parameter = beanRegistry.getBean(DefaultPublicConstructorBean.class);
        final PublicConstructorWithValidParameterBean bean = beanRegistry.getBean(
            PublicConstructorWithValidParameterBean.class);
        final DefaultPublicConstructorBean parameterFromBean = bean.getParameter();

        assertThat(parameter).isNotNull();
        assertThat(bean).isNotNull();
        assertThat(parameterFromBean).isSameAs(parameter);
    }

    @Test
    void shouldFailGettingBeanWhenConstructorThrowsException() {
        assertThatThrownBy(() -> beanRegistry.getBean(BeanWithConstructorException.class)).satisfies(e -> {
            assertThat(e).isInstanceOf(BeanInstanciationException.class);
            assertThat(((BeanInstanciationException) e).getBeanClass()).isEqualTo(BeanWithConstructorException.class);
        });
    }

    @Test
    void shouldFailGettingBeanWhenParameterConstructorThrowsException() {
        assertThatThrownBy(() -> beanRegistry.getBean(BeanWithParameterConstructorWithException.class)).satisfies(e -> {
            assertThat(e).isInstanceOf(BeanInstanciationException.class);
            assertThat(((BeanInstanciationException) e).getBeanClass()).isEqualTo(BeanWithConstructorException.class);
        });
    }

    @Test
    void shouldRegisterAndGetBeanWithDefaultPublicConstructor()
        throws BeanInstanciationException, TooManyCandidateBeansException, ZeroOrMultiplePublicConstructorsException {
        // First call triggers internally bean registration and instanciation.
        final DefaultPublicConstructorBean bean1 = beanRegistry.getBean(DefaultPublicConstructorBean.class);
        // Second call shall return exactly the same bean.
        final DefaultPublicConstructorBean bean2 = beanRegistry.getBean(DefaultPublicConstructorBean.class);

        assertThat(bean1).isNotNull();
        assertThat(bean2).isSameAs(bean2);
    }

    @Test
    void shouldRegisterChildBeanAndGetWithParentBean()
        throws BeanInstanciationException, TooManyCandidateBeansException, ZeroOrMultiplePublicConstructorsException {
        final ChildA childBean = beanRegistry.getBean(ChildA.class);
        final Parent parentBean = beanRegistry.getBean(Parent.class);

        assertThat(childBean).isNotNull();
        assertThat(parentBean).isSameAs(childBean);
    }

    @Test
    void shouldFailGettingParentBeanWhenMultipleChildBeansAreRegistered()
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

        public BeanWithNonPublicParameterConstructor(@Nonnull final NonPublicConstructorBean innerBean) {
        }
    }

    private static class BeanWithParameterConstructorWithException {

        public BeanWithParameterConstructorWithException(@Nonnull final BeanWithConstructorException innerBean) {
        }
    }

    private static class PublicConstructorWithValidParameterBean {

        private final DefaultPublicConstructorBean parameter;

        public PublicConstructorWithValidParameterBean(@Nonnull final DefaultPublicConstructorBean parameter) {
            this.parameter = parameter;
        }

        public DefaultPublicConstructorBean getParameter() {
            return parameter;
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
