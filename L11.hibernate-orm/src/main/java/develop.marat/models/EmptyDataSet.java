package develop.marat.models;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="empty")
public class EmptyDataSet extends DataSet {
    public EmptyDataSet(){
        super();
    }
}
