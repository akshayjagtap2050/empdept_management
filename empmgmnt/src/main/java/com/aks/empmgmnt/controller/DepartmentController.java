package com.aks.empmgmnt.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.aks.empmgmnt.dto.EmpRequestDto;
import com.aks.empmgmnt.entity.Department;
import com.aks.empmgmnt.service.DepartmentService;

@Controller
public class DepartmentController {

	Logger logger = LoggerFactory.getLogger(DepartmentController.class);

	@Autowired
	DepartmentService departmentService;

	@GetMapping("/dept/getDeprtment")
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
	public String registration(@ModelAttribute("dept") Department dept, Model model) {

		logger.info("called: save deparmtent" + dept.getDeptName());
		departmentService.saveDepartment(dept);

		return "redirect:/dept/getDeprtment";
	}

	@GetMapping("/showFormForDeptUpdate/{id}")
	public String showFormForUpdate(@PathVariable(value = "id") Integer id, Model model) {

		Department department = departmentService.findDeptById(id);
		//logger.info("called: update deparmtent" + department.getDeptName());

		model.addAttribute("dept", department);
		return "updateDept";
	}

	@PostMapping("/updateDept")
	public String updateDept(@ModelAttribute("dept") Department department, Model model) {

		logger.info("called update dept:" + department.getDeptName());
		departmentService.updateDepartment(department);

		return "redirect:/dept/getDeprtment";
	}

}
