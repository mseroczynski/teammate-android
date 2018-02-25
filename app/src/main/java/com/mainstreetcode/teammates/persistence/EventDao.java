package com.mainstreetcode.teammates.persistence;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.mainstreetcode.teammates.model.Event;
import com.mainstreetcode.teammates.persistence.entity.EventEntity;

import java.util.Date;
import java.util.List;

import io.reactivex.Maybe;

/**
 * DAO for {@link Event}
 */

@Dao
public abstract class EventDao extends EntityDao<EventEntity> {

    @Override
    protected String getTableName() {
        return "events";
    }

    @Query("SELECT * FROM events as event" +
            " WHERE :teamId = event_team" +
            " AND event_start_date < :date" +
            " ORDER BY event_start_date DESC" +
            " LIMIT 40")
    public abstract Maybe<List<Event>> getEvents(String teamId, Date date);

    @Query("SELECT * FROM events" +
            " WHERE :id = event_id")
    public abstract Maybe<Event> get(String id);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    protected abstract void insert(List<EventEntity> teams);

    @Update(onConflict = OnConflictStrategy.IGNORE)
    protected abstract void update(List<EventEntity> teams);

    @Delete
    public abstract void delete(EventEntity event);
}
