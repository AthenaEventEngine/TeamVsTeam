package com.github.athenaeventengine.events;

import com.github.athenaengine.core.config.interfaces.EventConfig;
import com.github.athenaengine.core.model.base.BaseEvent;
import com.github.athenaengine.core.model.base.BaseEventContainer;
import com.github.athenaeventengine.events.config.TvTEventConfig;

public class TeamVsTeamContainer extends BaseEventContainer {

    @Override
    protected Class<? extends EventConfig> getConfigClass() {
        return TvTEventConfig.class;
    }

    public Class<? extends BaseEvent> getEventClass() {
        return TeamVsTeam.class;
    }

    public String getEventName() {
        return "Team vs Team";
    }

    public String getDescription() {
        return "Two teams fight to death";
    }
}
