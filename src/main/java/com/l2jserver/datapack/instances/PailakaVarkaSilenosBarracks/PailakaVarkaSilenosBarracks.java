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
package com.l2jserver.datapack.instances.PailakaVarkaSilenosBarracks;

import static com.l2jserver.gameserver.ai.CtrlIntention.AI_INTENTION_ATTACK;
import static com.l2jserver.gameserver.ai.CtrlIntention.AI_INTENTION_CAST;
import static com.l2jserver.gameserver.enums.audio.Music.BS08_A_10000;
import static com.l2jserver.gameserver.network.SystemMessageId.YOU_HAVE_ENTERED_ANOTHER_INSTANT_ZONE_THEREFORE_YOU_CANNOT_ENTER_CORRESPONDING_DUNGEON;

import com.l2jserver.datapack.instances.AbstractInstance;
import com.l2jserver.datapack.quests.Q00144_PailakaInjuredDragon.Q00144_PailakaInjuredDragon;
import com.l2jserver.gameserver.ai.CtrlIntention;
import com.l2jserver.gameserver.enums.audio.Music;
import com.l2jserver.gameserver.enums.audio.Sound;
import com.l2jserver.gameserver.instancemanager.InstanceManager;
import com.l2jserver.gameserver.model.L2Object;
import com.l2jserver.gameserver.model.L2World;
import com.l2jserver.gameserver.model.Location;
import com.l2jserver.gameserver.model.actor.L2Character;
import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2MonsterInstance;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.holders.SkillHolder;
import com.l2jserver.gameserver.model.instancezone.InstanceWorld;
import com.l2jserver.gameserver.model.quest.Quest;
import com.l2jserver.gameserver.model.quest.QuestState;
import com.l2jserver.gameserver.model.skills.Skill;
import com.l2jserver.gameserver.network.SystemMessageId;
import com.l2jserver.gameserver.network.serverpackets.SpecialCamera;
import com.l2jserver.gameserver.util.Util;

/**
 * Pailaka (Varka Silenos Barracks) instance zone.
 * @author Zoey76
 */
@SuppressWarnings("unused")
public final class PailakaVarkaSilenosBarracks extends AbstractInstance {
	protected class PailakaVarkaSilenosBarracksWorld extends InstanceWorld {
		protected long storeTime = 0;
	}
	
	// NPCs
	private static final int INJURED_DRAGON_SKILL_USE = 18661;
	private static final int INJURED_DRAGON_CAMERA_1 = 18603;
	private static final int INJURED_DRAGON_CAMERA_2 = 18604;
	private static final int INJURED_DRAGON_TARGET_1 = 18605;
	private static final int INJURED_DRAGON_TARGET_2 = 18606;
	private static final int KETRA_ORC_SUPPORTER_2 = 32512;
	// Monster
	private static final int VARKA_SILENOS_RECRUIT = 18635;
	private static final int VARKA_SILENOS_FOOTMAN = 18636;
	private static final int GRAZING_ANTELOPE1 = 18637;
	private static final int VARKA_SILENOS_SCOUT = 18638;
	private static final int VARKA_SILENOS_HUNTER = 18639;
	private static final int VARKA_SILENOS_SHAMAN = 18640;
	private static final int VARKA_SILENOS_PRIEST = 18641;
	private static final int VARKA_SILENOS_WARRIOR = 18642;
	private static final int GRAZING_ANTELOPE2 = 18643;
	private static final int VARKA_SILENOS_MEDIUM = 18644;
	private static final int VARKA_SILENOS_MAGUS = 18645;
	private static final int VARKA_SILENOS_OFFICER = 18646;
	private static final int GRAZING_FLAVA = 18647;
	private static final int VARKA_SILENOS_SEER = 18648;
	private static final int VARKA_SILENOS_GREAT_MAGUS = 18649;
	private static final int VARKA_SILENOS_GENERAL = 18650;
	private static final int GRAZING_ELDER_ANTELOPE = 18651;
	private static final int VARKA_SILENOS_GREAT_SEER = 18652;
	private static final int VARKAS_ELITE_GUARD = 18653;
	private static final int VARKAS_COMMANDER = 18654;
	private static final int VARKAS_HEAD_GUARD = 18655;
	private static final int VARKAS_HEAD_MAGUS = 18656;
	private static final int PROPHETS_GUARD = 18657;
	private static final int DISCIPLE_OF_PROPHET = 18658;
	private static final int VARKAS_PROPHET = 18659;
	private static final int LATANA = 18660;
	private static final int GUARD_CONTROL = 18662;
	private static final int TELEPORT_TRAP = 18663;
	// Locations
	private static final Location ENTRY_POINT = new Location(-19008, 277024, -15000);
	private static final Location EXIT_POINT = new Location(-19008, 277122, -13376);
	// Skills
	private static final SkillHolder ELECTRIC_FLAME = new SkillHolder(5715, 1);
	private static final SkillHolder STUN = new SkillHolder(5716, 1);
	private static final SkillHolder FIRE_BREATH = new SkillHolder(5717, 1);
	private static final SkillHolder ANGER = new SkillHolder(5718, 1);
	private static final SkillHolder PRESENTATION_THE_RISE_OF_LATANA = new SkillHolder(5759, 1);
	// Items
	private static final int SWORD_OF_VALAKAS_1_HANDED = 9136;
	// Misc
	private static final int INSTANCE_ID = 45;
	
	private final L2PcInstance _player = null;
	
	public PailakaVarkaSilenosBarracks() {
		super(PailakaVarkaSilenosBarracks.class.getSimpleName());
		addAttackId(LATANA);
		addKillId(LATANA);
		addSeeCreatureId(INJURED_DRAGON_CAMERA_1, LATANA);
		addSkillSeeId(LATANA);
		addSpawnId(INJURED_DRAGON_SKILL_USE, INJURED_DRAGON_CAMERA_1, INJURED_DRAGON_CAMERA_2);
		
		addSpawn(LATANA, 105785, -41785, -1810, 32768, false, 0);
		addSpawn(INJURED_DRAGON_CAMERA_1, 105785, -41785, -1810, 32768, false, 0);
	}
	
	@Override
	public String onAdvEvent(String event, L2Npc npc, L2PcInstance player) {
		String htmltext = null;
		switch (event) {
			case "enter": {
				final QuestState qs = player.getQuestState(Q00144_PailakaInjuredDragon.class.getSimpleName());
				if (qs == null) {
					return htmltext;
				}
				if (qs.isStarted()) {
					if (qs.getCond() == 1) {
						qs.setCond(2, true);
						enterInstance(player, new PailakaVarkaSilenosBarracksWorld(), "PailakaVarkaSilenosBarracks.xml", INSTANCE_ID);
						htmltext = "32499-09.html";
					} else {
						htmltext = "32499-11.html";
					}
				}
				break;
			}
			case "INJURED_DRAGON_SKILL_USE_1002": {
				startQuestTimer("SCE_BOSS_2ND_SKILL", 2000, npc, null); // GetIndexFromCreature( myself.sm )
				startQuestTimer("INJURED_DRAGON_SKILL_USE_2002", 5000, npc, null);
				break;
			}
			case "INJURED_DRAGON_SKILL_USE_2002": {
				npc.decayMe();
				break;
			}
			case "INJURED_DRAGON_CAMERA_1_1000": {
				_player.sendPacket(new SpecialCamera(player, npc.getObjectId(), 600, 200, 5, 0, 15000, 10000, -10, 8, 1));
				// SpecialCamera3( myself.sm, 600, 200, 5, 0, 15000, 10000, -10, 8, 1, 1, 1 );
				startQuestTimer("INJURED_DRAGON_CAMERA_1_1001", 2000, npc, null);
				break;
			}
			case "INJURED_DRAGON_CAMERA_1_1001": {
				// TODO: myself::SpecialCamera3( myself.sm, 400, 200, 5, 4000, 15000, 10000, -10, 8, 1, 1, 0 );
				startQuestTimer("INJURED_DRAGON_CAMERA_1_1002", 4000, npc, null);
				break;
			}
			case "INJURED_DRAGON_CAMERA_1_1002": {
				// TODO: myself::SpecialCamera3( myself.sm, 300, 195, 4, 1500, 15000, 10000, -5, 10, 1, 1, 0 );
				startQuestTimer("INJURED_DRAGON_CAMERA_1_1003", 1700, npc, null);
				break;
			}
			case "INJURED_DRAGON_CAMERA_1_1003": {
				// TODO: myself::SpecialCamera3( myself.sm, 130, 2, 5, 0, 15000, 10000, 0, 0, 1, 0, 1 );
				startQuestTimer("INJURED_DRAGON_CAMERA_1_1004", 2000, npc, null);
				break;
			}
			case "INJURED_DRAGON_CAMERA_1_1004": {
				// TODO: myself::SpecialCamera3( myself.sm, 220, 0, 4, 800, 15000, 10000, 5, 10, 1, 0, 0 );
				startQuestTimer("INJURED_DRAGON_CAMERA_1_1005", 2000, npc, null);
				break;
			}
			case "INJURED_DRAGON_CAMERA_1_1005": {
				// TODO: myself::SpecialCamera3( myself.sm, 250, 185, 5, 4000, 15000, 10000, -5, 10, 1, 1, 0 );
				startQuestTimer("INJURED_DRAGON_CAMERA_1_1006", 4000, npc, null);
				break;
			}
			case "INJURED_DRAGON_CAMERA_1_1006": {
				// TODO: myself::SpecialCamera3( myself.sm, 200, 0, 5, 2000, 15000, 10000, 0, 25, 1, 0, 0 );
				startQuestTimer("INJURED_DRAGON_CAMERA_1_1007", 4530, npc, null);
				break;
			}
			case "INJURED_DRAGON_CAMERA_1_1007": {
				// TODO: myself::SpecialCamera3( myself.sm, 300, -3, 5, 3500, 15000, 6000, 0, 6, 1, 0, 0 );
				startQuestTimer("INJURED_DRAGON_CAMERA_1_9999", 10000, npc, null);
				break;
			}
			case "INJURED_DRAGON_CAMERA_1_2000": {
				// TODO: myself::SpecialCamera3( myself.sm, 250, 0, 6, 0, 15000, 10000, 2, 0, 1, 0, 1 );
				startQuestTimer("INJURED_DRAGON_CAMERA_1_2001", 2000, npc, null);
				break;
			}
			case "INJURED_DRAGON_CAMERA_1_2001": {
				// TODO: myself::SpecialCamera3( myself.sm, 230, 0, 5, 2000, 15000, 10000, 0, 0, 1, 0, 0 );
				startQuestTimer("INJURED_DRAGON_CAMERA_1_2002", 2500, npc, null);
				break;
			}
			case "INJURED_DRAGON_CAMERA_1_2002": {
				// TODO: myself::SpecialCamera3( myself.sm, 180, 175, 2, 1500, 15000, 10000, 0, 10, 1, 1, 0 );
				startQuestTimer("INJURED_DRAGON_CAMERA_1_2003", 1500, npc, null);
				break;
			}
			case "INJURED_DRAGON_CAMERA_1_2003": {
				// TODO: myself::SpecialCamera3( myself.sm, 300, 180, 5, 1500, 15000, 3000, 0, 6, 1, 1, 0 );
				startQuestTimer("INJURED_DRAGON_CAMERA_1_9999", 3000, npc, null);
				break;
			}
			case "INJURED_DRAGON_CAMERA_1_3000": {
				// TODO: myself::LookNeighbor( 2000 );
				startQuestTimer("INJURED_DRAGON_CAMERA_1_3000", 10000, npc, null);
				break;
			}
			case "INJURED_DRAGON_CAMERA_1_9999": {
				npc.decayMe();
				break;
			}
			case "INJURED_DRAGON_CAMERA_2_1000": {
				// TODO: SpecialCamera3( myself.sm, 450, 200, 3, 0, 15000, 10000, -15, 20, 1, 1, 1 );
				startQuestTimer("INJURED_DRAGON_CAMERA_2_1001", 100, npc, null);
				break;
			}
			case "INJURED_DRAGON_CAMERA_2_1001": {
				// TODO: SpecialCamera3( myself.sm, 350, 200, 5, 5600, 15000, 10000, -15, 10, 1, 1, 0 );
				startQuestTimer("INJURED_DRAGON_CAMERA_2_1002", 5600, npc, null);
				break;
			}
			case "INJURED_DRAGON_CAMERA_2_1002": {
				// TODO: SpecialCamera3( myself.sm, 360, 200, 5, 1000, 15000, 2000, -15, 10, 1, 1, 0 );
				startQuestTimer("INJURED_DRAGON_CAMERA_2_9999", 10000, npc, null);
				break;
			}
			case "INJURED_DRAGON_CAMERA_2_9999": {
				npc.decayMe();
				break;
			}
			case "LATANA_500": {
				addSpawn(INJURED_DRAGON_TARGET_1, 105465, -41817, -1768, 0, false, 0);
				startQuestTimer("LATANA_501", 3000, npc, null);
				break;
			}
			case "LATANA_501": {
				// TODO: myself::AddEffectActionDesire( myself.sm, 0, 91 * 1000 / 30, 10000 );
				startQuestTimer("LATANA_502", 3000, npc, null);
				break;
			}
			case "LATANA_502": {
				if (npc.getTarget() instanceof L2PcInstance) {
					final L2PcInstance target = npc.getTarget().getActingPlayer();
					((L2MonsterInstance) npc).addDamageHate(target, 0, 500);
					((L2MonsterInstance) npc).getAI().setIntention(AI_INTENTION_CAST, PRESENTATION_THE_RISE_OF_LATANA.getSkill(), target);
				}
				startQuestTimer("LATANA_503", 9700, npc, null);
				break;
			}
			case "LATANA_503": {
				if (npc.getTarget() != null) {
					if (npc.getTarget() instanceof L2PcInstance) {
						final L2PcInstance target = npc.getTarget().getActingPlayer();
						((L2MonsterInstance) npc).addDamageHate(target, 0, 500000);
						((L2MonsterInstance) npc).getAI().setIntention(AI_INTENTION_CAST, STUN.getSkill(), target);
					}
				}
				startQuestTimer("LATANA_504", 6030, npc, null);
				break;
			}
			case "LATANA_504": {
				startQuestTimer("LATANA_505", 4000, npc, null);
				break;
			}
			case "LATANA_505": {
				startQuestTimer("LATANA_2000", 1000, npc, null);
				break;
			}
			case "LATANA_600": {
				addSpawn(INJURED_DRAGON_TARGET_1, 105465, -41817, -1768, 0, false, 0);
				startQuestTimer("LATANA_602", 2000, npc, null);
				break;
			}
			case "LATANA_602": {
				if (npc.getTarget() instanceof L2PcInstance) {
					final L2PcInstance target = npc.getTarget().getActingPlayer();
					((L2MonsterInstance) npc).addDamageHate(target, 0, 50000);
					((L2MonsterInstance) npc).getAI().setIntention(AI_INTENTION_CAST, ELECTRIC_FLAME.getSkill(), target);
				}
				startQuestTimer("LATANA_603", 2500, npc, null);
				break;
			}
			case "LATANA_603": {
				if (npc.getTarget() instanceof L2PcInstance) {
					final L2PcInstance target = npc.getTarget().getActingPlayer();
					((L2MonsterInstance) npc).addDamageHate(target, 0, 50000);
					((L2MonsterInstance) npc).getAI().setIntention(AI_INTENTION_CAST, STUN.getSkill(), target);
				}
				startQuestTimer("LATANA_604", 6030, npc, null);
				break;
			}
			case "LATANA_604": {
				startQuestTimer("LATANA_2000", 6000, npc, null);
				break;
			}
			case "LATANA_1000": {
				startQuestTimer("LATANA_1000", 30 * 1000, npc, null);
				break;
			}
			case "LATANA_2000": {
				if (!(npc.getTarget() instanceof L2PcInstance)) {
					startQuestTimer("LATANA_2000", 3000, npc, null);
					break;
				}
				
				final L2PcInstance target = npc.getTarget().getActingPlayer();
				if (Util.checkIfInRange(100, npc, target, false)) {
					if (getRandom(100) < 30) {
						((L2MonsterInstance) npc).addDamageHate(target, 0, 500000);
						((L2MonsterInstance) npc).getAI().setIntention(AI_INTENTION_CAST, ELECTRIC_FLAME.getSkill(), target);
					} else {
						// TODO: myself::AddAttackDesire( target, @AMT_STAND, 1000 );
					}
				} else if (getRandom(100) < 50) {
					// TODO: if(myself::InMyTerritory( target ))
					// {
					addSpawn(INJURED_DRAGON_SKILL_USE, target);
					// }
				} else {
					((L2MonsterInstance) npc).addDamageHate(target, 0, 500000);
					((L2MonsterInstance) npc).getAI().setIntention(AI_INTENTION_CAST, FIRE_BREATH.getSkill(), target);
				}
				startQuestTimer("LATANA_2000", 6000, npc, null);
				break;
			}
			case "LATANA_4000": {
				npc.setScriptValue(0);
				break;
			}
			case "LATANA_9000": {
				addSpawn(INJURED_DRAGON_TARGET_1, npc);
				addSpawn(KETRA_ORC_SUPPORTER_2, npc.getX() + 100, npc.getY() + 100, npc.getZ(), 0, false, 0);
				npc.decayMe();
				break;
			}
			// Script events
			case "SCE_BOSS_2ND_SKILL": {
				if (npc.getTarget() instanceof L2PcInstance) {
					final L2PcInstance target = npc.getTarget().getActingPlayer();
					((L2MonsterInstance) npc).addDamageHate(target, 0, 500000);
					((L2MonsterInstance) npc).getAI().setIntention(AI_INTENTION_CAST, STUN.getSkill(), target);
				}
				break;
			}
			case "SCE_RATANA_CAMERA_START": {
				if (npc.isScriptValue(1)) {
					startQuestTimer("INJURED_DRAGON_CAMERA_1_1000", 10, npc, null);
				} else if (npc.isScriptValue(2)) {
					startQuestTimer("INJURED_DRAGON_CAMERA_1_2000", 10, npc, null);
				}
				break;
			}
			case "SCE_TARGET_SPAWN": {
				// TODO: Do nothing?
			}
		}
		return htmltext;
	}
	
	@Override
	public String onAttack(L2Npc npc, L2PcInstance attacker, int damage, boolean isSummon, Skill skill) {
		if (npc.getScriptValue() == 0) {
			final QuestState st = attacker.getQuestState(getName());
			if (st != null) {
				if (st.isCond(4)) {
					startQuestTimer("LATANA_9000", 1000, npc, null);
				} else {
					st.playSound(BS08_A_10000);
					startQuestTimer("SCE_RATANA_CAMERA_START", 4000, npc, null); // TODO: script value 2
					startQuestTimer("LATANA_600", 1000, npc, null);
					npc.setScriptValue(1);
				}
			}
		}
		
		if ((npc.getCurrentHp() < (npc.getMaxHp() * 0.3)) && (npc.getScriptValue() == 0)) {
			((L2MonsterInstance) npc).addDamageHate(attacker, 0, 50000000);
			((L2MonsterInstance) npc).getAI().setIntention(AI_INTENTION_CAST, ANGER.getSkill(), attacker);
			npc.setScriptValue(1);
			startQuestTimer("LATANA_4000", 120 * 1000, npc, attacker);
		}
		return super.onAttack(npc, attacker, damage, isSummon, skill);
	}
	
	@Override
	public String onKill(L2Npc npc, L2PcInstance killer, boolean isSummon) {
		addSpawn(INJURED_DRAGON_CAMERA_2, 105974, -41794, -1784, 32768, false, 0);
		addSpawn(KETRA_ORC_SUPPORTER_2, killer.getX() + 100, killer.getY() + 100, killer.getZ(), 0, false, 0);
		return super.onKill(npc, killer, isSummon);
	}
	
	@Override
	public String onSeeCreature(L2Npc npc, L2Character creature, boolean isSummon) {
		switch (npc.getId()) {
			case INJURED_DRAGON_CAMERA_1: {
				if (creature.isPlayer() && npc.isScriptValue(0)) {
					npc.setScriptValue(1);
				}
				break;
			}
			case LATANA: {
				if (npc.getScriptValue() == 0) {
					if (creature.isPlayer()) {
						final QuestState st = creature.getActingPlayer().getQuestState(getName());
						if (st != null) {
							if (st.isCond(4)) {
								startQuestTimer("LATANA_9000", 1000, npc, null);
							} else {
								st.playSound(BS08_A_10000);
								startQuestTimer("SCE_RATANA_CAMERA_START", 4000, npc, null); // TODO: script value 1
								startQuestTimer("LATANA_500", 1000, npc, null);
								npc.setScriptValue(1);
							}
						}
					}
				}
				break;
			}
		}
		return super.onSeeCreature(npc, creature, isSummon);
	}
	
	@Override
	public String onSkillSee(L2Npc npc, L2PcInstance caster, Skill skill, L2Object[] targets, boolean isSummon) {
		((L2MonsterInstance) npc).addDamageHate(caster, 0, 100);
		((L2MonsterInstance) npc).getAI().setIntention(AI_INTENTION_ATTACK, caster);
		return super.onSkillSee(npc, caster, skill, targets, isSummon);
	}
	
	@Override
	public String onSpawn(L2Npc npc) {
		switch (npc.getId()) {
			case INJURED_DRAGON_SKILL_USE: {
				startQuestTimer("INJURED_DRAGON_SKILL_USE_1002", 10, npc, null);
				if (npc.getScriptValue() != 0) {
					// TODO: Review where player object Id should be set.
					L2PcInstance c0 = L2World.getInstance().getPlayer(npc.getScriptValue());
					((L2MonsterInstance) npc).addDamageHate(c0, 0, 10000000);
					((L2MonsterInstance) npc).getAI().setIntention(AI_INTENTION_ATTACK, c0);
				}
				break;
			}
			case INJURED_DRAGON_CAMERA_1: {
				startQuestTimer("INJURED_DRAGON_CAMERA_1_3000", 10, npc, null);
				break;
			}
			case INJURED_DRAGON_CAMERA_2: {
				startQuestTimer("INJURED_DRAGON_CAMERA_2_1000", 1, npc, null);
				break;
			}
			case LATANA: {
				startQuestTimer("LATANA_1000", 1000, npc, null);
				break;
			}
		}
		return super.onSpawn(npc);
	}
	
	@Override
	protected void onEnterInstance(L2PcInstance player, InstanceWorld world, boolean firstEntrance) {
		if (firstEntrance) {
			world.addAllowed(player.getObjectId());
		}
		((PailakaVarkaSilenosBarracksWorld) world).storeTime = System.currentTimeMillis(); // TODO: Verify.
		teleportPlayer(player, ENTRY_POINT, world.getInstanceId());
		_log.info(getClass().getSimpleName() + " instance " + world.getInstanceId() + " by player: " + player.getName());
	}
}
