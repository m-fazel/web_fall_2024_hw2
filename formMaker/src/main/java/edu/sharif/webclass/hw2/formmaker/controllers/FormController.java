package edu.sharif.webclass.hw2.formmaker.controllers;

import edu.sharif.webclass.hw2.formmaker.models.Field;
import edu.sharif.webclass.hw2.formmaker.models.Form;
import edu.sharif.webclass.hw2.formmaker.services.FormService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/forms")
public class FormController {

    @Autowired
    private FormService formService;

    @GetMapping("/")
    public ResponseEntity<?> getForms() {
        List<Form> forms = formService.getForms();
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("forms", forms);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/")
    public ResponseEntity<?> createForm(@RequestBody Form form) {
        try {
            Form createdForm = formService.createForm(form);
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("form", createdForm);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "خطا در ایجاد فرم. لطفاً از صحت اطلاعات مطمئن شوید و دوباره تلاش کنید.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getFormById(@PathVariable Long id) {
        Optional<Form> form = formService.getFormById(id);
        if (form.isPresent()) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("form", form.get());
            return ResponseEntity.ok(response);
        } else {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "فرم مورد نظر پیدا نشد.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateForm(@PathVariable Long id, @RequestBody Form form) {
        Optional<Form> updatedForm = formService.updateForm(id, form);
        if (updatedForm.isPresent()) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("form", updatedForm.get());
            return ResponseEntity.ok(response);
        } else {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "فرم مورد نظر برای بروزرسانی پیدا نشد.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteForm(@PathVariable Long id) {
        if (formService.deleteForm(id)) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            return ResponseEntity.ok(response);
        } else {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "فرم مورد نظر پیدا نشد.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @GetMapping("/{id}/fields")
    public ResponseEntity<?> getFormFields(@PathVariable Long id) {
        Optional<List<Field>> fields = formService.getFormFields(id);
        Map<String, Object> response = new HashMap<>();
        if (fields.isPresent()) {
            response.put("success", true);
            response.put("fields", fields.get());
            return ResponseEntity.ok(response);
        } else {
            response.put("success", false);
            response.put("message", "فرم مورد نظر یافت نشد.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @PutMapping("/{id}/fields")
    public ResponseEntity<?> updateFormFields(@PathVariable Long id, @RequestBody List<Field> fields) {
        Optional<Form> updatedForm;
        try{
            updatedForm = formService.updateFormFields(id, fields);
        } catch (Exception e){
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "خطا در ایجاد فرم. لطفاً از صحت اطلاعات مطمئن شوید و دوباره تلاش کنید.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        Map<String, Object> response = new HashMap<>();
        if (updatedForm.isPresent()) {
            response.put("success", true);
            response.put("form", updatedForm.get());
            return ResponseEntity.ok(response);
        } else {
            response.put("success", false);
            response.put("message", "فرم برای بروزرسانی فیلدها پیدا نشد.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @PostMapping("/{id}/publish")
    public ResponseEntity<?> togglePublishForm(@PathVariable Long id) {
        Optional<Form> toggledForm = formService.togglePublishForm(id);
        if (toggledForm.isPresent()) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("form", toggledForm.get());
            return ResponseEntity.ok(response);
        } else {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "فرم برای انتشار پیدا نشد.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @GetMapping("/published")
    public ResponseEntity<?> getPublishedForms() {
        List<Form> publishedForms = formService.getPublishedForms();
        Map<String, Object> response = new HashMap<>();
        if (publishedForms.isEmpty()) {
            response.put("success", false);
            response.put("message", "فرم منتشر شده ای وجود ندارد.");
            return ResponseEntity.ok(response);
        } else {
            response.put("success", true);
            response.put("forms", publishedForms);
            return ResponseEntity.ok(response);
        }
    }
}
