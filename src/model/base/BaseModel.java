package model.base;

import java.util.Objects;

public abstract class BaseModel {
    protected final String id;

    protected BaseModel(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof BaseModel baseModel))
            return false;
        return Objects.equals(id, baseModel.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}