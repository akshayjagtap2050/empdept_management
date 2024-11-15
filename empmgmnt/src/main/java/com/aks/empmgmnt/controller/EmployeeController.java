package com.aks.empmgmnt.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.aks.empmgmnt.dto.EmpRequestDto;
import com.aks.empmgmnt.entity.Employee;
import com.aks.empmgmnt.service.EmployeeService;

@Controller
public class EmployeeController {

	Logger logger = LoggerFactory.getLogger(EmployeeController.class);

	@Autowired
	EmployeeService employeeService;

	@GetMapping("/login")
	public String login() {
		return "login";
	}

	@GetMapping("/allEmployee")
	public String getAllEmployee(Model model) {

		model.addAttribute("emps", employeeService.getAllEmployeeList());

		return "allEmployee";
	}

	@GetMapping("/createEmployee")
	public String createEmployee(Model model) {
		EmpRequestDto empRequestDto = new EmpRequestDto();
		model.addAttribute("user", empRequestDto);
		return "createEmp";
	}

	@GetMapping("/deleteEmployee/{id}")
	public String deleteEmployee(@PathVariable(value = "id") Integer id) {

		// call delete employee method
		employeeService.deleteEmployeeById(id);
		return "redirect:/allEmployee";
	}

	@PostMapping("/save")
	public String registration(@ModelAttribute("user") EmpRequestDto userDto, Model model) {

		logger.info("called: save employee");
		employeeService.saveEmployee(userDto);

		return "redirect:/allEmployee";
	}

	@PostMapping("/updateEmp")
	public String updateEmp(@ModelAttribute("user") EmpRequestDto userDto, Model model) {

		logger.info("called update employee:" + userDto.getEmpId());
		employeeService.updateEmployee(userDto);

		return "redirect:/allEmployee";
	}

	@GetMapping("/homepage")
	public String getHomePage(Model model) {

		return "homepage";
	}

	@GetMapping("/showFormForUpdate/{id}")
	public String showFormForUpdate(@PathVariable(value = "id") Integer id, Model model) {

		// get employee from the service
		EmpRequestDto employee = employeeService.findEmpById(id);

		// set employee as a model attribute to pre-populate the form
		model.addAttribute("user", employee);
		return "updateEmp";
	}

}
