/*
 * Copyright (C) 2004-2013 L2J DataPack
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
package handlers.effecthandlers;

import com.l2jserver.gameserver.ai.CtrlIntention;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.effects.EffectFlag;
import com.l2jserver.gameserver.model.effects.EffectTemplate;
import com.l2jserver.gameserver.model.effects.L2Effect;
import com.l2jserver.gameserver.model.effects.L2EffectType;
import com.l2jserver.gameserver.model.stats.Env;

/**
 * Betray effect implementation.
 * @author decad
 */
public class Betray extends L2Effect
{
	public Betray(Env env, EffectTemplate template)
	{
		super(env, template);
	}
	
	@Override
	public int getEffectFlags()
	{
		return EffectFlag.BETRAYED.getMask();
	}
	
	@Override
	public L2EffectType getEffectType()
	{
		return L2EffectType.BETRAY;
	}
	
	@Override
	public void onExit()
	{
		getEffected().getAI().setIntention(CtrlIntention.AI_INTENTION_IDLE);
	}
	
	@Override
	public boolean onStart()
	{
		if (getEffector().isPlayer() && getEffected().isSummon())
		{
			L2PcInstance targetOwner = getEffected().getActingPlayer();
			getEffected().getAI().setIntention(CtrlIntention.AI_INTENTION_ATTACK, targetOwner);
			return true;
		}
		return false;
	}
}