import static ch.lenglet.aop.StateBasedPermissionUtils.verifyState;

public aspect StateBasedPermission {

    pointcut stateBasedPermissionMethod(): execution(@ch.lenglet.aop.StateBasedPermission * *(..));

    Object around(): stateBasedPermissionMethod() {
        return verifyState(() -> proceed(), thisJoinPoint);
    }
}