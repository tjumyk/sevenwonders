package org.tjuscs.sevenwonders.net;

import java.io.Serializable;
/**
 * 
 * @author CSDN ID:zhouyuqwert
 *
 */
public class User implements Serializable
{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String userName;

	public User(String userName)
	{
		super();
		this.userName = userName;
	}

	public String getUserName()
	{
		return userName;
	}

	public void setUserName(String userName)
	{
		this.userName = userName;
	}
	
	
}
