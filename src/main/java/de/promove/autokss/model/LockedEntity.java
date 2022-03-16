package de.promove.autokss.model;

/**
 * @author <a href="mailto:andreas@bambo.it">Andreas Baumgartner, andreas@bambo.it</a>
 * @since 1.0.0
 */
public interface LockedEntity {

    boolean isLocked();

    void setLocked(boolean locked);
    
}
