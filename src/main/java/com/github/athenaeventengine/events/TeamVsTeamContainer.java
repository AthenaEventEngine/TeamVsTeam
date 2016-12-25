package com.github.athenaeventengine.events;

import com.github.athenaeventengine.events.config.TvTEventConfig;
import com.github.u3games.eventengine.events.handler.AbstractEvent;
import com.github.u3games.eventengine.model.BaseEventContainer;

public class TeamVsTeamContainer extends BaseEventContainer<TvTEventConfig> {

    public Class<? extends AbstractEvent> getEventClass() {
        return TeamVsTeam.class;
    }

    public String getBaseConfigPath() {
        return "asdasd";
    }

    public String getEventName() {
        return "Team vs Team";
    }

    public String getDescription() {
        return "Two teams fight to death";
    }
}
