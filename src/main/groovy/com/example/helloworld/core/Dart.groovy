package com.example.helloworld.core

import javax.persistence.*

/**
 * CBW: Write me.
 *
 * @author Chris Wash <a href="mailto:chris.wash@gmail.com">chris.wash@gmail.com</a>
 */
@Entity
@Table(name = "darts")
@NamedQueries([
        @NamedQuery(
                name = "com.example.helloworld.core.Dart.findAll",
                query = "SELECT d FROM Dart d"
        ),
        @NamedQuery(
                name = "com.example.helloworld.core.Dart.findById",
                query = "SELECT d FROM Dart d WHERE d.dartId = :id"
        ),
        @NamedQuery(
                name = "com.example.helloworld.core.Dart.findByHandle",
                query = "SELECT d FROM Dart d WHERE d.handle = :handle"
        )
])
public class Dart {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    long dartPk

    @Column(nullable = false, unique = true)
    String dartId

    @Column(nullable = false, unique = true)
    String handle

    @Column
    Integer totalHits

    @Column
    Integer totalShots

    @ElementCollection(fetch = FetchType.EAGER)
    @OrderColumn(name = "ordinal")
    Map<Integer,Integer> positionHitTotals

}
