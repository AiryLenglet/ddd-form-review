package ch.lenglet.aop;

import ch.lenglet.core.Form;
import ch.lenglet.core.UnauthorizedOperation;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.Supplier;

public class StateBasedPermissionUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger("StateBasedPermission");

    public static Object verifyState(Supplier<Object> methodExecution, JoinPoint jp) {
        final var form = (Form) jp.getTarget();
        LOGGER.debug("State based check for form {}", form);
        final var methodSignature = (MethodSignature) jp.getSignature();
        final var method = methodSignature.getMethod();
        final var stateBasedPermissionAnnotation = method.getAnnotation(StateBasedPermission.class);

        if(form.status() != stateBasedPermissionAnnotation.status()) {
            throw new UnauthorizedOperation("Cannot perform '" + methodSignature.getName() + "' for status '" + form.status() + "'");
        }
        return methodExecution.get();
    }
}
