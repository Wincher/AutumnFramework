package cn.wincher.plugin.soap.security.aspect;

import cn.wincher.plugin.soap.security.annotation.User;
import cn.wincher.plugin.soap.security.exception.AuthzException;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.autumn.framework.annotation.Aspect;
import org.autumn.framework.annotation.Controller;
import org.autumn.framework.proxy.AspectProxy;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Objects;

/**
 * @author wincher
 * @since 2019-01-23
 * <p> cn.wincher.plugin.security.aspect <p>
 */
@Aspect(Controller.class)
public class AuthzAnnotationAspect extends AspectProxy {
  
  private static final Class[] ANNOTATION_CLASS_ARRAY = {
      User.class,
  };
  
  @Override
  public void before(Class<?> cls, Method method, Object[] params) throws Throwable {
    Annotation annotation = getAnnotation(cls, method);
    if (Objects.nonNull(annotation)) {
      Class<?> annotationType = annotation.annotationType();
      if (annotationType.equals(User.class)) {
        handleUser();
      }
    }
    super.before(cls, method, params);
  }
  
  private void handleUser() throws AuthzException {
    Subject currentUser = SecurityUtils.getSubject();
    PrincipalCollection principals = currentUser.getPrincipals();
    if (Objects.isNull(principals) || principals.isEmpty()) {
      throw new AuthzException("not login");
    }
  }
  
  private Annotation getAnnotation(Class<?> cls, Method method) {
    for (Class<? extends Annotation> annotationClass : ANNOTATION_CLASS_ARRAY) {
      if (method.isAnnotationPresent(annotationClass)) {
        return method.getAnnotation(annotationClass);
      }
      
      if (cls.isAnnotationPresent(annotationClass)) {
        return cls.getAnnotation(annotationClass);
      }
    }
    return null;
  }
  
}
