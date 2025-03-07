package com.dpardo.ulid.hibernate;

import com.dpardo.ulid.ULID;
import org.hibernate.HibernateException;
import org.hibernate.MappingException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.type.Type;

import java.util.Properties;

public class ULIDIdGenerator implements IdentifierGenerator {

    private ULIDTypeDescriptor.ValueTransformer valueTransformer;

    @Override
    public void configure(Type type, Properties parameters, ServiceRegistry serviceRegistry) throws MappingException {
        if (ULID.class.isAssignableFrom(type.getReturnedClass())) {
            valueTransformer = ULIDTypeDescriptor.PassThroughTransformer.INSTANCE;
        } else if (String.class.isAssignableFrom(type.getReturnedClass())) {
            valueTransformer = ULIDTypeDescriptor.StringTransformer.INSTANCE;
        } else if (byte[].class.isAssignableFrom(type.getReturnedClass())) {
            valueTransformer = ULIDTypeDescriptor.ToBytesTransformer.INSTANCE;
        } else {
            throw new HibernateException("Unanticipated return type [" + type.getReturnedClass().getName() + "] for ULID conversion");
        }
    }

    @Override
    public Object generate(SharedSessionContractImplementor session, Object object) throws HibernateException {
        Object id = session.getEntityPersister(null, object)
                .getClassMetadata().getIdentifier(object, session);

        if (id != null) {
            return id;
        }
        ULID val = ULID.getUlid();
        return valueTransformer.transform(val);
    }
}
