/*
 * Copyright © 2004-2021 L2J DataPack
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
package com.l2jserver.datapack.handlers.admincommandhandlers;

import com.l2jserver.gameserver.handler.IAdminCommandHandler;
import com.l2jserver.gameserver.model.L2Object;
import com.l2jserver.gameserver.model.L2World;
import com.l2jserver.gameserver.model.actor.ActorDebugCategory;
import com.l2jserver.gameserver.model.actor.L2Character;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.network.SystemMessageId;

public class AdminDebug implements IAdminCommandHandler {
	private static final String ADMIN_DEBUG = "admin_debug";
	private static final String ADMIN_DEBUG_HELP = "admin_debug_help";
	private static final String ADMIN_DEBUG_CATEGORY = "admin_debug_category";
	
	private static final String[] ADMIN_COMMANDS = {
		ADMIN_DEBUG_HELP,
		ADMIN_DEBUG,
		ADMIN_DEBUG_CATEGORY
	};
	
	@Override
	public final boolean useAdminCommand(String command, L2PcInstance activeChar) {
		String[] commandSplit = command.split(" ");
		switch (commandSplit[0]) {
			case ADMIN_DEBUG_HELP: {
				showDebugHelp(activeChar);
			}
			case ADMIN_DEBUG: {
				L2Character target = getDebugCharacter(activeChar, commandSplit.length > 1 ? commandSplit[1] : null);
				if (target == null) {
					activeChar.sendPacket(SystemMessageId.TARGET_IS_NOT_FOUND_IN_THE_GAME);
					break;
				}
				setDebug(activeChar, target);
			}
			case ADMIN_DEBUG_CATEGORY: {
				if (commandSplit.length < 2) {
					showWrongUsage(activeChar, "Not enough parameters!");
					break;
				}
				
				ActorDebugCategory category = null;
				try {
					category = ActorDebugCategory.valueOf(commandSplit[1]);
				} catch (Exception e) {
					showWrongUsage(activeChar, commandSplit[1] + " is not a valid ActorDebugCategory!");
					break;
				}
				
				L2Character target = getDebugCharacter(activeChar, commandSplit.length > 2 ? commandSplit[2] : null);
				if (target == null) {
					activeChar.sendPacket(SystemMessageId.TARGET_IS_NOT_FOUND_IN_THE_GAME);
					break;
				}
				activeChar.sendMessage("Debug category " + category.name() + " has been " + (target.toggleDebugCategory(category) ? "enabled" : "disabled") + ".");
			}
		}
		return true;
	}
	
	@Override
	public final String[] getAdminCommandList() {
		return ADMIN_COMMANDS;
	}
	
	private final L2Character getDebugCharacter(L2PcInstance activeChar, String playerName) {
		L2Object target = null;
		if (playerName != null) {
			target = L2World.getInstance().getPlayer(playerName.trim());
		} else {
			target = activeChar.getTarget();
			if (target == null) {
				target = activeChar;
			}
		}
		
		if (target instanceof L2Character) {
			return (L2Character) target;
		}
		return null;
	}
	
	private final void showWrongUsage(L2PcInstance activeChar, String msg) {
		activeChar.sendMessage(msg);
		activeChar.sendMessage("Type //debug_help for help.");
	}
	
	private final void showDebugHelp(L2PcInstance activeChar) {
		activeChar.sendMessage("//debug [playerName]");
		activeChar.sendMessage("  Description:");
		activeChar.sendMessage("    Start to debug an Actor.");
		activeChar.sendMessage("  Parameters:");
		activeChar.sendMessage("    playerName - name of the player to debug");
		activeChar.sendMessage("  Notes:");
		activeChar.sendMessage("    Without parameter or target you debug yourself");
		
		activeChar.sendMessage("//debug_help");
		activeChar.sendMessage("  Description:");
		activeChar.sendMessage("    Show this help.");
		
		activeChar.sendMessage("//debug_gui");
		activeChar.sendMessage("  Description:");
		activeChar.sendMessage("    Show the debug GUI.");
		
		activeChar.sendMessage("//debug_category debugCategory [playerName]");
		activeChar.sendMessage("  Description:");
		activeChar.sendMessage("    Toggle the given debug category on/off.");
		activeChar.sendMessage("  Parameters:");
		activeChar.sendMessage("    debugCategory - category to toggle");
		for (ActorDebugCategory category : ActorDebugCategory.values()) {
			activeChar.sendMessage("      " + category.name());
		}
		activeChar.sendMessage("    playerName - name of the player to debug");
		activeChar.sendMessage("  Notes:");
		activeChar.sendMessage("    Without parameter or target you debug yourself");
	}
	
	private final void setDebug(L2PcInstance activeChar, L2Character target) {
		if (target.isDebug()) {
			target.setDebug(null);
			activeChar.sendMessage("Stop debugging " + target.getName());
		} else {
			target.setDebug(activeChar);
			activeChar.sendMessage("Start debugging " + target.getName());
		}
	}
}