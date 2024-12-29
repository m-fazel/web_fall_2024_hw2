package edu.sharif.webclass.hw2.formmaker.services;

import edu.sharif.webclass.hw2.formmaker.models.Field;
import edu.sharif.webclass.hw2.formmaker.models.Form;
import edu.sharif.webclass.hw2.formmaker.repositories.FieldRepository;
import edu.sharif.webclass.hw2.formmaker.repositories.FormRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class FormService {
    @Autowired
    private FormRepository formRepository;

    @Autowired
    private FieldRepository fieldRepository;

    public List<Form> getForms() {
        return formRepository.findAll();
    }

    public Form createForm(Form form) {
        return formRepository.save(form);
    }

    public Optional<Form> getFormById(Long id) {
        return formRepository.findById(id);
    }

    public Optional<Form> updateForm(Long id, Form updatedForm) {
        Optional<Form> existingForm = formRepository.findById(id);
        if (existingForm.isPresent()) {
            Form form = existingForm.get();

            if (updatedForm.getName() != null) {
                form.setName(updatedForm.getName());
            }
            if (updatedForm.getStatus() != null) {
                form.setStatus(updatedForm.getStatus());
            }
            if (updatedForm.getMethod() != null) {
                form.setMethod(updatedForm.getMethod());
            }
            if (updatedForm.getFinalAddress() != null) {
                form.setFinalAddress(updatedForm.getFinalAddress());
            }

            return Optional.of(formRepository.save(form));
        }
        return Optional.empty();
    }

    public boolean deleteForm(Long id) {
        Optional<Form> form = formRepository.findById(id);
        if (form.isPresent()) {
            formRepository.delete(form.get());
            return true;
        }
        return false;
    }

    public Optional<List<Field>> getFormFields(Long id) {
        Optional<Form> form = formRepository.findById(id);
        return form.map(value -> List.copyOf(value.getFields()));
    }

    public Optional<Form> updateFormFields(Long id, List<Field> fields) {
        Optional<Form> form = formRepository.findById(id);
        if (form.isPresent()) {
            for (Field field : fields) {
                field.setForm(form.get());
            }
            Form existingForm = form.get();
            if (existingForm.getFields() == null) {
                existingForm.setFields(new ArrayList<>());
            }
            existingForm.getFields().addAll(fields);
            fieldRepository.saveAll(fields);
            form = formRepository.findById(id);
            return form;
        }
        return form;
    }

    public Optional<Form> togglePublishForm(Long id) {
        Optional<Form> form = formRepository.findById(id);
        if (form.isPresent()) {
            Form existingForm = form.get();
            existingForm.setStatus(!existingForm.getStatus());
            return Optional.of(formRepository.save(existingForm));
        }
        return Optional.empty();
    }

    public List<Form> getPublishedForms() {
        return formRepository.findByStatus(true);
    }
}
