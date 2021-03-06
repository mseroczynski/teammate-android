/*
 * MIT License
 *
 * Copyright (c) 2019 Adetunji Dahunsi
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.mainstreetcode.teammate.persistence;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.mainstreetcode.teammate.model.Event;
import com.mainstreetcode.teammate.model.Game;
import com.mainstreetcode.teammate.persistence.entity.GameEntity;

import java.util.Date;
import java.util.List;

import io.reactivex.Maybe;

/**
 * DAO for {@link Event}
 */

@Dao
public abstract class GameDao extends EntityDao<GameEntity> {

    @Override
    protected String getTableName() {
        return "games";
    }

    @Query("SELECT * FROM games as game" +
            " WHERE (:teamId = game_host AND game_ref_path = 'user')" +
            " OR :teamId = game_home_entity" +
            " OR :teamId = game_away_entity" +
            " AND game_created < :date" +
            " ORDER BY game_created DESC" +
            " LIMIT :limit")
    public abstract Maybe<List<Game>> getGames(String teamId, Date date, int limit);

    @Query("SELECT * FROM games as game" +
            " WHERE :tournamentId = game_tournament" +
            " AND game_round = :round" +
            " ORDER BY game_created DESC" +
            " LIMIT :limit")
    public abstract Maybe<List<Game>> getGames(String tournamentId, int round, int limit);

    @Query("SELECT * FROM games" +
            " WHERE :id = game_id")
    public abstract Maybe<Game> get(String id);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public abstract void insert(List<GameEntity> games);

    @Update(onConflict = OnConflictStrategy.IGNORE)
    protected abstract void update(List<GameEntity> games);

    @Delete
    public abstract void delete(GameEntity game);
}
