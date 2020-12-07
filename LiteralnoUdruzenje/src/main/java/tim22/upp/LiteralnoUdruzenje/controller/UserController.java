
    @GetMapping(path = "/regtask", produces = "application/json")
    public @ResponseBody FormFieldsDTO getFormFieldsReaderRegistration() {

        ProcessInstance pi = runtimeService.startProcessInstanceByKey("ReaderRegistration");
        Task task = taskService.createTaskQuery().processInstanceId(pi.getId()).list().get(0);
        TaskFormData taskFormData = formService.getTaskFormData(task.getId());
        List<FormField> properties = taskFormData.getFormFields();
        return new FormFieldsDTO(task.getId(), pi.getId(), properties);
    }

    @GetMapping(path = "/logintask", produces = "application/json")
    public @ResponseBody FormFieldsDTO getFormFieldsLoginForm() {

        ProcessInstance pi = runtimeService.startProcessInstanceByKey("LoginProcess");
        Task task = taskService.createTaskQuery().processInstanceId(pi.getId()).list().get(0);
        TaskFormData taskFormData = formService.getTaskFormData(task.getId());
        List<FormField> properties = taskFormData.getFormFields();
        return new FormFieldsDTO(task.getId(), pi.getId(), properties);
    }
