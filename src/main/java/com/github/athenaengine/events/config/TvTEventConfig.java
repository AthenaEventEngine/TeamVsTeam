package com.github.athenaengine.events.config;

import com.github.athenaengine.core.interfaces.IEventConfig;
import com.github.athenaengine.core.model.config.TeamConfig;
import com.github.athenaengine.core.model.holder.EItemHolder;

import java.util.List;

public class TvTEventConfig implements IEventConfig {

    private boolean enabled;
    private String instanceFile;
    private List<EItemHolder> reward;
    private boolean rewardKillEnabled;
    private List<EItemHolder> rewardKill;
    private boolean rewardPvPKillEnabled;
    private int rewardPvPKill;
    private boolean rewardFameKillEnabled;
    private int rewardFameKill;
    private List<TeamConfig> teams;

    public boolean isEnabled() {
        return enabled;
    }

    public String getInstanceFile() {
        return instanceFile;
    }

    public boolean isRewardKillEnabled() {
        return rewardKillEnabled;
    }

    public List<EItemHolder> getRewardKill() {
        return rewardKill;
    }

    public boolean isRewardPvPKillEnabled() {
        return rewardPvPKillEnabled;
    }

    public int getRewardPvPKill() {
        return rewardPvPKill;
    }

    public boolean isRewardFameKillEnabled() {
        return rewardFameKillEnabled;
    }

    public int getRewardFameKill() {
        return rewardFameKill;
    }

    public List<EItemHolder> getReward() {
        return reward;
    }

    public List<TeamConfig> getTeams() {
        return teams;
    }
}

