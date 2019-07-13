package develop.marat.dao;

import develop.marat.executors.Executor;
import develop.marat.models.DataSet;
import develop.marat.models.UserDataSet;
import org.jetbrains.annotations.NotNull;


public class DAOFactory {
    private final Executor executor;

    public DAOFactory(@NotNull Executor executor) {
        this.executor = executor;
    }

    public DAO createDAO(@NotNull Class<? extends DataSet> type) {
        if(type.equals(DataSet.class)){
            return new DataSetExecutorDAO(executor);
        }

        if(type.equals(UserDataSet.class)){
            return new UserDataSetExecutorDAO(executor);
        }

        return null;
    }

}
