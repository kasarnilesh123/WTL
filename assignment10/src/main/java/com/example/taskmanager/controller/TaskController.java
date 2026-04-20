package com.example.taskmanager.controller;

import com.example.taskmanager.model.Employee;
import com.example.taskmanager.model.Task;
import com.example.taskmanager.repository.EmployeeRepository;
import com.example.taskmanager.repository.TaskRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
public class TaskController {

    private final EmployeeRepository employeeRepository;
    private final TaskRepository taskRepository;

    public TaskController(EmployeeRepository employeeRepository, TaskRepository taskRepository) {
        this.employeeRepository = employeeRepository;
        this.taskRepository = taskRepository;
    }

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("employeeCount", employeeRepository.count());
        model.addAttribute("taskCount", taskRepository.count());
        return "index";
    }

    @GetMapping("/employees")
    public String listEmployees(Model model) {
        model.addAttribute("employees", employeeRepository.findAll());
        model.addAttribute("newEmployee", new Employee());
        return "employees";
    }

    @PostMapping("/employees")
    public String addEmployee(@ModelAttribute Employee employee) {
        employeeRepository.save(employee);
        return "redirect:/employees";
    }

    @GetMapping("/tasks")
    public String listTasks(Model model) {
        model.addAttribute("tasks", taskRepository.findAll());
        model.addAttribute("employees", employeeRepository.findAll());
        model.addAttribute("newTask", new Task());
        return "tasks";
    }

    @PostMapping("/tasks")
    public String addTask(@ModelAttribute Task task, @RequestParam Long employeeId) {
        Optional<Employee> employee = employeeRepository.findById(employeeId);
        if (employee.isPresent()) {
            task.setAssignee(employee.get());
            if (task.getStatus() == null || task.getStatus().isEmpty()) {
                task.setStatus("PENDING");
            }
            taskRepository.save(task);
        }
        return "redirect:/tasks";
    }

    @PostMapping("/tasks/{id}/status")
    public String updateTaskStatus(@PathVariable Long id, @RequestParam String status) {
        Optional<Task> taskOpt = taskRepository.findById(id);
        if (taskOpt.isPresent()) {
            Task task = taskOpt.get();
            task.setStatus(status);
            taskRepository.save(task);
        }
        return "redirect:/tasks";
    }

    @PostMapping("/employees/delete/{id}")
    public String deleteEmployee(@PathVariable Long id) {
        employeeRepository.deleteById(id);
        return "redirect:/employees";
    }

    @PostMapping("/tasks/delete/{id}")
    public String deleteTask(@PathVariable Long id) {
        taskRepository.deleteById(id);
        return "redirect:/tasks";
    }
}
