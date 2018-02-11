/*  Planner V2. Application that allows scheduling meetings and appointments.
    Copyright (C) 2018 Bartosz Mykowski

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <https://www.gnu.org/licenses/>.
*/

package com.xaffax.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "planner")
public class Planner
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "plan_id")
	private Integer	plan_id;

	@Column(name = "user_name")
	private String	user_name;

	@Column(name = "room")
	private Integer	room;

	@Column(name = "fromDate")
	@DateTimeFormat(pattern = "dd-MM-yyyy HH:mm")
	private Date	fromDate;

	@Column(name = "toDate")
	@DateTimeFormat(pattern = "dd-MM-yyyy HH:mm")
	private Date	toDate;

	public Integer getPlan_id()
	{
		return plan_id;
	}

	public void setPlan_id(Integer plan_id)
	{
		this.plan_id = plan_id;
	}

	public Integer getRoom()
	{
		return room;
	}

	public void setRoom(Integer room)
	{
		this.room = room;
	}

	public String getUser_name()
	{
		return user_name;
	}

	public void setUser_name(String user_name)
	{
		this.user_name = user_name;
	}

	public Date getFromDate()
	{
		return fromDate;
	}

	public void setFromDate(Date fromDate)
	{
		this.fromDate = fromDate;
	}

	public Date getToDate()
	{
		return toDate;
	}

	public void setToDate(Date toDate)
	{
		this.toDate = toDate;
	}

	@Override
	public String toString()
	{
		return "Planner [plan_id=" + plan_id + ", user_name=" + user_name
				+ ", room=" + room + ", fromDate=" + fromDate + ", toDate="
				+ toDate + "]";
	}
}