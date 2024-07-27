package model.base;

public abstract class Deletable extends BaseModel {
    protected boolean deleted;

    protected Deletable(String id) {
        super(id);
        deleted = false;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
}