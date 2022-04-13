package de.promove.autokss.model;


/**
 * @author <a href="mailto:andreas@bambo.it">Andreas Baumgartner, andreas@bambo.it</a>
 */
public abstract class AbstractNamedBaseEntity extends AbstractBaseEntity implements NamedEntity, Comparable<NamedEntity> {

    public abstract String getName();

    @Override
    public int compareTo(NamedEntity o) {
        return this.getName().compareTo(o.getName());
    }
}
