package org.grails.orm.hibernate

import grails.gorm.annotation.Entity
import org.junit.Test

/**
 * @author grocher
 */
class TablePerSubclassWithCustomTableNameTests extends AbstractGrailsHibernateTests {


    @Override
    protected getDomainClasses() {
        [Animal, Dog, Cat]
    }

    @Test
    void testGeneratedTables() {
        def con
        try {
            con = session.connection()
            def statement = con.prepareStatement("select * from myDogs")
            statement.execute()
            statement = con.prepareStatement("select * from myCats")
            statement.execute()
        } finally {
            con.close()
        }
    }
}

@Entity
class Animal {
    Long id
    Long version
    String name
    static mapping = {
        tablePerSubclass true
        table "myAnimals"
    }
}

@Entity
class Dog extends Animal {
    String bark
    static mapping = {
        table "myDogs"
    }
}

@Entity
class Cat extends Animal {
    String meow
    static mapping = {
        table "myCats"
    }
}