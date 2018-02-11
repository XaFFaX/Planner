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

package com.xaffax;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.xaffax.entities.Planner;
import com.xaffax.service.PlannerService;

@Controller
public class PlannerCTRL
{
	private static final Logger	logger	= LoggerFactory
			.getLogger(PlannerCTRL.class);

	private PlannerService		plannerService;

	@Autowired(required = true)
	public void setPlannerService(PlannerService ps)
	{
		this.plannerService = ps;
	}

	@RequestMapping(value =
	{ "/", "/planners" }, method = RequestMethod.GET)
	public String listPlanner(Model model)
	{
		model.addAttribute("planner", new Planner());
		model.addAttribute("listPlanners", this.plannerService.listPlanners());
		return "planner";
	}

	@InitBinder
	public void initBinder(WebDataBinder binder)
	{
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm");
		dateFormat.setLenient(true);
		binder.registerCustomEditor(Date.class,
				new CustomDateEditor(dateFormat, false));
	}

	// For adding and updating planner
	@RequestMapping(value = "/planner/add", method = RequestMethod.POST)
	public String addPlanner(@ModelAttribute("planner") Planner p,
			BindingResult result, RedirectAttributes redirectAttributes)
	{
		String message = null;
		logger.info("Planner details: " + p);
		message = this.plannerService.saveOrUpdate(p);
		redirectAttributes.addFlashAttribute("DATE_PICK_RESULT", message);
		return "redirect:/planners";
	}

	@RequestMapping("/remove/{plan_id}")
	public String removePlanner(@PathVariable("plan_id") int id)
	{

		this.plannerService.delete(id);
		return "redirect:/planners";
	}

	@RequestMapping("/edit/{plan_id}")
	public String editPlanner(@PathVariable("plan_id") int id, Model model)
	{
		model.addAttribute("planner", this.plannerService.getPlannerByID(id));
		model.addAttribute("listPlanners", this.plannerService.listPlanners());
		return "planner";
	}

}
