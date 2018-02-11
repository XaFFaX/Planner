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

package com.xaffax.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.xaffax.entities.Planner;

@Component
public class PlanValidator implements Validator
{

	public boolean supports(Class<?> clas)
	{
		return Planner.class.equals(clas);
	}

	public void validate(Object o, Errors err)
	{
		ValidationUtils.rejectIfEmpty(err, "user_name",
				"planner.user_name.empty");
		ValidationUtils.rejectIfEmpty(err, "fromDate",
				"planner.fromDate.empty");
		ValidationUtils.rejectIfEmpty(err, "toDate", "planner.toDate.empty");

	}

}
