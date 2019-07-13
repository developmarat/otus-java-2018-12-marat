package develop.marat.dao;

import develop.marat.executors.Executor;
import org.jetbrains.annotations.NotNull;

public abstract class BaseDAO<T> implements DAO<T> {
    protected final Executor executor;

    BaseDAO(@NotNull Executor executor) {
        this.executor = executor;
    }
}
