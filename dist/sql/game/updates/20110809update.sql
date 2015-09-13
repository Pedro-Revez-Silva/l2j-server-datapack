ALTER TABLE `custom_npcaidata` CHANGE `npc_id` `npcId` mediumint(7) unsigned NOT NULL;
ALTER TABLE `custom_npcaidata` ADD `minSkillChance` tinyint(3) unsigned NOT NULL DEFAULT '7' AFTER `npcId`;
ALTER TABLE `custom_npcaidata` CHANGE `skill_chance` `maxSkillChance` tinyint(3) unsigned NOT NULL DEFAULT '15';
ALTER TABLE `custom_npcaidata` CHANGE `primary_attack` `primarySkillId` smallint(5) unsigned DEFAULT '0';
ALTER TABLE `custom_npcaidata` CHANGE `can_move` `canMove` tinyint(1) unsigned NOT NULL DEFAULT '1';
ALTER TABLE `custom_npcaidata` CHANGE `minrangeskill` `minRangeSkill` smallint(5) unsigned DEFAULT '0';
ALTER TABLE `custom_npcaidata` CHANGE `minrangechance` `minRangeChance` tinyint(3) unsigned DEFAULT '0';
ALTER TABLE `custom_npcaidata` CHANGE `maxrangeskill` `maxRangeSkill` smallint(5) unsigned DEFAULT '0';
ALTER TABLE `custom_npcaidata` CHANGE `maxrangechance` `maxRangeChance` tinyint(3) unsigned DEFAULT '0';
ALTER TABLE `custom_npcaidata` CHANGE `soulshot` `soulShot` smallint(4) unsigned DEFAULT '0';
ALTER TABLE `custom_npcaidata` CHANGE `spiritshot` `spiritShot` smallint(4) unsigned DEFAULT '0';
ALTER TABLE `custom_npcaidata` CHANGE `spschance` `spsChance` tinyint(3) unsigned DEFAULT '0';
ALTER TABLE `custom_npcaidata` CHANGE `sschance` `ssChance` tinyint(3) unsigned DEFAULT '0';
ALTER TABLE `custom_npcaidata` CHANGE `ischaos` `isChaos` smallint(4) unsigned DEFAULT '0';
ALTER TABLE `custom_npcaidata` MODIFY `clan` varchar(40) DEFAULT NULL;
ALTER TABLE `custom_npcaidata` CHANGE `clan_range` `clanRange` smallint(4) unsigned DEFAULT '0';
ALTER TABLE `custom_npcaidata` MODIFY `enemyRange` smallint(4) unsigned DEFAULT '0' AFTER `enemyClan`;
ALTER TABLE `custom_npcaidata` MODIFY `dodge` tinyint(3) unsigned DEFAULT '0';
ALTER TABLE `custom_npcaidata` CHANGE `ai_type` `aiType` varchar(8) NOT NULL DEFAULT 'fighter';
ALTER TABLE `custom_npcaidata` DROP PRIMARY KEY; 
ALTER TABLE `custom_npcaidata` ADD PRIMARY KEY (`npcId`);