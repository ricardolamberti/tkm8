package common.cache;

/** Cache scope levels. */
public enum CacheScope {
    /** Global scope shared across all users and sessions. */
    GLOBAL,
    /** Scope specific to a user. */
    USER,
    /** Scope limited to a session. */
    SESSION
}
