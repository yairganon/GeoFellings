package util;

import java.io.Serializable;

public class Id<I extends Comparable<I>, T> implements Serializable, Comparable<Id<I, T>> {
    private  I id;

    Id() { }

    Id(I id) {
        this.id = id;
    }

    public final boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (o != null && o instanceof Id) {
            Id id1 = (Id)o;
            if (this.id != null) {
                return this.id.equals(id1.id);
            } else return id1.id == null;

        } else {
            return false;
        }
    }

    public int hashCode() {
        return this.id != null ? this.id.hashCode() : 0;
    }

    public int compareTo(Id<I, T> o) {
        return this.id.compareTo(o.id);
    }

    public String toString() {
        return this.id.toString();
    }

    protected I getId() {
        return this.id;
    }
}