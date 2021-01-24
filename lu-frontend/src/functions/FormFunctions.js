export default function validate(field,value,setIsValid,isValid,selected){
    field.validationConstraints.forEach(constraint => {
    
        if (constraint.name === 'required') {
            if (value === null || value === undefined || value === null || value === '') {
                Object.assign(isValid,{[field.id]:`${field.label} is required`});

            }else{
                delete isValid[`${field.id}`];;
                setIsValid(isValid);
            }
        }


        if (constraint.configuration !== null) {
            
            if(value !== undefined && value !== null && value !== ''){
                if (constraint.name === 'minlength') {
                    debugger;
                    if (value.length < constraint.configuration) {
                        Object.assign(isValid,{[field.id]:`${field.label} should have minimum ${constraint.configuration} characters`});
                    }else{
                       delete isValid[`${field.id}`];
                       setIsValid(isValid);
                    }
                }

                if (constraint.name === 'maxlength') {
                    if (value.length > constraint.configuration) {
                        Object.assign(isValid,{[field.id]:`${field.label} should have maximum ${constraint.configuration} characters`});
                    }else{
                        delete isValid[`${field.id}`];
                        setIsValid(isValid);
                    }
                }

                if (constraint.name === 'min') {
                    if (value.length < constraint.configuration) {
                        Object.assign(isValid,{[field.id]:`${field.label} should have at least ${constraint.configuration} digits`});

                    }else{
                        delete isValid[`${field.id}`];;
                        setIsValid(isValid);
                    }
                }

                if (constraint.name === 'max') {
                    if (value.length > constraint.configuration) {
                        Object.assign(isValid,{[field.id]:`${field.label} should have maximum ${constraint.configuration} digits`});
                    }else{
                        delete isValid[`${field.id}`];
                        setIsValid(isValid);
                    }
                }

                if (constraint.name === 'readonly') {
                    if (value !== null || value !== undefined || value !== null || value !== '') {
                        Object.assign(isValid,{[field.id]:`${field.label} is readonly`});

                }else{
                    delete isValid[`${field.id}`];
                    setIsValid(isValid);
                }
            }
            
        }
    }
    });

    if(field.type.name.includes('multiEnum_')){
        debugger;
        const splitArray = field.type.name.split('_');
        let minValue = (splitArray.length === 3 )? splitArray[2] : 'none';
        minValue = parseInt(minValue);

        if(minValue !== 'none'){
            if (selected === null || selected === undefined || selected === '' || selected.length < minValue) {
                Object.assign(isValid,{[field.id]:`${field.label} should have minimum size ${minValue}`});

            }else{
                delete isValid[`${field.id}`];
                setIsValid(isValid);
            }
        }
    }
}

