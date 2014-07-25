package com.example.helloworld.db
import com.example.helloworld.api.PlayerData
import com.example.helloworld.core.Player
import com.google.common.base.Optional
import com.google.common.collect.ImmutableMap
import com.google.inject.Inject
import io.dropwizard.hibernate.AbstractDAO
import org.hibernate.SessionFactory
/**
 * CBW: Write me.
 *
 * @author Chris Wash <a href="mailto:chris.wash@gmail.com">chris.wash@gmail.com</a>
 */
class PlayerDAO extends AbstractDAO<Player> {

    @Inject
    public PlayerDAO(SessionFactory factory) {
        super(factory)
    }
    def mapPlayerToPlayerData = {
        def hitTotals = it.positionHitTotals ?: new HashMap<>()
        return new PlayerData(it.playerId, it.handle, it.totalWinnings, it.maxPotWon, it.totalHits, it.totalShots, it.maxDistanceHit, it.currentHitStreak, it.maxHitStreak, ImmutableMap.copyOf(hitTotals))
    }

    private Player findByPlayerId(String id) {
        return uniqueResult(namedQuery("com.example.helloworld.core.Player.findById").setString("id", id))
    }

    private Player findByPlayerHandle(String handle) {
        return uniqueResult(namedQuery("com.example.helloworld.core.Player.findByHandle").setString("handle", handle))
    }

    public Optional<PlayerData> findById(String id) {
        Player player = findByPlayerId(id)
        return Optional.fromNullable(player == null ? null : mapPlayerToPlayerData(player))
    }

    public Optional<PlayerData> findByHandle(String handle) {
        Player player = findByPlayerHandle(handle)
        return Optional.fromNullable(player == null ? null : mapPlayerToPlayerData(player))
    }

    public List<PlayerData> findAll() {
        List<Player> players = list(namedQuery("com.example.helloworld.core.Player.findAll"))
        List<PlayerData> playerDataList = players.collect mapPlayerToPlayerData

        return playerDataList
    }

    public PlayerData create(Player player) {
        Player savedPlayer = persist(player)
        return mapPlayerToPlayerData(savedPlayer)
    }

    void incrementTotalShots(String id) {
        Player player = findByPlayerId(id)
        player.totalShots++
        persist(player)
    }

    void incrementTotalHits(String id) {
        Player player = findByPlayerId(id)
        player.totalHits++
        persist(player)
    }

    void checkForMaxDistanceAndUpdate(String id, Integer distance) {
        Player player = findByPlayerId(id)
        if (distance > player.maxDistanceHit)
        {
            player.maxDistanceHit = distance
            persist(player)
        }
    }

    void updatePositionalHitTotals(String id, Integer position) {
        Player player = findByPlayerId(id)
        Integer total = player.positionHitTotals.get(position) ?: 0
        total++
        player.positionHitTotals.put(position, total)
        persist(player)
    }
}
