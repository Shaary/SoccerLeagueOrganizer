package com.teamtreehouse.model;

import java.util.Set;
import java.util.TreeSet;

public class Team implements Comparable<Team>{
  private String mTeamName;
  private String mCoachName;
  private final int NUMBER_OF_PLAYERS = 11;
  private Set<Player> mListOfPlayers;
  
  public Team(String teamName, String coachName) {
    mTeamName = teamName.trim().toUpperCase();
    mCoachName = coachName.trim().toLowerCase();
    mListOfPlayers = new TreeSet<Player>();
  }
  
  @Override
  public String toString() {
    return String.format("'%s' coached by %s", mTeamName, mCoachName);
  }
  
  @Override
  public int compareTo(Team other) {
    return mTeamName.compareTo(other.mTeamName);
  }
  public String getTeamName() {
    return mTeamName;
  }
  
  public String getCoachName() {
    return mCoachName;
  }
  
  public Set<Player> getListOfPlayers() {
    return mListOfPlayers;
  }
  public void addPlayer(Player player, Team team) {
	int amountOfPlayers = mListOfPlayers.size(); 
	if (amountOfPlayers <= NUMBER_OF_PLAYERS) {
    mListOfPlayers.add(player);
    System.out.printf("You added %s to team '%s'%n", 
    		           player.getName(), team.getTeamName());
	} else {
	  System.out.println("There are 11 players in your team. You can't add more!");
	}
  }
  
  public void removePlayer(Player player) {
    mListOfPlayers.remove(player);
  }
  
  public String balance() {
    int expCount = 0;
    int inexpCount = 0;
    
    for (Player player : mListOfPlayers) {
      if (player.isPreviousExperience() == true) {
        expCount++;
      } else {
        inexpCount++;
      }
    }
    return (String.format("Team %s: %d experiensed and %d unexperienced players %n", 
            mTeamName, expCount, inexpCount, mTeamName));
  }
}