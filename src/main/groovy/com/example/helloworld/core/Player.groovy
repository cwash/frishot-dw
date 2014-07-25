package com.example.helloworld.core

import javax.persistence.*

/**
 * CBW: Write me.
 *
 * @author Chris Wash <a href="mailto:chris.wash@gmail.com">chris.wash@gmail.com</a>
 */
@Entity
@Table(name = "players")
@NamedQueries([
        @NamedQuery(
                name = "com.example.helloworld.core.Player.findAll",
                query = "SELECT p FROM Player p"
        ),
        @NamedQuery(
                name = "com.example.helloworld.core.Player.findById",
                query = "SELECT p FROM Player p WHERE p.playerId = :id"
        ),
        @NamedQuery(
                name = "com.example.helloworld.core.Player.findByHandle",
                query = "SELECT p FROM Player p WHERE p.handle = :handle"
        )
])
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    long playerPk

    @Column(nullable = false, unique = true)
    String playerId

    @Column(nullable = false, unique = true)
    String handle

    @Column
    Integer totalWinnings

    @Column
    Integer maxPotWon

    @Column
    Integer totalHits

    @Column
    Integer totalShots

    @Column
    Integer maxDistanceHit

    @Column
    Integer currentHitStreak

    @Column
    Integer maxHitStreak

    @ElementCollection(fetch = FetchType.EAGER)
    @OrderColumn(name = "ordinal")
    Map<Integer, Integer> positionHitTotals

}
