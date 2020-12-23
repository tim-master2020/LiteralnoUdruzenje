package tim22.upp.LiteralnoUdruzenje.model.camundaCustomTypes;

import org.camunda.bpm.engine.ProcessEngineException;
import org.camunda.bpm.engine.impl.form.type.AbstractFormFieldType;
import org.camunda.bpm.engine.impl.form.type.FormTypes;
import org.camunda.bpm.engine.impl.form.type.StringFormType;
import org.camunda.bpm.engine.variable.Variables;
import org.camunda.bpm.engine.variable.value.TypedValue;

import java.util.List;

public class CustomType extends StringFormType {

    private String name;

    public CustomType(String name){
        this.name = name;
    }

   @Override
    public String getName(){
        return "string_".concat(name);
   }

}
