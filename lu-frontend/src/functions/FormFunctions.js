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
            
        }
    }
    });

    if(field.type.name.includes('multiEnum_editors')){

        const nameArray = field.type.name.split('_');
        const minValue = nameArray[2];
        const maxValue = nameArray[3];
        let isOkay = false;

        if(minValue === 'none' && maxValue === 'none'){
           const minAndMaxValue = field.type.defaultValue;           
           if(value !== null || value !== undefined || value !== null || value !== '' && value.length > minAndMaxValue){
                setIsValid(isValid[field.id] = (`${field.id} should have length of ${minAndMaxValue}`));
           }else{
               isOkay = true;
           }
        }
           else if(minValue === 'none' && maxValue !== 'none'){
               if(value.length > maxValue){
                setIsValid(isValid[field.id] = (`${field.id} should not be bigger than ${maxValue}`));
               }else{
                   isOkay = true;
               }
           }else if(minValue !== 'none' && maxValue === 'none'){
               if(value.length < minValue){
                setIsValid(isValid[field.id] = (`${field.id} should not be lesser than ${minValue}`));
               }else{
                   isOkay = true;
               }
           
            }
           else{

             if(value.length < minValue || value.length > maxValue){
                setIsValid(isValid[field.id] = (`${field.id} should not be lesser than ${minValue} and not bigger than ${maxValue}`));
            }else{
                isOkay = true;
            }
           }

                    
        if(isOkay){
            delete isValid[`${field.id}`];
            setIsValid(isValid);
        }
        
    }
}

