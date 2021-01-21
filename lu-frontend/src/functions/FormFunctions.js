export function validate(field,value,setIsValid,isValid){
    field.validationConstraints.forEach(constraint => {
    
        if (constraint.name === 'required') {
            if (value === null || value === undefined || value === null || value === '') {
                setIsValid(isValid[field.id] = (`${field.id} is required`));
            }else{
                delete isValid[`${field.id}`];;
                setIsValid(isValid);
            }
        }


        if (constraint.configuration !== null) {
            
            if(value !== undefined && value !== null && value !== ''){
                if (constraint.name === 'minlength' && field.type.name === 'string') {
                    if (value.length < constraint.configuration) {
                        setIsValid(isValid[field.id] = (`${field.id} should have at least ${constraint.configuration} characters`));
                    }else{
                       delete isValid[`${field.id}`];
                       setIsValid(isValid);
                    }
                }

                if (constraint.name === 'maxlength' && field.type.name === 'string') {
                    if (value.length > constraint.configuration) {
                        setIsValid(isValid[field.id] = (`${field.id} should have maximum ${constraint.configuration} characters`));
                    }else{
                        delete isValid[`${field.id}`];
                        setIsValid(isValid);
                    }
                }

                if (constraint.name === 'min' && field.type.name === 'long') {
                    if (value.length < constraint.configuration) {
                        setIsValid(isValid[field.id] = (`${field.id} should have at least ${constraint.configuration} digits`));
                    }else{
                        delete isValid[`${field.id}`];;
                        setIsValid(isValid);
                    }
                }

                if (constraint.name === 'max' && field.type.name === 'long') {
                    if (value.length > constraint.configuration) {
                        setIsValid(isValid[field.id] = (`${field.id} should have maximum ${constraint.configuration} digits`));
                    }else{
                        delete isValid[`${field.id}`];
                        setIsValid(isValid);
                    }
                }

                if (constraint.name === 'readonly') {
                    if (value !== null || value !== undefined || value !== null || value !== '') {
                        setIsValid(isValid[field.id] = (`${field.id} is readonly`));
                }else{
                    delete isValid[`${field.id}`];
                    setIsValid(isValid);
                }
            }
            if (constraint.name === 'validator' && constraint.configuration === 'tim22.upp.LiteralnoUdruzenje.validators.EditorSumValidator') {
                if (value !== null || value !== undefined || value !== null || value !== '' && value.length <= 2) {
                    setIsValid(isValid[field.id] = (`${field.id} you need to select at least 2 editors.`));
            }else{
                delete isValid[`${field.id}`];
                setIsValid(isValid);
            }
        }
        }
    }
    });
    return setIsValid(isValid);
}

