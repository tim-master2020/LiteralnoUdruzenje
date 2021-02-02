export default function validate(field,value,setIsValid,isValid,selected){
    field.validationConstraints.forEach(constraint => {
    
        if (constraint.name === 'required') {
            const message = `${field.label} is required`
            if (value === null || value === undefined || value === null || value === ''){
                debugger;
                Object.assign(isValid,{[`${field.id}`]:message});

            }else{
                if(isValid[`${field.id}`] === message){
                delete isValid[`${field.id}`];;
                setIsValid(isValid);
                }
            }
        }


        if (constraint.configuration !== null) {
            
            if(value !== undefined && value !== null && value !== ''){
            
                if (constraint.name === 'minlength') {
                    let message = '';
                    const messageString = `${field.label} should have minimum ${constraint.configuration} characters`;
                    const messageFile = `${field.label} should have minimum ${constraint.configuration} file(s) uploaded`;
                    if (value.length < constraint.configuration) {
                        message = (field.type.name === 'string')? messageString: messageFile;
                        Object.assign(isValid,{[field.id]: message});
                    }else{
                        if(isValid[`${field.id}`] === message){
                            delete isValid[`${field.id}`];;
                            setIsValid(isValid);
                            }
                    }
                }

                if (constraint.name === 'maxlength' && field.type.name === 'string') {
                    const message = `${field.label} should have maximum ${constraint.configuration} characters`;
                    if (value.length > constraint.configuration) {
                        Object.assign(isValid,{[field.id]:message});
                    }else{
                        if(isValid[`${field.id}`] === message){
                            delete isValid[`${field.id}`];;
                            setIsValid(isValid);
                            }
                    }
                }

                if (constraint.name === 'min' && field.type.name === 'long') {
                    const message = `${field.label} should have at least ${constraint.configuration} digits`; 
                    if (value.length < constraint.configuration) {
                        Object.assign(isValid,{[field.id]:message});

                    }else{
                        if(isValid[`${field.id}`] === message){
                            delete isValid[`${field.id}`];;
                            setIsValid(isValid);
                            }
                    }
                }

                if (constraint.name === 'max' && field.type.name === 'long') {
                    const message = `${field.label} should have maximum ${constraint.configuration} digits`;
                    if (value.length > constraint.configuration) {
                        Object.assign(isValid,{[field.id]:message});
                    }else{
                        if(isValid[`${field.id}`] === message){
                            delete isValid[`${field.id}`];;
                            setIsValid(isValid);
                            }
                    }
                }

                if (constraint.name === 'readonly') {
                    const message = `${field.label} is readonly`;
                    if (value !== null || value !== undefined || value !== null || value !== '') {
                        Object.assign(isValid,{[field.id]:message});

                }else{
                    if(isValid[`${field.id}`] === message){
                        delete isValid[`${field.id}`];;
                        setIsValid(isValid);
                        }
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
            const message = `${field.label} should have minimum size ${minValue}`;
            if (selected === null || selected === undefined || selected === '' || selected.length < minValue) {
                Object.assign(isValid,{[field.id]:`${field.label} should have minimum size ${minValue}`});

            }else{
                if(isValid[`${field.id}`] === message){
                    delete isValid[`${field.id}`];;
                    setIsValid(isValid);
                    }
            }
        }
    }

    if(field.type.name.includes('singleEnum_') || field.type.name.includes('enum') ){
        debugger;
        const message = `For ${field.label} you need to choose one option`;
        if (selected === null || selected === undefined || selected === '' || selected.length === 0) {
             Object.assign(isValid,{[field.id]:message});
        }else{

            if(isValid[`${field.id}`] === message){
                 delete isValid[`${field.id}`];;
                 setIsValid(isValid);
            }
         }
    }

    if(field.type.name.includes('_input_file')){
        debugger;
        const splitArray = field.type.name.split('_');
        let minValue = (splitArray.length === 4 )? splitArray[3] : 'none';
        minValue = parseInt(minValue);

        if(minValue !== 'none'){
            const message = `${field.label} should have minimum size ${minValue}`
            if (value === null || value === undefined || value === '' || value.length < minValue) {
                Object.assign(isValid,{[field.id]:message});

            }else{
                if(isValid[`${field.id}`] === message){
                    delete isValid[`${field.id}`];;
                    setIsValid(isValid);
                    }
            }
        }
    }

    if(field.type.name.includes('input_single')){

        const message = `You need to upload one file.`
        if (value === null || value === undefined || value === '' || value.length === 0) {
                Object.assign(isValid,{[field.id]:message});

            }else{
                if(isValid[`${field.id}`] === message){
                delete isValid[`${field.id}`];;
                setIsValid(isValid);
            }
        }
        
    }

    if(field.type.name.includes('_email')){
        const message = `${field.label} should be a valid email`;
        const regex = '^([_a-zA-Z0-9-]+(\.[_a-zA-Z0-9-]+)*@[a-zA-Z0-9-]+(\.[a-zA-Z0-9-]+)*(\.[a-zA-Z]{1,6}))?$';
            if (value === null || value === undefined || value === '' || value.match(regex) ===  null) {
                Object.assign(isValid,{[field.id]:message});

            }else{
                if(isValid[`${field.id}`] === message){
                    delete isValid[`${field.id}`];;
                    setIsValid(isValid);
                    }
            }
        
    }

    if(field.type.name.includes('_password')){
        const message = `${field.label} should have at least one upper case and one lower case charachter, a number and should have at least 8 charachters.`;
        const regex = '(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8,}';
            if (value === null || value === undefined || value === '' || value.match(regex) ===  null) {
                Object.assign(isValid,{[field.id]:message});

            }else{
                if(isValid[`${field.id}`] === message){
                    delete isValid[`${field.id}`];;
                    setIsValid(isValid);
                    }
            }      
    }

    if(field.type.name.includes('_textArea')){
        const message = `${field.label} is required.Please enter a value.`;

            if (value === null || value === undefined || value === '') {
                Object.assign(isValid,{[field.id]:message});

            }else{
                if(isValid[`${field.id}`] === message){
                    delete isValid[`${field.id}`];;
                    setIsValid(isValid);
                    }
            }      
    }
}

