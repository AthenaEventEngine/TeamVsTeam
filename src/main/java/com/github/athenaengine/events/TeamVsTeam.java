package com.github.athenaengine.events;

/*
 * Copyright (C) 2015-2016 L2J EventEngine
 *
 * This file is part of L2J EventEngine.
 *
 * L2J EventEngine is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * L2J EventEngine is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */

import com.github.athenaengine.core.builders.TeamsBuilder;
import com.github.athenaengine.core.config.BaseConfigLoader;
import com.github.athenaengine.core.datatables.MessageData;
import com.github.athenaengine.core.dispatcher.events.OnDeathEvent;
import com.github.athenaengine.core.dispatcher.events.OnKillEvent;
import com.github.athenaengine.core.enums.ListenerType;
import com.github.athenaengine.core.enums.ScoreType;
import com.github.athenaengine.core.helper.RewardHelper;
import com.github.athenaengine.core.helper.ScreenMessageHelper;
import com.github.athenaengine.core.model.base.BaseEvent;
import com.github.athenaengine.core.model.entity.Character;
import com.github.athenaengine.core.model.entity.Player;
import com.github.athenaengine.core.model.entity.Team;
import com.github.athenaengine.core.util.EventUtil;
import com.github.athenaengine.events.config.TvTEventConfig;

public class TeamVsTeam extends BaseEvent<TvTEventConfig> {
    // Time for resurrection
    private static final int TIME_RES_PLAYER = 10;

    @Override
    protected String getInstanceFile() {
        return getConfig().getInstanceFile();
    }

    @Override
    protected TeamsBuilder onCreateTeams() {
        return new TeamsBuilder()
                .addTeams(getConfig().getTeams())
                .setPlayers(getPlayerEventManager().getAllEventPlayers());
    }

    @Override
    protected void onEventStart() {
        addSuscription(ListenerType.ON_KILL);
        addSuscription(ListenerType.ON_DEATH);
    }

    @Override
    protected void onEventFight() {
        // Nothing
    }

    @Override
    protected void onEventEnd() {
        giveRewardsTeams();
    }

    // LISTENERS ------------------------------------------------------
    @Override
    public void onKill(OnKillEvent event) {
        Player ph = getPlayerEventManager().getEventPlayer(event.getAttacker());
        Character target = event.getTarget();

        // We increased the team's points
        getTeamsManager().getPlayerTeam(ph).increasePoints(ScoreType.KILL, 1);

        // Reward for kills
        if (getConfig().isRewardKillEnabled()) {
            ph.giveItems(getConfig().getRewardKill());
        }
        // Reward PvP for kills
        if (getConfig().isRewardPvPKillEnabled()) {
            ph.setPvpKills(ph.getPvpKills() + getConfig().getRewardPvPKill());
            EventUtil.sendEventMessage(ph, MessageData.getInstance().getMsgByLang(ph, "reward_text_pvp", true).replace("%count%", getConfig().getRewardPvPKill() + ""));
        }
        // Reward fame for kills
        if (getConfig().isRewardFameKillEnabled()) {
            ph.setFame(ph.getFame() + getConfig().getRewardFameKill());
            EventUtil.sendEventMessage(ph, MessageData.getInstance().getMsgByLang(ph, "reward_text_fame", true).replace("%count%", getConfig().getRewardFameKill() + ""));
        }
        // Message Kill
        if (BaseConfigLoader.getInstance().getMainConfig().isKillerMessageEnabled()) {
            EventUtil.messageKill(ph, target);
        }
        showPoint(ph.getTeam());
    }

    @Override
    public void onDeath(OnDeathEvent event) {
        Player ph = getPlayerEventManager().getEventPlayer(event.getTarget());

        scheduleRevivePlayer(ph, TIME_RES_PLAYER);
        // Incremented by one the number of deaths Character
        ph.increasePoints(ScoreType.DEATH, 1);
    }

    // VARIOUS METHODS -------------------------------------------------
    private void giveRewardsTeams() {
        if (getPlayerEventManager().getAllEventPlayers().isEmpty()) return;

        RewardHelper.newInstance()
                .setScoreType(ScoreType.KILL)
                .addReward(1, getConfig().getReward())
                .setParticipants(getTeamsManager().getAllTeams())
                .distribute(true);
    }

    private void showPoint(Team team) {
        ScreenMessageHelper.newInstance()
                .setMessage(" | %teamName% %score% | ")
                .replaceHolder("%teamName%", team.getName())
                .replaceHolder("%score%", String.valueOf(team.getPoints(ScoreType.KILL)))
                .setTime(10000)
                .show(team.getMembers());
    }
}