package tim22.upp.LiteralnoUdruzenje.model.camundaCustomTypes;

import org.camunda.bpm.engine.ProcessEngineException;
import org.camunda.bpm.engine.impl.form.type.EnumFormType;
import org.camunda.bpm.engine.variable.Variables;
import org.camunda.bpm.engine.variable.value.TypedValue;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MultiEnumType extends EnumFormType {

    private String name;

    private String minValue;

    private String maxValue;

    public MultiEnumType(Map<String, String> values) {
        super(values);
    }

    public MultiEnumType(String name) {
        super(new HashMap<>());
        this.name = name;
    }

    public MultiEnumType(String name,String minValue,String maxValue) {
        super(new HashMap<>());
        this.name = name;
        this.minValue = minValue;
        this.maxValue = maxValue;
    }

    public String getName() {
        if(minValue != null && maxValue != null) {
            return "multiEnum_".concat(name) + "_".concat(minValue) + "_".concat(maxValue);
        }else{
            return "multiEnum_".concat(name);
        }
    }

    public TypedValue convertValue(TypedValue propertyValue) {
        Object value = propertyValue.getValue();
        return value == null ? Variables.stringValue((String) null, propertyValue.isTransient()) : Variables.stringValue(value.toString(), propertyValue.isTransient());
    }

    public String convertModelValueToFormValue(Object modelValue) {
        if (modelValue != null) {
            if (!(modelValue instanceof List)) {
                throw new ProcessEngineException("Model value should be a List");
            }
        }

        return modelValue.toString();
    }
}
