package com.l2jserver.datapack.handlers.effecthandlers.instant;

import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.powermock.api.easymock.PowerMock.replayAll;

import java.util.List;
import java.util.Map;

import org.powermock.api.easymock.annotation.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import com.l2jserver.datapack.test.AbstractTest;
import com.l2jserver.gameserver.model.L2Party;
import com.l2jserver.gameserver.model.StatsSet;
import com.l2jserver.gameserver.model.actor.L2Character;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.skills.BuffInfo;

/**
 * Instant Call Target Party effect test.
 * @author Zoey76
 * @version 2.6.2.0
 */
@PrepareForTest(BuffInfo.class)
public class InstantCallTargetPartyTest extends AbstractTest {
	
	@Mock
	private BuffInfo buffInfo;
	@Mock
	private L2Character effected;
	@Mock
	private L2Party party;
	@Mock
	private L2PcInstance effectedPlayer;
	@Mock
	private L2PcInstance partyMember;
	
	private InstantCallTargetParty effect;
	
	@BeforeSuite
	void init() {
		final var set = new StatsSet(Map.of("name", "InstantBetray"));
		final var params = new StatsSet(Map.of("chance", "80", "time", "30"));
		effect = new InstantCallTargetParty(null, null, set, params);
	}
	
	@Test
	void test_target_not_in_party() {
		expect(buffInfo.getEffected()).andReturn(effected);
		expect(effected.isInParty()).andReturn(false);
		replayAll();
		
		effect.onStart(buffInfo);
	}
	
	@Test
	void test_target_in_party_cannot_summon_party_member() {
		expect(buffInfo.getEffected()).andReturn(effected);
		expect(effected.isInParty()).andReturn(true);
		expect(effected.getParty()).andReturn(party);
		expect(party.getMembers()).andReturn(List.of(effectedPlayer, partyMember));
		expect(effected.getActingPlayer()).andReturn(effectedPlayer);
		expect(effectedPlayer.canSummonTarget(partyMember)).andReturn(false);
		expect(effectedPlayer.canSummonTarget(effectedPlayer)).andReturn(false);
		replayAll();
		
		effect.onStart(buffInfo);
	}
	
	@Test
	void test_target_in_party_can_summon_party_member() {
		expect(buffInfo.getEffected()).andReturn(effected);
		expect(effected.isInParty()).andReturn(true);
		expect(effected.getParty()).andReturn(party);
		expect(party.getMembers()).andReturn(List.of(effectedPlayer, partyMember));
		expect(effected.getActingPlayer()).andReturn(effectedPlayer);
		expect(effectedPlayer.canSummonTarget(partyMember)).andReturn(true);
		expect(effectedPlayer.canSummonTarget(effectedPlayer)).andReturn(false);
		partyMember.teleToLocation(effected, true);
		expectLastCall();
		replayAll();
		
		effect.onStart(buffInfo);
	}
}
