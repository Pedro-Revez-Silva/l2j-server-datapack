/*
 * Copyright © 2004-2019 L2J DataPack
 * 
 * This file is part of L2J DataPack.
 * 
 * L2J DataPack is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * L2J DataPack is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package com.l2jserver.datapack.quests.Q00144_PailakaInjuredDragon;

import static com.l2jserver.gameserver.model.quest.State.COMPLETED;
import static com.l2jserver.gameserver.model.quest.State.CREATED;
import static com.l2jserver.gameserver.model.quest.State.STARTED;
import static java.util.Map.entry;

import java.util.Map;

import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.holders.SkillHolder;
import com.l2jserver.gameserver.model.quest.Quest;
import com.l2jserver.gameserver.model.quest.QuestState;
import com.l2jserver.gameserver.util.Util;

/**
 * Pailaka - Injured Dragon (144)
 * @author Zoey76
 */
public final class Q00144_PailakaInjuredDragon extends Quest {
	// NPCs
	private static final int KETRA_ORC_SHAMAN = 32499;
	private static final int KETRA_ORC_SUPPORTER_1 = 32502;
	private static final int KETRA_ORC_INTELLIGENCE_OFFICER = 32509;
	private static final int KETRA_ORC_SUPPORTER2 = 32512;
	// Monster
	private static final int LATANA = 18660;
	// Items
	private static final int PAILAKA_INSTANT_SHIELD = 13032;
	private static final int QUICK_HEALING_POTION = 13033;
	private static final int SPEAR_OF_SILENOS = 13052;
	private static final int ENHANCED_SPEAR_OF_SILENOS = 13053;
	private static final int COMPLETE_SPEAR_OF_SILENOS = 13054;
	private static final int PAILAKA_SOULSHOT_GRADE_A = 13055;
	private static final int WEAPON_UPGRADE_STAGE_1 = 13056;
	private static final int WEAPON_UPGRADE_STAGE_2 = 13057;
	private static final int SILENOS_HAIR_ACCESSORY = 13058;
	
	private static final int PAILAKA_SHIRT = 13296;
	private static final int SCROLL_OF_ESCAPE = 736;
	// Skills
	private static final SkillHolder PAILAKA_REWARD_ENERGY_REPLENISHING = new SkillHolder(5774, 2);
	private static final Map<String, SkillHolder> BUFFS = Map.ofEntries( //
		entry("buff-1", new SkillHolder(1086, 2)), // Haste Lv2
		entry("buff-2", new SkillHolder(1204, 2)), // Wind Walk Lv2
		entry("buff-3", new SkillHolder(1059, 3)), // Empower Lv3
		entry("buff-4", new SkillHolder(1085, 3)), // Acumen Lv3
		entry("buff-5", new SkillHolder(1078, 6)), // Concentration Lv6
		entry("buff-6", new SkillHolder(1068, 3)), // Might Lv3
		entry("buff-7", new SkillHolder(1240, 3)), // Guidance Lv3
		entry("buff-8", new SkillHolder(1077, 3)), // Focus Lv3
		entry("buff-9", new SkillHolder(1242, 3)), // Death Whisper Lv3
		entry("buff-10", new SkillHolder(1062, 2)), // Berserker Spirit Lv2
		entry("buff-11", new SkillHolder(1268, 4)), // Vampiric Rage Lv4
		entry("buff-12", new SkillHolder(1045, 6)) // Blessed Body Lv6
	);
	// Misc
	private static final int MIN_LEVEL = 73;
	private static final int MAX_LEVEL = 77;
	private static final int XP_REWARD = 28000000;
	private static final int SP_REWARD = 2850000;
	
	public Q00144_PailakaInjuredDragon() {
		super(144, Q00144_PailakaInjuredDragon.class.getSimpleName(), "Pailaka - Injured Dragon");
		addStartNpc(KETRA_ORC_SHAMAN);
		addTalkId(KETRA_ORC_SHAMAN, KETRA_ORC_SUPPORTER_1, KETRA_ORC_INTELLIGENCE_OFFICER, KETRA_ORC_SUPPORTER2);
		addFirstTalkId(KETRA_ORC_INTELLIGENCE_OFFICER, KETRA_ORC_SUPPORTER2);
		addKillId(LATANA);
		registerQuestItems(PAILAKA_INSTANT_SHIELD, QUICK_HEALING_POTION, SPEAR_OF_SILENOS, ENHANCED_SPEAR_OF_SILENOS, COMPLETE_SPEAR_OF_SILENOS, COMPLETE_SPEAR_OF_SILENOS, PAILAKA_SOULSHOT_GRADE_A, WEAPON_UPGRADE_STAGE_1, WEAPON_UPGRADE_STAGE_2, SILENOS_HAIR_ACCESSORY);
	}
	
	@Override
	public String onAdvEvent(String event, L2Npc npc, L2PcInstance player) {
		final QuestState qs = getQuestState(player, false);
		if (qs == null) {
			return null;
		}
		
		String htmltext = null;
		switch (event) {
			case "32499-04.htm": {
				if (player.getLevel() < MIN_LEVEL) {
					htmltext = "32499-03.htm";
				} else if (player.getLevel() > MAX_LEVEL) {
					htmltext = "32499-04z.htm";
				} else {
					htmltext = "32499-04.htm";
				}
				break;
			}
			case "32499-05.html":
			case "32499-08a.html":
			case "32499-10.html":
			case "32502-02.html":
			case "32502-03.html":
			case "32502-04.html":
			case "32502-07.html": {
				htmltext = event;
				break;
			}
			case "32499-06.htm": {
				if (qs.isCreated() && (player.getLevel() >= MIN_LEVEL)) {
					htmltext = event;
				}
				break;
			}
			case "32499-07.htm": {
				if (qs.isCreated() && (player.getLevel() >= MIN_LEVEL) && (player.getLevel() <= MAX_LEVEL)) {
					qs.setState(STARTED);
					qs.setCond(1, true);
					qs.setMemoState(1);
					htmltext = event;
				}
				break;
			}
			case "32499-09.html": {
				if (qs.isStarted()) {
					if (qs.getCond() == 1) {
						qs.setCond(2, true);
						// TODO(Zoey76): enterInstance(player, "PailakaVarkaSilenosBarracks.xml", ENTRY_POINT);
						htmltext = "32499-09.html";
					} else {
						htmltext = "32499-11.html";
					}
				}
				break;
			}
			case "32502-05.html": {
				if (qs.getCond() == 2) {
					qs.setCond(3, true);
					qs.setMemoState(3);
					qs.giveItems(SPEAR_OF_SILENOS, 1);
					htmltext = event;
				}
				break;
			}
			case "weapon": {
				if (qs.hasQuestItems(SPEAR_OF_SILENOS)) {
					if (qs.hasQuestItems(WEAPON_UPGRADE_STAGE_1)) {
						qs.takeItems(SPEAR_OF_SILENOS, -1);
						qs.takeItems(WEAPON_UPGRADE_STAGE_1, -1);
						qs.giveItems(ENHANCED_SPEAR_OF_SILENOS, 1);
						htmltext = "32509-02.html";
					} else {
						htmltext = "32509-05.html";
					}
				} else if (qs.hasQuestItems(ENHANCED_SPEAR_OF_SILENOS)) {
					if (qs.hasQuestItems(WEAPON_UPGRADE_STAGE_2)) {
						qs.takeItems(ENHANCED_SPEAR_OF_SILENOS, -1);
						qs.takeItems(WEAPON_UPGRADE_STAGE_2, -1);
						qs.giveItems(COMPLETE_SPEAR_OF_SILENOS, 1);
						htmltext = "32509-03.html";
					} else {
						htmltext = "32509-04.html";
					}
				} else if (qs.hasQuestItems(COMPLETE_SPEAR_OF_SILENOS)) {
					htmltext = "32509-06.html";
				} else if (!qs.hasQuestItems(SPEAR_OF_SILENOS)) {
					htmltext = "32509-01a.html";
				}
				break;
			}
			case "buffs": {
				if (npc.getVariables().getInt("i_ai1", 0) == 0 && npc.getVariables().getInt("i_ai0", 0) < 5) {
					htmltext = "32509-99.html";
				}
				if (npc.getVariables().getInt("i_ai1", 0) == 1) {
					htmltext = "32509-96.html";
				}
				break;
			}
			case "return": {
				if (qs.getMemoState() == 4) {
					qs.giveItems(PAILAKA_SHIRT, 1);
					qs.giveItems(SCROLL_OF_ESCAPE, 1);
					qs.addExpAndSp(XP_REWARD, SP_REWARD);
					npc.setTarget(player);
					npc.doCast(PAILAKA_REWARD_ENERGY_REPLENISHING);
					qs.takeItems(SPEAR_OF_SILENOS, -1);
					qs.takeItems(ENHANCED_SPEAR_OF_SILENOS, -1);
					qs.takeItems(COMPLETE_SPEAR_OF_SILENOS, -1);
					qs.takeItems(PAILAKA_SOULSHOT_GRADE_A, -1);
					qs.takeItems(WEAPON_UPGRADE_STAGE_1, -1);
					qs.takeItems(WEAPON_UPGRADE_STAGE_2, -1);
					qs.takeItems(SILENOS_HAIR_ACCESSORY, -1);
					qs.takeItems(QUICK_HEALING_POTION, -1);
					qs.takeItems(PAILAKA_INSTANT_SHIELD, -1);
					qs.exitQuest(false, true);
					htmltext = "32512-02z.html";
				}
				break;
			}
			default: {
				if (event.startsWith("buff-")) {
					if (npc.getVariables().getInt("i_ai0", 0) < 5) {
						npc.getVariables().set("i_ai0", npc.getVariables().getInt("i_ai0", 0) + 1);
						npc.setTarget(player);
						npc.doCast(BUFFS.get(event));
						htmltext = "32509-98.html";
					}
					
					if (npc.getVariables().getInt("i_ai0", 0) >= 5 && npc.getVariables().getInt("i_ai1", 0) == 0) {
						npc.getVariables().set("i_ai1", 1);
						htmltext = "32509-97.html";
					}
				}
				break;
			}
		}
		return htmltext;
	}
	
	@Override
	public String onFirstTalk(L2Npc npc, L2PcInstance player) {
		String htmltext = null;
		switch (npc.getId()) {
			case KETRA_ORC_INTELLIGENCE_OFFICER: {
				htmltext = "32509-01.html";
				break;
			}
			case KETRA_ORC_SUPPORTER2: {
				htmltext = "32512-01.html";
				break;
			}
		}
		return htmltext;
	}
	
	@Override
	public String onKill(L2Npc npc, L2PcInstance killer, boolean isSummon) {
		final QuestState st = killer.getQuestState(getName());
		if ((st != null) && st.isCond(3) && Util.checkIfInRange(1500, npc, killer, false)) {
			st.setCond(4, true);
		}
		return super.onKill(npc, killer, isSummon);
	}
	
	@Override
	public String onTalk(L2Npc npc, L2PcInstance player) {
		final QuestState qs = getQuestState(player, true);
		String htmltext = getNoQuestMsg(player);
		if (qs == null) {
			return htmltext;
		}
		
		switch (npc.getId()) {
			case KETRA_ORC_SHAMAN: {
				switch (qs.getState()) {
					case CREATED: {
						htmltext = "32499-01.htm";
						break;
					}
					case STARTED: {
						if (qs.getCond() == 1) {
							htmltext = "32499-08.html";
						} else if (qs.getCond() > 1) {
							htmltext = "32499-10.html";
						}
						break;
					}
					case COMPLETED: {
						htmltext = "32499-02.htm";
						break;
					}
				}
				break;
			}
			case KETRA_ORC_SUPPORTER_1: {
				if (qs.getCond() == 2) {
					htmltext = "32502-01.html";
				} else if (qs.getCond() > 2) {
					htmltext = "32502-06.html";
				}
				break;
			}
			case KETRA_ORC_SUPPORTER2: {
				if (qs.isCompleted()) {
					htmltext = "32512-02.html";
				}
				break;
			}
		}
		return htmltext;
	}
}
