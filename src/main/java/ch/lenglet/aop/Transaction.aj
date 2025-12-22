import static ch.lenglet.aop.TransactionUtils.wrapInTransaction;

public aspect Transaction {

    pointcut transactionMethod(): execution(@ch.lenglet.aop.Transaction * *(..));

    Object around(): transactionMethod() {
        return wrapInTransaction(() -> proceed());
    }
}