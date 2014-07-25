package com.example.helloworld.db

import com.example.helloworld.api.GameData
import com.example.helloworld.core.Game
import com.google.common.base.Optional
import com.google.common.collect.ImmutableList
import com.google.inject.Inject
import io.dropwizard.hibernate.AbstractDAO
import org.hibernate.SessionFactory

/**
 * CBW: Write me.
 *
 * @author Chris Wash <a href="mailto:chris.wash@gmail.com">chris.wash@gmail.com</a>
 */
class GameDAO extends AbstractDAO<Game> {

    @Inject
    public GameDAO(SessionFactory factory) {
        super(factory)
    }

    def mapGameToGameData = {
        def playerGroup = it.playerGroup ?: Collections.emptyList()
        def playerOrder = it.playerOrder ?: Collections.emptyList()
        def playersHit = it.playersHit ?: Collections.emptyList()
        return new GameData(it.gameId, it.initialPot, it.totalPot,  ImmutableList.copyOf(playerGroup), ImmutableList.copyOf(playerOrder), ImmutableList.copyOf(playersHit), it.potSplit, it.gameOver)
    }

    public Optional<GameData> findById(String id) {
        Game game = findByGameId(id)
        return Optional.fromNullable(game == null ? null : mapGameToGameData(game))
    }

    private Game findByGameId(String id) {
        return uniqueResult(namedQuery("com.example.helloworld.core.Game.findById").setString("id", id))
    }

    public List<GameData> findAll() {
        List<Game> games = list(namedQuery("com.example.helloworld.core.Game.findAll"))
        List<GameData> gameData = games.collect mapGameToGameData

        return gameData
    }

    public GameData create(Game game) {
        Game savedGame = persist(game)
        return mapGameToGameData(savedGame)
    }

    public GameData startGame(String id) {
        Game game = findByGameId(id)
        // set over to false
        game.gameOver = false;
        // calculate the total pot
        game.totalPot = game.initialPot + game.playerGroup.size()
        // randomize order
        game.playerOrder = new ArrayList<>(game.playerGroup)
        Collections.shuffle(game.playerOrder, new Random(System.nanoTime()));
        Game savedGame = persist(game);
        return mapGameToGameData(savedGame);
    }

    public GameData addPlayerToGame(String gameId, String handle) {
        Game game = findByGameId(gameId)
        game.playerGroup.add(handle)
        Game savedGame = persist(game)
        return mapGameToGameData(savedGame)
    }

    public GameData addPlayerHit(String gameId, String handle) {
        Game game = findByGameId(gameId)
        game.playersHit.add(handle)
        Game savedGame = persist(game)
        return mapGameToGameData(savedGame)
    }
}
