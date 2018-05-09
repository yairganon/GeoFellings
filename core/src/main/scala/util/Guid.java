package util;

import java.util.UUID;

public class Guid<T> extends Id<String, T> {
    public Guid() {super();}
    public Guid(UUID id) {
        super(id.toString());
    }

    public Guid(String id) {
        super(id.toLowerCase());
        UUID.fromString(id);
    }

    public static <T> Guid of(String id) {
        return new Guid(id);
    }

    public static <T> Guid of(UUID id) {
        return new Guid(id);
    }

    /** @deprecated */
    @Deprecated
    public static <T> Guid<T> random() {
        return of(UUID.randomUUID());
    }

    public <R> Guid<R> asIdOf(Class<R> type) {
        return of(this.getId());
    }

    public String getId() {
        return (String)super.getId();
    }
}