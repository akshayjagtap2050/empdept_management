package com.aks.empmgmnt.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.aks.empmgmnt.dto.DeptRequestDto;
import com.aks.empmgmnt.dto.EmpRequestDto;
import com.aks.empmgmnt.entity.Department;
import com.aks.empmgmnt.service.DepartmentService;

import jakarta.validation.Valid;

@Controller
@Validated
@RequestMapping("/dept")
public class DepartmentController {

	Logger logger = LoggerFactory.getLogger(DepartmentController.class);

	@Autowired
	DepartmentService departmentService;

	@GetMapping("/getDeprtment")
	public String getDeprtment(Model model) {

		model.addAttribute("depts", departmentService.getAllDepartmentList());

		return "getDepartment";
	}

	@GetMapping("/addDepartment")
	public String addDepartment(Model model) {
		Department dept = new Department();
		model.addAttribute("dept", dept);
		return "createDepartment";
	}

	@PostMapping("/saveDept")
	public String registration(@ModelAttribute("dept") @Valid DeptRequestDto dept, Model model,
			BindingResult bindingResult) {

		// if (bindingResult.hasErrors()) {
		// logger.info("save dept validation error");
		// return from with error page
		// }

		logger.info("called: save deparmtent" + dept.getDeptName());
		departmentService.saveDepartment(dept);

		return "redirect:/dept/getDeprtment";
	}

	@GetMapping("/showFormForDeptUpdate/{id}")
	public String showFormForUpdate(@PathVariable(value = "id") Integer id, Model model) {

		logger.info("called: showFormForDeptUpdate id: " + id);

		model.addAttribute("dept", departmentService.findDeptById(id));

		return "updateDept";
	}

	@PutMapping("/updateDept")
	public String updateDept(@ModelAttribute("dept") @Valid DeptRequestDto deptRequestDto, Model model) {

		logger.info("called update dept:" + deptRequestDto.getDeptName());
		departmentService.updateDepartment(deptRequestDto);

		return "redirect:/dept/getDeprtment";
	}

}
