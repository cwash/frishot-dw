package com.example.helloworld.db
import com.example.helloworld.core.Person
import com.google.common.base.Optional
import com.google.inject.Inject
import io.dropwizard.hibernate.AbstractDAO
import org.hibernate.SessionFactory

public class PersonDAO extends AbstractDAO<Person> {

    @Inject
    public PersonDAO(SessionFactory factory) {
        super(factory)
    }

    public Optional<Person> findById(Long id) {
        return Optional.fromNullable(get(id))
    }

    public Person create(Person person) {
        return persist(person)
    }

    public List<Person> findAll() {
        return list(namedQuery("com.example.helloworld.core.Person.findAll"))
    }
}
