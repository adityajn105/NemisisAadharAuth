package com.morpho.morphosmart.sdk;

import java.util.ArrayList;

/**
 * This class contains the list of users. 
 * Warning: this class is not linked to the internal database.
 * When a record is added or removed from the database, this object is not updated. 
 */
public class MorphoUserList {
	private ArrayList<MorphoUser> morphoUsers = new ArrayList<MorphoUser>();
	
	
	/**
	 * Get user identified by an index
	 * @param input index
	 * @return Corresponding MorphoUser object 
	 */
	public MorphoUser getUser(int index)
	{
		if(index < morphoUsers.size())
		{			
			return morphoUsers.get(index);
		}
		else
		{			
			return null;
		}
	}
	/**
	 * Get number of users corresponding to the current list
	 * @param None
	 * @return number of users
	 */
	
	public int getNbUser()
	{
		return morphoUsers.size();
	}
	
	@SuppressWarnings("unused")
	private void addUser(MorphoUser morphoUser)
	{
		morphoUsers.add(morphoUser);
	}
}
