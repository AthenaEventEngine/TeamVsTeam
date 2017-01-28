package com.github.athenaengine.events;

import com.github.athenaengine.core.interfaces.IEventConfig;
import com.github.athenaengine.core.model.base.BaseEvent;
import com.github.athenaengine.core.model.base.BaseEventContainer;
import com.github.athenaengine.events.config.TvTEventConfig;

public class TeamVsTeamContainer extends BaseEventContainer {

    @Override
    protected Class<? extends IEventConfig> getConfigClass() {
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
