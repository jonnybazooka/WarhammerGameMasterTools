# Warhammer Game Master's Toolkit
This application was developed for personal use as a tool for game master. It's origilan purpose was to track combat in Warhammer RPG 4th edition. Some additional features were added during development.

## Features:
### Main View
![Main Window](https://i.ibb.co/r6v642D/GMT-Main-Screen.png)

**Save Adventurer**

Saves a new adventurer to a file named *adventurers.txt*. This file holds information on every adventurer in a group. Input fields ```Adventurer's Name```, ```Initiative``` and ```Perceprion``` are required.

**Load Adventurers**

Loads all adventurers and displays them in main view with corresponding stats.

**Update Adventurer**

Updates attributes of existing adventurer. Input field ```Adventurer's Name``` must corresppond to an existing adventurer. Both fields ```Initiative``` and ```Perceprion``` are required. New input will replace the old.

**Delete Adventurer**

Deletes an adventurer. Input field ```Adventurer's Name``` is required and must corresppond to an existing adventurer.

**Add Opponent to Combat**

Creates an opponent and stores information about him for the next combat. Input fields ```Opponent's Name``` and ```Initiative``` are required.

**Start Combat**

Loads all adventurers and opponents and makes random rolls for initiative according to WFRP 4ed rules. Subsequently arranges all combatants in order from quickest to slowest and displays them. Example below:

![Combat Example](https://i.ibb.co/f0N2DQ9/GMT-Combat-Example.png)

Main view is editable, so gamemaster can easily track all wounds and status effects like stuns, poison etc.

**Perception Check**

Sometimes game master need to do a perception check for the players to see if they can spot a hidden danger or treasure. Usually it's best if the players don't know if they succeded of failed or even if the check was made in the first place. This button allows to make such a check with just one click. Results are shown as *Success Levels*. Example:

![Perception Check Example](https://i.ibb.co/6gmJXkr/GMT-Perception-Check.png)

**Random Names Generator Buttons**

These buttons allow for quick generation of names for non player characters. Generator uses *.csv* files embeded in *.jar* archive. Each file contains around 250 names and almost the same amount of surnames and nicknames for each race and gender. Additionally generator takes into account some special rules for generating names for non-human races.

**Save NPC**

If there is a chance that given NPC will return in future adventures, game master may want to save information about such NPC. After generating name, game master can write any additional information he wishes and save NPC. All NPC information is stored in a *npcs.txt* file.

**Load NPC**

Load all NPCs and displays information about them. Example:

![Load NPCs Example](https://i.ibb.co/SvQ0k0t/GMT-Load-NPCs.png)

**Update NPCs**

All loaded information about NPCs can be modified. If game master wishes to add something or remove, this button serves just that purpose.
