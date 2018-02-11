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

package com.xaffax.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xaffax.dao.PlannerDAO;
import com.xaffax.entities.Planner;

@Service
public class PlannerServiceImp implements PlannerService
{

	@Autowired
	PlannerDAO plannerDAO;

	@Transactional
	public String saveOrUpdate(Planner plan)
	{
		return plannerDAO.saveOrUpdate(plan);
	}

	@Transactional
	public List<Planner> listPlanners()
	{
		return plannerDAO.listPlanners();
	}

	@Transactional
	public void delete(int id)
	{
		plannerDAO.delete(id);
		return;
	}

	@Transactional
	public Planner getPlannerByID(int id)
	{
		return plannerDAO.getPlannerByID(id);
	}

}