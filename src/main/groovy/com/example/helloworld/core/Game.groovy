package com.example.helloworld.core

import javax.persistence.*

/**
 * CBW: Write me.
 *
 * @author Chris Wash <a href="mailto:chris.wash@gmail.com">chris.wash@gmail.com</a>
 */
@Entity
@Table(name = "games")
@NamedQueries([
        @NamedQuery(
                name = "com.example.helloworld.core.Game.findAll",
                query = "SELECT g FROM Game g"
        ),
        @NamedQuery(
                name = "com.example.helloworld.core.Game.findById",
                query = "SELECT g FROM Game g WHERE g.gameId = :id"
        )
])
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    long gamePk

    @Column(nullable = false, unique = true)
    String gameId

    @Column(nullable = false)
    Integer initialPot

    @Column
    Integer totalPot

    @ElementCollection(fetch = FetchType.EAGER)
    @OrderColumn(name = "ordinal")
    List<String> playerGroup

    @ElementCollection(fetch = FetchType.EAGER)
    @OrderColumn(name = "ordinal")
    List<String> playerOrder

    @ElementCollection(fetch = FetchType.EAGER)
    @OrderColumn(name = "ordinal")
    List<String> playersHit

    @Column
    Integer potSplit

    @Column
    Boolean gameOver

    @Column
    String createdBy

}
