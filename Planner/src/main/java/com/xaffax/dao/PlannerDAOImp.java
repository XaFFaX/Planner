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

package com.xaffax.dao;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.xaffax.entities.Planner;

@Repository
@Transactional
public class PlannerDAOImp implements PlannerDAO
{
	@Autowired
	SessionFactory				session;

	private static final Logger	logger	= LoggerFactory
			.getLogger(PlannerDAOImp.class);

	// checks whether 2 date ranges overlap
	private static boolean areDatesOverlapped(Date startDate1, Date endDate1,
			Date startDate2, Date endDate2)
	{
		return ((startDate1.getTime() < endDate2.getTime())
				&& (startDate2.getTime() < endDate1.getTime()));
	}

	public String saveOrUpdate(Planner plan)
	{
		boolean overlap = false;
		String message = null;
		List<Planner> allPlans = new ArrayList<>(this.listPlanners());

		// check to see if appointment is not >120 minutes and <15 minutes
		if (plan.getToDate().getTime() - plan.getFromDate().getTime() > 120 * 60
				* 1000
				|| plan.getToDate().getTime()
						- plan.getFromDate().getTime() < 15 * 60 * 1000)
		{
			message = "Scheduled time cannot be longer than 120 minutes and shorter than 15 minutes! Please update accordingly.";
			return message;
		}

		// checks for overlapping dates with all plans currently in the database
		for (Planner planElem : allPlans)
		{
			if (plan.getRoom() == planElem.getRoom())
			{
				if (areDatesOverlapped(plan.getFromDate(), plan.getToDate(),
						planElem.getFromDate(), planElem.getToDate()))
				{
					overlap = true;
					break;
				}
			}
		}

		if (overlap)
		{
			Date proposedStartDate = plan.getFromDate();
			Date proposedEndDate = plan.getToDate();
			int i = 0;
			boolean foundSlot = false;
			// this loop looks for next free slot that will be of the
			// same size as requested.
			// check is done by adding 30 minutes to requested date and checking
			// if there is an overlap,
			// if not, appropriate date is returned as a suggestion,
			// if suitable slot is not found within 20 loop cycles then
			// user is informed that no suitable slot was found
			do
			{
				proposedStartDate = new Date(
						proposedStartDate.getTime() + 1800 * 1000);
				proposedEndDate = new Date(
						proposedEndDate.getTime() + 1800 * 1000);
				for (Planner planElem : allPlans)
				{
					if (plan.getRoom() == planElem.getRoom())
					{
						if (areDatesOverlapped(proposedStartDate,
								proposedEndDate, planElem.getFromDate(),
								planElem.getToDate()))
						{
							break;
						} else
						{
							SimpleDateFormat dateFormat = new SimpleDateFormat(
									"dd-MM-yyyy HH:mm");
							message = "Sorry, selected time for room number "
									+ plan.getRoom()
									+ " is not available, proposed dates for this room: "
									+ dateFormat.format(proposedStartDate)
									+ " to "
									+ dateFormat.format(proposedEndDate);
							foundSlot = true;
							i = 100;

						}
					}
				}
			} while (i++ < 20);
			if (!foundSlot)
				message = "Unable to find any suitable timeslot within next few hours.";
		}

		else
		{
			session.getCurrentSession().saveOrUpdate(plan);
			logger.info("Plan saved successfully, Plan Details=" + plan);
			message = "Added successfully!";
		}
		return message;
	}

	@SuppressWarnings("unchecked")
	public List<Planner> listPlanners()
	{
		return session.getCurrentSession().createQuery("from Planner").list();
	}

	public void delete(int id)
	{
		Session session = this.session.getCurrentSession();
		Planner p = (Planner) session.load(Planner.class, new Integer(id));
		if (null != p)
		{
			session.delete(p);
		}
		logger.info("Person deleted successfully, person details=" + p);
	}

	public Planner getPlannerByID(int id)
	{
		Session session = this.session.getCurrentSession();
		Planner p = (Planner) session.load(Planner.class, new Integer(id));
		logger.info("Plan loaded successfully, Person details=" + p);
		return p;
	}
}
